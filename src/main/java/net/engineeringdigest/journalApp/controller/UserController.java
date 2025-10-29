package net.engineeringdigest.journalApp.controller;

import lombok.extern.slf4j.Slf4j;
import net.engineeringdigest.journalApp.api.response.WeatherResponse;
import net.engineeringdigest.journalApp.dto.UserDTO;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repository.UserRepository;
import net.engineeringdigest.journalApp.service.UserService;
import net.engineeringdigest.journalApp.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WeatherService weatherService;

    @GetMapping()
    public ResponseEntity<User> getUserbyUserName() {
        // SecurityContextHolder Stores currently authenticated user's details for this request.
        // we fetch userDetails like username from authentication instead of endpoints.
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName(); // Fetch user from DB using username
        User user = userService.findByUserName(userName);
        return new ResponseEntity<>(user,HttpStatus.OK);
    }

    @Transactional
    @PutMapping()
    public ResponseEntity<Boolean> updateUser(@RequestBody UserDTO updatedUserDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User userInDb = userService.findByUserName(userName);

        try{
            // Update only non-empty fields from DTO
            if (updatedUserDTO.getUserName() != null && !updatedUserDTO.getUserName().trim().isEmpty()) {
                userInDb.setUserName(updatedUserDTO.getUserName().trim()); // update username
            }

            if (updatedUserDTO.getEmail() != null && !updatedUserDTO.getEmail().trim().isEmpty()) {
                userInDb.setEmail(updatedUserDTO.getEmail().trim()); // update email
            }

            // Update only if sentiment flag changed
            if (updatedUserDTO.isSentimentAnalysis() != userInDb.isSentimentAnalysis()) {
                userInDb.setSentimentAnalysis(updatedUserDTO.isSentimentAnalysis());
            }

            if (updatedUserDTO.getPassword() != null && !updatedUserDTO.getPassword().trim().isEmpty()) {
                userInDb.setPassword(updatedUserDTO.getPassword().trim()); // update password
                userService.saveNewUser(userInDb); // call service that encrypts + saves
            } else {
                userService.saveUser(userInDb); // normal save (no bcrypt)
            }
            return new ResponseEntity<>(HttpStatus.OK);

        } catch (Exception e) {
            log.error("Error during update : "+e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    @DeleteMapping()
    public ResponseEntity<?> deleteUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        userRepository.deleteByUserName(userName);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/greetings")
    public ResponseEntity<String> greetings() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        WeatherResponse weatherResponse = weatherService.getWeather("Mumbai");
        String greet = "";
        if(weatherResponse != null) {
            int temperature = weatherResponse.getCurrent().getTemperature();
            int feelslike = weatherResponse.getCurrent().getFeelslike();
            List<String> weatherDescriptions = weatherResponse.getCurrent().getWeatherDescriptions();
            greet = ", weather in Mumbai feels like, " + "temperature=" +temperature+" degree celcius," + "feels like: "+feelslike+ " degree celcius,"+ " weather: "+weatherDescriptions;
        }
        return new ResponseEntity<>("Hi " + userName + greet, HttpStatus.OK);
    }
}
