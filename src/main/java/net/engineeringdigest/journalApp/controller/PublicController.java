package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.dto.UserDTO;
import net.engineeringdigest.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private UserService userService;

    @PostMapping("/create-user")
    public ResponseEntity<Boolean> createUser(@RequestBody UserDTO newUserDTO) {
        boolean flag = userService.saveNewUser(newUserDTO);
        if(flag) {
            return new ResponseEntity<>(flag,HttpStatus.OK);
        }
        return new ResponseEntity<>(flag,HttpStatus.BAD_REQUEST);
    }
}
