package com.example.userservice.rest.services;

import com.example.userservice.core.exceptions.auth.EntityAlreadyExistsException;
import com.example.userservice.core.exceptions.general.NotFoundException;
import com.example.userservice.core.helpers.JwtService;
import com.example.userservice.core.repositories.UserRepository;
import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import com.example.userservice.rest.dtos.UserDTO;
import com.example.userservice.rest.models.UserModel;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class UserService {

  private final UserRepository userRepository;
  private final JwtService jwtService;

  public UserService(UserRepository userRepository, JwtService jwtService) {
    this.userRepository = userRepository;
    this.jwtService = jwtService;
  }


  public Optional<UserModel> create(UserModel data) throws HttpServerErrorException {
    if (this.userRepository.existsByEmail(data.getEmail())) {
      throw new EntityAlreadyExistsException("User Already Exists");
    }
    return Optional.of(this.userRepository.save(data));
  }

  public Optional<List<UserDTO>> getAll() {
    return Optional.of(this.userRepository.findAll().stream().map(UserDTO::new).toList());
  }

  public Optional<UserDTO> get(String id) {
    return this.userRepository.findById(id).map(UserDTO::new);
  }

  public Stream<UserDTO> search(String userName) {
    return this.userRepository.searchAllByUserNameContains(userName.toLowerCase()).stream().map(UserDTO::new);
  }

  public Optional<UserDTO> getByUsername(String userName){
    return this.userRepository.findByUserName(userName).map(UserDTO::new);
  }

  public UserDTO getMe(String auth) {
    Claims claims = this.jwtService.decode(auth);
    return new UserDTO(claims);
  }

  public Optional<UserModel> getByEmail(String email) {
    return Optional.ofNullable(this.userRepository.findByEmail(email));
  }

  public Optional<UserModel> verify(String id, UserModel updatedUser) {
    Optional<UserModel> user = this.userRepository.findById(id);
    user.ifPresent(userModel -> {
      UserModel foundUser = user.get();
      foundUser.setValidationCode(updatedUser.getValidationCode());
      this.userRepository.save(foundUser);
    });

    return Optional.ofNullable(user.orElseThrow(() -> new NotFoundException("User does not exist")));
  }


  public boolean delete(String id) {
    if (this.userRepository.existsById(id)) {
      this.userRepository.deleteById(id);
    } else {
      throw new NotFoundException("User does not exist");
    }
    return true;
  }
}
