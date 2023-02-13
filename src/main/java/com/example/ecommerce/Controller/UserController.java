package com.example.ecommerce.Controller;


import com.example.ecommerce.Dto.ResponseDto;
import com.example.ecommerce.Dto.User.SignInDto;
import com.example.ecommerce.Dto.User.SignInResponseDto;
import com.example.ecommerce.Dto.User.SignupDto;
import com.example.ecommerce.Exception.AuthenticationFailException;
import com.example.ecommerce.Exception.CustomException;
import com.example.ecommerce.Model.User;
import com.example.ecommerce.Service.AuthenticationService;
import com.example.ecommerce.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.util.List;

@RequestMapping("user")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class UserController {
    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    UserService userService;

    @GetMapping("/all")
    public List<User> findAllUser(@RequestParam("token") String token) throws AuthenticationFailException {
        authenticationService.authenticate(token);
        return userService.findAll();
    }

    @GetMapping("/get")
    public User getCurrentUser(@RequestParam("token") String token) throws AuthenticationFailException {
        authenticationService.authenticate(token);
        return authenticationService.getUser(token);
    }

    @PostMapping("/signup")
    public ResponseDto signUp(@RequestBody SignupDto signupDto) throws Exception {
        return userService.signUp(signupDto);
    }

    @PostMapping("/signIn")
    public SignInResponseDto signIn(@RequestBody SignInDto signInDto) throws CustomException, NoSuchAlgorithmException {
        return userService.signIn(signInDto);
    }
}
