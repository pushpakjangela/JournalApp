package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/admin")
@SuppressWarnings("ALL")
public class AdminController {
    @Autowired
    private UserService userService;

    @GetMapping("/all-users")
    public ResponseEntity<?> getAllAdminUsers() {
        System.out.println("Here is all users ADMIN");
        List<User> all =  userService.getAllUsers();
        if(!all.isEmpty() && all!=null) {
            return new ResponseEntity<>(all, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @PostMapping("/create-admin-user")
    public ResponseEntity<?> addAdminUser(@RequestBody User user) {
        try{
            userService.saveNewAdminUser(user);
            return new ResponseEntity<>(user,HttpStatus.CREATED);
        }catch (Exception e) {
            System.out.println("Error while creating admin user"+e);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
