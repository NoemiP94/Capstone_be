package noemipusceddu.Capstone_be.controllers;


import noemipusceddu.Capstone_be.entities.User;
import noemipusceddu.Capstone_be.exceptions.BadRequestException;
import noemipusceddu.Capstone_be.payloads.user.UserDTO;
import noemipusceddu.Capstone_be.payloads.user.UserLoginDTO;
import noemipusceddu.Capstone_be.payloads.user.UserLoginResponseDTO;
import noemipusceddu.Capstone_be.payloads.user.UserResponseDTO;
import noemipusceddu.Capstone_be.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponseDTO registerUser(@RequestBody @Validated UserDTO newUserBody, BindingResult validation) throws IOException{
        if(validation.hasErrors()){
            throw new BadRequestException("There are error in the request!");
        }else{
            User newUser = authService.saveUser(newUserBody);
            return new UserResponseDTO(newUser.getId());
        }
    }

    @PostMapping("/login")
    public UserLoginResponseDTO loginUser(@RequestBody UserLoginDTO body){
        Map<String, String> authResponse = authService.authenticateUser(body);
        return new UserLoginResponseDTO(authResponse.get("token"), authResponse.get("role"));
    }

}
