package com.educo.educo.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class User implements UserDetails {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name="UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name="id", updatable = false, nullable = false)
    private String id;

    @Email(message = "Invalid email address.")
    @NotBlank(message = "Email is required")
    @Column(unique = true)
    private String email;

    @NotBlank(message = "Name is required.")
    private String fullName;

    @NotBlank(message = "Password is required.")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @NotBlank(message = "Please repeat password for confirmation")
    @Transient
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String confirmPassword;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "owner")
    private List<Question> questions = new ArrayList<>();

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "owner")
    @Fetch(value = FetchMode.SUBSELECT)
    private List<Comment> comments = new ArrayList<>();

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "owner")
    @Fetch(value = FetchMode.SUBSELECT)
    private List<Vote> votes = new ArrayList<>();

    // One to many with Questions

    // One to many Comments

    // One to many Votes

    @CreationTimestamp
    @JsonIgnore
    private Date createdAt;

    @JsonIgnore
    @UpdateTimestamp
    private Date updatedAt;

    /* UserDetails interface methods */

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    @JsonIgnore
    public String getUsername() {
        return id;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return true;
    }
}
