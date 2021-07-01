package me.forklift.userservice.service;

import me.forklift.userservice.dto.UserDto;
import me.forklift.userservice.repository.UserEntity;

public interface UserService {
    UserDto createUser(UserDto userDto);

    UserDto getUserByUserId(String userId);

    Iterable<UserEntity> getUserByAll();
}
