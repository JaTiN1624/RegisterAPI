package com.example.Invoice.System.Service;

//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.example.Invoice.System.Entity.Users;
import com.example.Invoice.System.Repo.UsersRepo;
import com.example.Invoice.System.requests.LoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
public class UsersService {
//    @Autowired
//    LoginRequest loginRequest;

    private final EmailService emailService;

    @Autowired
    UsersRepo usersRepo;

    public UsersService(EmailService emailService) {
        this.emailService = emailService;
    }


//    public UsersService(EmailService emailService) {
//        this.emailService = emailService;
//    }

    // BCrypt encoder
//    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public Users addUser(Users user) throws Exception{

        Optional<Users> existingUser = usersRepo.findByEmail(user.getEmail());


        if(existingUser.isPresent()  && existingUser .get().getVerified()){
            throw new Exception("Email already Exist. Please try different one.");
        }

        if(!user.getcPassword().equals(user.getPassword())){
            throw new Exception("Password not match. Password should be same!");
        }
//        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(user.getPassword());
        String otp = generatedOtp();
        user.setOtp(otp);
        Users savedUser =  usersRepo.save(user);
        sendVerificationEmail(savedUser.getEmail(), otp);

        return savedUser;
    }

//    send email to user for verification
    public void verify(String email, String otp) {
        Optional<Users> users = usersRepo.findByEmail(email);
        if (users.isEmpty()) {
            throw new RuntimeException("User   not found!");
        }
        Users user = users.get();
        if (user.getOtp().equals(otp)) {
            user.setVerified(true); // Update verified to 1
            usersRepo.save(user);
        } else {
            throw new RuntimeException("Invalid OTP!");
        }
    }


//    Generate OTP
    private String generatedOtp(){
        Random random = new Random();
        int otpValue = 100000 + random.nextInt(900000);

        return String.valueOf(otpValue);
    }

//    send verification to user mail
    private void sendVerificationEmail(String email,String otp){
        String subject = "Email verification ";
        String body = "Your verification otp is: " + otp;

        emailService.sendEmail(email,subject,body);
    }

//    login user  section
    public boolean loginUser(LoginRequest loginRequest){
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();


        Optional<Users> user = usersRepo.findByEmail(email);

        if (user.isEmpty() || !user.get().getVerified()) {
            return false;
        }

        // Get the user object
        Users user1 = user.get();
        // Compare the password
//        if (!passwordEncoder.matches(password, user1.getPassword())) {
//            return false;
//        }

        if(!password.equals(user1.getPassword())){
            return false;
        }
        return true;
    }


}
