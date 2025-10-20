package net.engineeringdigest.journalApp.controller;

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
    public ResponseEntity<User> getUserbyId() {
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
        userInDb.setUserName(updatedUserDTO.getUserName());
        userInDb.setPassword(updatedUserDTO.getPassword());
        boolean b = userService.saveNewUser(userInDb);//this method is used bcz we need to encode password again and save.
        return new ResponseEntity<>(b,HttpStatus.NO_CONTENT);
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
