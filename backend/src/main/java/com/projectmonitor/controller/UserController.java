package com.projectmonitor.controller;
import com.projectmonitor.entity.User; import com.projectmonitor.repository.UserRepository; import org.springframework.web.bind.annotation.*; import java.util.List;
@RestController @RequestMapping("/api/users") public class UserController {private final UserRepository repo;public UserController(UserRepository r){repo=r;} @GetMapping public List<User> all(){return repo.findAll().stream().peek(u->u.setPassword(null)).toList();}}
