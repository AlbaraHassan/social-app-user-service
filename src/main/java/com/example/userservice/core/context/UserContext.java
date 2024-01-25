package com.example.userservice.core.context;


import org.springframework.stereotype.Component;
import com.example.userservice.rest.dtos.UserDTO;
import com.example.userservice.rest.enums.Role;

@Component
public class UserContext {
  private final ThreadLocal<UserDTO> userContextThreadLocal = new ThreadLocal<>();

  public String getId() {
    return this.userContextThreadLocal.get().getId();
  }
  public Role getRole() {
    return this.userContextThreadLocal.get().getRole();
  }

  public String getEmail() {
    return this.userContextThreadLocal.get().getEmail();
  }

  public String getUsername() {
    return this.userContextThreadLocal.get().getUserName();
  }

  public void setUser(UserDTO user) {
    this.userContextThreadLocal.set(user);
  }

  public void clear() {
    this.userContextThreadLocal.remove();
  }
}
