package pl.edu.pg.apkademikbackend.user.model;

import pl.edu.pg.apkademikbackend.WebSecurity.model.Role;

import java.util.Set;

public class UserWithRoles {
    private long id;
    private String name;
    private String surname;
    private String email;
    private Set<Role> roles;

    public UserWithRoles() {
    }

    public UserWithRoles(long id, String name, String surname, String email, Set<Role> roles) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.roles = roles;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
