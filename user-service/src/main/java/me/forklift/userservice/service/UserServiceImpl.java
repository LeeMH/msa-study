package me.forklift.userservice.service;

import lombok.extern.slf4j.Slf4j;
import me.forklift.userservice.dto.UserDto;
import me.forklift.userservice.repository.UserEntity;
import me.forklift.userservice.repository.UserRepository;
import me.forklift.userservice.vo.ResponseOrder;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    UserRepository repository;
    BCryptPasswordEncoder encoder;
    Environment env;
    RestTemplate restTemplate;

    @Autowired
    public UserServiceImpl(UserRepository repository, BCryptPasswordEncoder encoder, Environment env, RestTemplate restTemplate) {
        this.repository = repository;
        this.encoder = encoder;
        this.env = env;
        this.restTemplate = restTemplate;
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        userDto.setUserId(UUID.randomUUID().toString());

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserEntity userEntity = mapper.map(userDto, UserEntity.class);
        userEntity.setEncryptedPassword(encoder.encode(userDto.getPassword()));

        repository.save(userEntity);

        UserDto returnUserDto = mapper.map(userEntity, UserDto.class);

        return returnUserDto;
    }

    @Override
    public UserDto getUserByUserId(String userId) {
        UserEntity user = repository.findByUserId(userId);

        if (user == null)
            throw new UsernameNotFoundException("user not found");

        UserDto userDto = new ModelMapper().map(user, UserDto.class);

        String orderUrl = String.format(env.getProperty("order_service.url"), userId);
        log.info("api call url = {}", orderUrl);

        ResponseEntity<List<ResponseOrder>> orderListResponse = restTemplate.exchange(
                orderUrl, HttpMethod.GET, null, new ParameterizedTypeReference<List<ResponseOrder>>() {
                }
        );

        List<ResponseOrder> orders = orderListResponse.getBody();
        userDto.setOrders(orders);

        return userDto;
    }

    @Override
    public UserDto getUserByEmail(String email) {
        UserEntity user = repository.findByEmail(email);
        UserDto userDto = new ModelMapper().map(user, UserDto.class);

        if (user == null) throw new UsernameNotFoundException("존재하지 않는 사용자 입니다.");

        return userDto;
    }

    @Override
    public Iterable<UserEntity> getUserByAll() {
        return repository.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity = repository.findByEmail(email);
        if (userEntity == null)
            throw new UsernameNotFoundException(String.format("[%s] 이메일 사용자를 찾을수 없습니다.", email));

        return new User(userEntity.getEmail(), userEntity.getEncryptedPassword(),
                true, true, true, true,
                new ArrayList<>());
    }
}
