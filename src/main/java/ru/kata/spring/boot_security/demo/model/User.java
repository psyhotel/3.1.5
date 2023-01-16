package ru.kata.spring.boot_security.demo.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Set;

@Entity
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username")
    private String name;

    @Column(name = "password")
    private String password;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    private Set<Role> roleSet;

    public User() {
    }

    public User(Set<Role> roleSet) {
        this.roleSet = roleSet;
    }

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public User(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        this.name = username;
        this.password = password;
        this.roleSet = (Set<Role>) authorities;
    }

    public User(String name, String password, Set<Role> roleSet) {
        this.name = name;
        this.password = password;
        this.roleSet = roleSet;
    }

    public User(Long id, String name, Set<Role> roleSet) {
        this.id = id;
        this.name = name;
        this.roleSet = roleSet;
    }

    public User(Long id, String name, String password, Set<Role> roleSet) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.roleSet = roleSet;
    }

    public Set<Role> getRoleSet() {
        return roleSet;
    }

    public void setRoleSet(Set<Role> roleSet) {
        this.roleSet = roleSet;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getUsername() {
        return name;
    }

    public void setUsername(String name) {
        this.name = name;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + password + '\'' +
                '}';
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roleSet;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public String getRoleSetString() {

        StringBuilder s = new StringBuilder();

        for (Role r : roleSet) {
            if (s.length() != 0) {
                s.append(", ");
            }
            s.append(r.getRole());
        }
        return s.toString();
    }
}