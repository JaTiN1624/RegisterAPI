package com.example.Invoice.System.Controller;


import com.example.Invoice.System.Entity.Users;
import com.example.Invoice.System.Service.UsersService;
import com.example.Invoice.System.requests.LoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class usersController {

    @Autowired
    UsersService userService;
    @PostMapping("/addUser")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<?> addUser(@RequestBody Users user){

        try{
            Users newUser = userService.addUser(user);
            return new ResponseEntity<>(newUser, HttpStatus.CREATED);
        }
        catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/loginUser")
    @CrossOrigin(origins = "http://localhost:3000")
    public Boolean loginUser(@RequestBody LoginRequest loginRequest){
        return userService.loginUser(loginRequest);
    }
    @PostMapping("/verify")
    public ResponseEntity<?> verifyUser(@RequestParam String email, @RequestParam String otp){
        try{
            userService.verify(email, otp);

            return new ResponseEntity<>("User verified successfully",HttpStatus.OK);
        }catch (RuntimeException e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
}
