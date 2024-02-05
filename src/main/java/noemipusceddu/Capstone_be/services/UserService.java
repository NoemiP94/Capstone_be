package noemipusceddu.Capstone_be.services;

import noemipusceddu.Capstone_be.entities.User;
import noemipusceddu.Capstone_be.exceptions.NotFoundException;
import noemipusceddu.Capstone_be.payloads.user.UserDTO;
import noemipusceddu.Capstone_be.repositories.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private PasswordEncoder bcrypt;

    public Page<User> getUsers(int page, int size, String sort){
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        return userDAO.findAll(pageable);
    }

    public User findById(UUID id) throws NotFoundException {
        return userDAO.findById(id).orElseThrow(()-> new NotFoundException(id));
    }

    public User findByEmail(String email){
        return userDAO.findByEmail(email).orElseThrow(()-> new NotFoundException("User with email " + email + " not found!"));
    }

    public User findByIdAndUpdate(UUID id, UserDTO body){
        User user = this.findById(id);
        user.setName(body.name());
        user.setSurname(body.surname());
        user.setEmail(body.email());
        user.setPassword(bcrypt.encode(body.password()));
        return userDAO.save(user);
    }

    public void deleteById(UUID id){
        User user = this.findById(id);
        userDAO.delete(user);
    }
}
