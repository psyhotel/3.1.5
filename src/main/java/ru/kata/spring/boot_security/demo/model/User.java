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

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "password")
    private String password;

    @Column(name = "age")
    private int age;

    @Column(name = "email")
    private String email;

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

    public User(String name, String password, Collection<? extends GrantedAuthority> authorities) {
        this.name = name;
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

    public User(Long id, String name, String surname, String password, int age, String email, Set<Role> roleSet) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.age = age;
        this.email = email;
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

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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