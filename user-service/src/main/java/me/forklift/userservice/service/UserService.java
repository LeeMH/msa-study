package me.forklift.userservice.service;

import me.forklift.userservice.dto.UserDto;

public interface UserService {
    UserDto createUser(UserDto userDto);
}
