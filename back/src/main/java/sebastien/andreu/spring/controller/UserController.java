package sebastien.andreu.spring.controller;

import org.hibernate.annotations.Any;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import sebastien.andreu.spring.dto.user.LoginDto;
import sebastien.andreu.spring.dto.user.SignUpDto;
import sebastien.andreu.spring.entity.User;
import sebastien.andreu.spring.jwt.JwtUtil;
import sebastien.andreu.spring.repository.RoleRepository;
import sebastien.andreu.spring.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

@RestController
public class UserController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping(path = "/signup", produces = "application/json")
    public @ResponseBody ResponseEntity<Map<String, String>> registerUser(@RequestBody SignUpDto signUpDto) {
        Map<String, String> response = new HashMap<>();

        try {
            List<User> users = (List<User>) userRepository.findAll();
            Optional<User> foundUser = users.stream()
                    .filter(userFind -> userFind.getEmail().equals(signUpDto.getEmail()))
                    .findFirst();

            if (foundUser.isPresent()) {
                throw new RuntimeException("Cette adresse e-mail est déjà utilisée.");
            }

            foundUser = users.stream()
                    .filter(userFind -> userFind.getPseudo().equals(signUpDto.getPseudo()))
                    .findFirst();

            if (foundUser.isPresent()) {
                throw new RuntimeException("Ce pseudo est déjà utilisé.");
            }

            User user = new User();
            user.setPseudo(signUpDto.getPseudo());
            user.setEmail(signUpDto.getEmail());
            user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));
            user.setRole("USER");
            userRepository.save(user);

            response.put("message", "Utilisateur enregistré avec succès");
            response.put("user", user.toString());
            System.out.println(response);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(path = "/login", produces = "application/json")
    public ResponseEntity<Map<String, Any>> authenticateUser(@RequestBody LoginDto loginDto) {
        Map<String, String> response = new HashMap<>();
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getPseudo(), loginDto.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            User user = userRepository.findByPseudoOrEmail(loginDto.getPseudo(), loginDto.getPseudo());
            if (user == null) {
                throw new RuntimeException("Username not found!");
            }

            String token = jwtUtil.createToken(user);
            response.put("token", token);
            return new ResponseEntity(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping(path = "/test", produces = "application/json")
    public ResponseEntity<String> authenticateUser() {
        return new ResponseEntity<String>("test", HttpStatus.OK);
    }

    @GetMapping(path = "/test2", produces = "application/json")
    public ResponseEntity<String> authenticateUser2() {
        return new ResponseEntity<String>("test2", HttpStatus.OK);
    }
}