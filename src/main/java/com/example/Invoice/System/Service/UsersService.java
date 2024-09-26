package com.example.Invoice.System.Service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.example.Invoice.System.Entity.Users;
import com.example.Invoice.System.Repo.UsersRepo;
import com.example.Invoice.System.requests.LoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsersService {
//    @Autowired
//    LoginRequest loginRequest;

    @Autowired
    UsersRepo usersRepo;

    // BCrypt encoder
    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public Users addUser(Users user) throws Exception{

        Optional<Users> existingUser = usersRepo.findByEmail(user.getEmail());


        if(existingUser.isPresent()){
            throw new Exception("Email already Exist. Please try different one.");
        }

        if(!user.getcPassword().equals(user.getPassword())){
            throw new Exception("Password not match. Password should be same!");
        }

        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);

        return usersRepo.save(user);

    }


    public boolean loginUser(LoginRequest loginRequest){
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();


        Optional<Users> user = usersRepo.findByEmail(email);

        if (user.isEmpty()) {
            return false;
        }

        // Get the user object
        Users user1 = user.get();

        // Compare the password
        if (!passwordEncoder.matches(password, user1.getPassword())) {
            return false;
        }
        return true;
    }
}
