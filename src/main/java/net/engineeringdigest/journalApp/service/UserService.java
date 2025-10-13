package net.engineeringdigest.journalApp.service;

import lombok.extern.slf4j.Slf4j;
import net.engineeringdigest.journalApp.controller.JournalEntryController;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repository.UserRepository;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

@Slf4j
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    //private static final Logger logger = LoggerFactory.getLogger(UserService.class); // used for understanding logging but we can use @Slf4j annotation internally includes instance of logger.


    public boolean saveNewUser(User user) { // used only when new user is saved with credentials
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoles(Arrays.asList("USER"));
            userRepository.save(user);
            return true;
        } catch (Exception e) {
//            log.error("Error occured for {}: ", user.getUserName(), e);
            log.info("hahahah");
            log.debug("hahahah");
//            log.trace("hahahah");
//            log.warn("hahahah");
            return false;
        }
    }

    // used for save most of the time
    public void saveUser(User user) {
        try {
            userRepository.save(user);
        } catch (Exception e) {
            log.error("Exception : ",e);
        }
    }

    // used when new admin user is saved with credentials
    public void saveAdmin(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("USER", "ADMIN"));
        userRepository.save(user);
    }

    public ArrayList<User> getAllEntries() {
        return new ArrayList<>(userRepository.findAll());
    }

    public Optional<User> findById(ObjectId id) {
        return userRepository.findById(id);
    }

    public User findByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

    public void deleteById(ObjectId id) {
        userRepository.deleteById(id);
    }

    public void deleteByUserName(String userName) {
        userRepository.deleteByUserName(userName);
    }

}
