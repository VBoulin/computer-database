package com.excilys.formation.java.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user")
public class User {
  @Id
  @GeneratedValue
  @Column(name = "id")
  private long    id;
  @Column(name = "username")
  private String  username;
  @Column(name = "password")
  private String  password;
  @Column(name = "role")
  private String  role;
  @Column(name = "enabled")
  private boolean enabled;
  @Column(name = "accountNonExpired")
  private boolean accountNonExpired;
  @Column(name = "credentialsNonExpired")
  private boolean credentialsNonExpired;
  @Column(name = "accountNonLocked")
  private boolean accountNonLocked;

  //------------------------------
  //Constructors
  //------------------------------

  public User() {
    super();
    // TODO Auto-generated constructor stub
  }

  public User(long id, String username, String password, String role, boolean enabled,
      boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked) {
    super();
    this.id = id;
    this.username = username;
    this.password = password;
    this.role = role;
    this.enabled = enabled;
    this.accountNonExpired = accountNonExpired;
    this.credentialsNonExpired = credentialsNonExpired;
    this.accountNonLocked = accountNonLocked;
  }

  //------------------------------
  //Getters & setters
  //------------------------------

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }

  public boolean isEnabled() {
    return enabled;
  }

  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }

  public boolean isAccountNonExpired() {
    return accountNonExpired;
  }

  public void setAccountNonExpired(boolean accountNonExpired) {
    this.accountNonExpired = accountNonExpired;
  }

  public boolean isCredentialsNonExpired() {
    return credentialsNonExpired;
  }

  public void setCredentialsNonExpired(boolean credentialsNonExpired) {
    this.credentialsNonExpired = credentialsNonExpired;
  }

  public boolean isAccountNonLocked() {
    return accountNonLocked;
  }

  public void setAccountNonLocked(boolean accountNonLocked) {
    this.accountNonLocked = accountNonLocked;
  }

  //------------------------------
  //hashcode & equals & toString
  //------------------------------

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("User [id=");
    builder.append(id);
    builder.append(", username=");
    builder.append(username);
    builder.append(", password=");
    builder.append(password);
    builder.append(", role=");
    builder.append(role);
    builder.append(", enabled=");
    builder.append(enabled);
    builder.append(", accountNonExpired=");
    builder.append(accountNonExpired);
    builder.append(", credentialsNonExpired=");
    builder.append(credentialsNonExpired);
    builder.append(", accountNonLocked=");
    builder.append(accountNonLocked);
    builder.append("]");
    return builder.toString();
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + (accountNonExpired ? 1231 : 1237);
    result = prime * result + (accountNonLocked ? 1231 : 1237);
    result = prime * result + (credentialsNonExpired ? 1231 : 1237);
    result = prime * result + (enabled ? 1231 : 1237);
    result = prime * result + (int) (id ^ (id >>> 32));
    result = prime * result + ((password == null) ? 0 : password.hashCode());
    result = prime * result + ((role == null) ? 0 : role.hashCode());
    result = prime * result + ((username == null) ? 0 : username.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    User other = (User) obj;
    if (accountNonExpired != other.accountNonExpired)
      return false;
    if (accountNonLocked != other.accountNonLocked)
      return false;
    if (credentialsNonExpired != other.credentialsNonExpired)
      return false;
    if (enabled != other.enabled)
      return false;
    if (id != other.id)
      return false;
    if (password == null) {
      if (other.password != null)
        return false;
    } else if (!password.equals(other.password))
      return false;
    if (role == null) {
      if (other.role != null)
        return false;
    } else if (!role.equals(other.role))
      return false;
    if (username == null) {
      if (other.username != null)
        return false;
    } else if (!username.equals(other.username))
      return false;
    return true;
  }

}
