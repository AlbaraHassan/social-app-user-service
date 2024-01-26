package com.example.userservice.rest.controllers;


import com.example.userservice.core.auth.Auth;
import com.example.userservice.rest.dtos.UserCreateDTO;
import com.example.userservice.rest.dtos.UserDTO;
import com.example.userservice.rest.enums.Role;
import com.example.userservice.rest.models.UserModel;
import com.example.userservice.rest.services.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@RestController
@RequestMapping("api/user")
@Tag(name = "User")
public class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @Auth({Role.ADMIN})
  @GetMapping("/all")
  public Optional<List<UserDTO>> getAll() {
    return this.userService.getAll();
  }

  @Auth
  @GetMapping
  public Optional<UserDTO> get(@RequestParam String id) {
    return this.userService.get(id);
  }

  @Auth
  @GetMapping("/getMe")
  public UserDTO getMe(HttpServletRequest request) {
    return this.userService.getMe(request.getHeader("Authorization").substring(7));
  }

  @Auth({Role.ADMIN})
  @DeleteMapping
  public boolean delete(@RequestParam String id) {
    return this.userService.delete(id);
  }

  @PostMapping
  public Optional<UserModel> create(@RequestBody UserModel data) {
    return this.userService.create(data);
  }

  @PostMapping("verify")
  public Optional<UserModel> verify(@RequestParam String id, @RequestBody UserModel data) {
    return this.userService.verify(id, data);
  }

  @GetMapping("email")
  public Optional<UserModel> getByEmail(@RequestParam String email) {
    return this.userService.getByEmail(email);
  }


  @GetMapping("/search")
  @Auth
  public List<UserModel> search(@RequestParam String userName) {
    return this.userService.search(userName);
  }

  @GetMapping("/getByUsername")
  public Optional<UserDTO> getByUsername(@RequestParam String userName) {
    return this.userService.getByUsername(userName);
  }

}
