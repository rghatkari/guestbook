package org.rghatkari.guestbook.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.rghatkari.guestbook.entities.Role;
import org.rghatkari.guestbook.entities.User;
import org.rghatkari.guestbook.repository.UserRepository;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.HashSet;
import java.util.Set;

import static org.mockito.Mockito.doReturn;

@SpringBootTest
public class UserDetailsServiceImplTest {

    UserDetailsServiceImpl service = new UserDetailsServiceImpl();

    @MockBean
    private UserRepository userRepository;


//    @Test
//    @DisplayName("load user by username email while login")
//    void loadUserByUsername() {
//        Set<Role> roleSet = new HashSet<>();
//        Role role = new Role();
//        role.setId(1);
//        role.setName("GUEST");
//        roleSet.add(role);
//        String emailId = "test@test.com";
//        User user = new User();
//        user.setId(1);
//        user.setRoles(roleSet);
//        user.setEmail("test@test.com");
//        user.setFullName("RajeshG");
//        doReturn(user).when(userRepository).getUserByEmail(emailId);
//        UserDetails userDetails = service.loadUserByUsername(emailId);
//    }
}
