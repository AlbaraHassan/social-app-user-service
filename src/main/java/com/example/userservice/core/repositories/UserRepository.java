package com.example.userservice.core.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.example.userservice.rest.models.UserModel;

import java.util.List;
import java.util.Optional;


@Repository
public interface UserRepository extends MongoRepository<UserModel, String> {
  boolean existsByEmail(String email);
  List<UserModel> searchAllByUserNameContains(String userName);
  Optional<UserModel> findByUserName(String userName);
  UserModel findByEmail(String email);
}
