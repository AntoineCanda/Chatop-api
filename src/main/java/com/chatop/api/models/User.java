package com.chatop.api.models;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.chatop.api.dto.UserDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * User entity class
 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
@Table(name="USERS")
public class User implements UserDetails {

    
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NonNull
    @Column(name="email" ,nullable = false, unique = true)
    private String email;

    @NonNull
    @Column(name = "name", nullable = false)
    private String name;

    @NonNull
    @Column(name="password",nullable = false)
    private String password;

    @Column(name="created_at")
    private Timestamp createdAt;

    @Column(name="updated_at")
    private Timestamp updatedAt;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "active")
    private boolean active;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return this.getEmail();
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
       return this.isActive();
    }

    public UserDTO getUserDTOFromUser(User user){
        return UserDTO.builder()
               .id(user.getId())
               .name(user.getName())
               .email(user.getEmail())
               .created_at(user.getCreatedAt().toLocalDateTime())
               .updated_at(user.getUpdatedAt().toLocalDateTime())
               .build();
    }
 
}
