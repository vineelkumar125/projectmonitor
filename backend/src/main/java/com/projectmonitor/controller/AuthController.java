//package com.projectmonitor.controller;
//import com.projectmonitor.dto.*; import com.projectmonitor.entity.*; import com.projectmonitor.repository.UserRepository; import com.projectmonitor.security.JwtService; import jakarta.validation.Valid; import org.springframework.http.*; import org.springframework.security.crypto.password.PasswordEncoder; import org.springframework.web.bind.annotation.*;
//@RestController @RequestMapping("/api/auth") public class AuthController {private final UserRepository users;private final PasswordEncoder encoder;private final JwtService jwt; public AuthController(UserRepository u,PasswordEncoder e,JwtService j){users=u;encoder=e;jwt=j;}
// @PostMapping("/login") public LoginResponse login(@Valid @RequestBody LoginRequest r){User u=users.findByEmail(r.email()).orElseThrow(()->new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Invalid email or password"));if(!encoder.matches(r.password(),u.getPassword()))throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Invalid email or password");return new LoginResponse(jwt.generate(u.getEmail(),u.getRole().name()),u.getId(),u.getName(),u.getEmail(),u.getRole().name());}
// @PostMapping("/register") public LoginResponse register(@Valid @RequestBody RegisterRequest r){if(users.findByEmail(r.email()).isPresent())throw new ResponseStatusException(HttpStatus.CONFLICT,"Email already registered");Role role=Role.MEMBER;try{if(r.role()!=null)role=Role.valueOf(r.role().toUpperCase());}catch(Exception ignored){} User u=users.save(new User(r.name(),r.email(),encoder.encode(r.password()),role));return new LoginResponse(jwt.generate(u.getEmail(),u.getRole().name()),u.getId(),u.getName(),u.getEmail(),u.getRole().name());}
//}
package com.projectmonitor.controller;

import com.projectmonitor.dto.LoginRequest;
import com.projectmonitor.dto.LoginResponse;
import com.projectmonitor.dto.RegisterRequest;
import com.projectmonitor.entity.Role;
import com.projectmonitor.entity.User;
import com.projectmonitor.repository.UserRepository;
import com.projectmonitor.security.JwtService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

 private final UserRepository users;
 private final PasswordEncoder encoder;
 private final JwtService jwt;

 public AuthController(
         UserRepository users,
         PasswordEncoder encoder,
         JwtService jwt) {
  this.users = users;
  this.encoder = encoder;
  this.jwt = jwt;
 }

 @PostMapping("/login")
 public LoginResponse login(
         @Valid @RequestBody LoginRequest request) {

  User user = users.findByEmail(request.email())
          .orElseThrow(() ->
                  new ResponseStatusException(
                          HttpStatus.UNAUTHORIZED,
                          "Invalid email or password"
                  ));

  if (!encoder.matches(
          request.password(),
          user.getPassword())) {

   throw new ResponseStatusException(
           HttpStatus.UNAUTHORIZED,
           "Invalid email or password"
   );
  }

  return new LoginResponse(
          jwt.generate(
                  user.getEmail(),
                  user.getRole().name()
          ),
          user.getId(),
          user.getName(),
          user.getEmail(),
          user.getRole().name()
  );
 }

 @PostMapping("/register")
 public LoginResponse register(
         @Valid @RequestBody RegisterRequest request) {

  if (users.findByEmail(request.email()).isPresent()) {
   throw new ResponseStatusException(
           HttpStatus.CONFLICT,
           "Email already registered"
   );
  }

  Role role = Role.MEMBER;

  try {
   if (request.role() != null) {
    role = Role.valueOf(
            request.role().toUpperCase()
    );
   }
  } catch (IllegalArgumentException ignored) {
   // Keep default MEMBER role
  }

  User user = users.save(
          new User(
                  request.name(),
                  request.email(),
                  encoder.encode(request.password()),
                  role
          )
  );

  return new LoginResponse(
          jwt.generate(
                  user.getEmail(),
                  user.getRole().name()
          ),
          user.getId(),
          user.getName(),
          user.getEmail(),
          user.getRole().name()
  );
 }
}
