package noemipusceddu.Capstone_be.services;

import noemipusceddu.Capstone_be.entities.Role;
import noemipusceddu.Capstone_be.entities.User;
import noemipusceddu.Capstone_be.exceptions.BadRequestException;
import noemipusceddu.Capstone_be.exceptions.UnauthorizedException;
import noemipusceddu.Capstone_be.payloads.user.UserDTO;
import noemipusceddu.Capstone_be.payloads.user.UserLoginDTO;
import noemipusceddu.Capstone_be.repositories.UserDAO;
import noemipusceddu.Capstone_be.security.JWTTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private UserService userService;
    @Autowired
    private JWTTools jwtTools;
    @Autowired
    private PasswordEncoder bcrypt;

    public Map<String, String> authenticateUser(UserLoginDTO body){
        User user = userService.findByEmail(body.email());
        if(bcrypt.matches(body.password(), user.getPassword())){
            String role = String.valueOf(user.getRole());
            String accessToken = jwtTools.createToken(user);
            Map<String, String> response = new HashMap<>();
            response.put("role", role);
            response.put("token", accessToken);
            return response;
        }else{
            throw new UnauthorizedException("Wrong password!");
        }
    }

    public User registerUser(UserDTO body) throws IOException{
        userDAO.findByEmail(body.email()).ifPresent(a->{
            throw new BadRequestException("User with email " + a.getEmail() + " already exists" );
        });
        User user = new User();
        user.setName(body.name());
        user.setSurname(body.surname());
        user.setEmail(body.email());
        user.setPassword(bcrypt.encode(body.password()));
        user.setRole(Role.ADMIN);
        return userDAO.save(user);
    }

}
