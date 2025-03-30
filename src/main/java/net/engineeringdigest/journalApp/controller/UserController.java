package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repository.UserRepository;
import net.engineeringdigest.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
@SuppressWarnings("ALL")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @GetMapping("/allUsers")
    public ResponseEntity<?> getAllUser() {
        List<User> allUser=userService.getAllUsers();
        if(allUser!=null&& !allUser.isEmpty()) {
            return new ResponseEntity<>(allUser, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/getUserByUserName/{userName}")
    public ResponseEntity<?> getUserByUserName(@PathVariable String userName) {
        try{
            User user = userService.findUserByUsername(userName);
            return new ResponseEntity<>(user,HttpStatus.OK);
        } catch(Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @GetMapping("id/{userId}")
    public ResponseEntity<?> getUserById(@PathVariable ObjectId userId) {
        Optional<User> user=userRepository.findById(userId);
        if(user.isPresent()){
            return new ResponseEntity<>(user.get(),HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/deleteUser")
    public ResponseEntity<?> deleteUserById(@RequestBody User user ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        userService.deleteByUserName(userName);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/updateUser")
    public ResponseEntity<?> updateUser (@RequestBody User user) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        String userName = authentication.getName();
        User currentUser  = userService.findUserByUsername(userName);
        if (currentUser  != null) {
            currentUser.setUserName(user.getUserName());
            currentUser.setPassword(user.getPassword());
            userService.saveNewUser (currentUser );
            return new ResponseEntity<>(currentUser , HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
