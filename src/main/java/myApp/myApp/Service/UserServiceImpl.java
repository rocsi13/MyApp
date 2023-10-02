package myApp.myApp.Service;

import myApp.myApp.Entity.Role;
import myApp.myApp.Entity.User;
import myApp.myApp.Entity.UserDto;
import myApp.myApp.Repository.RoleRepository;
import myApp.myApp.Repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService{
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository,  RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }
    @Override
    public void saveUser(UserDto userDto) {
        User user = new User();
        user.setFullName(userDto.getFullName());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        Role role = roleRepository.findByName("ADMIN");
        if (role == null) {
            role = checkRoleExist();
        }
        user.setRoles(Arrays.asList(role));
        userRepository.save(user);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    private UserDto convertEntityToDto(User user){
        UserDto userDto = new UserDto();
        userDto.setFullName(user.getFullName());
        userDto.setEmail(user.getEmail());
        System.out.println("User DTO / Convert User to DTo");
        System.out.println(userDto);
        return userDto;
    }
    @Override
    public List<UserDto> findAllUsers() {
        List<User> users = userRepository.findAll();
        System.out.println("Find All Users Service impl");
        return users.stream()
                .map((user) -> convertEntityToDto(user))
                .collect(Collectors.toList());
    }
    private Role checkRoleExist() {
        Role role = new Role();
        role.setName("ADMIN");
        return roleRepository.save(role);
    }
}
