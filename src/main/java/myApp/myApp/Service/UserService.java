package myApp.myApp.Service;

import myApp.myApp.Entity.User;
import myApp.myApp.Entity.UserDto;

import java.util.List;

public interface UserService {
    void saveUser(UserDto userDto);

    User findByEmail(String email);

    List<UserDto> findAllUsers();

    void updateUser(String email, User user);
}
