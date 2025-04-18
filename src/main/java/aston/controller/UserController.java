package aston.controller;

import aston.dto.UserDto;
import aston.service.interfaces.UserService;

public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    public UserDto getUser(Long id) {
        return userService.getUser(id);
    }

    public UserDto createUser(UserDto userDto) {
        return userService.createUser(userDto);
    }

    public UserDto updateUser(Long id, UserDto userDto) {
        return userService.updateUser(id, userDto);
    }

    public void deleteUser(Long id) {
        userService.deleteUser(id);
    }
}
