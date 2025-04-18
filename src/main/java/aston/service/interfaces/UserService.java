package aston.service.interfaces;

import aston.dto.UserDto;

public interface UserService {

    UserDto getUser(Long id);

    UserDto createUser(UserDto userDto);

    UserDto updateUser(Long id, UserDto userDto);

    void deleteUser(Long id);
}