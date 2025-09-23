package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

// step 2 required for authentication
// 1. User entity, ✅
// 2. UserRepository to interact with mongodb, ✅
// 3. UserDetailsService implementation is needed to fetch user details for authentication, ✅
// 4. A configuration SecurityConfig to integrate everything with spring security (AuthenticationManager setup) ✅

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUserName(username);
        if(user != null) {
            return org.springframework.security.core.userdetails.User.builder()
                    .username(user.getUserName()) // Spring ko username chahiye
                    .password(user.getPassword()) // DB se mila password
                    .roles(user.getRoles().toArray(new String[0])) // List<String> ko String[] me convert
                    .build(); // make UserDetails object in spring friendly format
        }
        throw new UsernameNotFoundException("User not found with userName" + username);
    }
}
