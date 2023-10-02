package myApp.myApp.Service;

import myApp.myApp.Entity.Role;
import myApp.myApp.Entity.User;
import myApp.myApp.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        System.out.println("Email/loadUserbyEmail/CustomUSerDetailsService");
        System.out.println(email);
        System.out.println("USer Repository/load User by Username/CustomUSerDetailsService");
        System.out.println(userRepository);
        User user = userRepository.findByEmail(email);
        System.out.println("user/CustomUSerDetailsService");
        System.out.println(user);
        if (user != null) {
            System.out.println("Load User Not Null/CustomUSerDetailsService");
            System.out.println(user);
            return new CustomUserDetails(user.getEmail(), user.getPassword(), authorities(user.getRoles()), user.getFullName());
        }else{
            System.out.println("Else user/password invalid/CustomUSerDetailsService");
            System.out.println(user);
            throw new UsernameNotFoundException("Invalid username or password.");
        }
    }

    public Collection<? extends GrantedAuthority> authorities(Collection<Role> roles){
        System.out.println("GrantedAuthority");
        Collection < ? extends GrantedAuthority> mapRoles = roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
        System.out.println(mapRoles);
        return mapRoles;
    }


}
