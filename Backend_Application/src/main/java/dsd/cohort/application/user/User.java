package dsd.cohort.application.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import dsd.cohort.application.ingredient.Ingredient;
import dsd.cohort.application.recipe.Recipe;
import dsd.cohort.application.token.Token;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

/**
 * The annotations below help with handling boilerplate code for the users entity
 * NoArgs/AllArgs use is dependent on how you create a users.
 * NoArgs will be for creating a null users if any logic needs to happen during construction
 * AllArgs will be for creating a new users with all fields
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false) // reference the column in the database
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @NotBlank
    @Email
    @Column(name = "email", unique = true)
    private String email;

    @NotBlank
    @Column(name = "password")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "user")
    private List<Token> tokens;

    @OneToMany
    private Set<Recipe> favoriteRecipes = new HashSet<>();

    @OneToMany
    private Set<Ingredient> groceryList = new HashSet<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getAuthorities();
    }

    @Override
    public String getUsername() {
        return email;
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

    public static class Builder {

        private String firstName;
        private String lastName;
        private String email;
        private String password;
        private final Role role = Role.USER;
        private List<Token> tokens = new ArrayList<>();
        private final Set<Recipe> favoriteRecipes = new HashSet<>();
        private final Set<Ingredient> groceryList = new HashSet<>();

        public Builder firstName(String name) {
            firstName = name;
            return this;
        }

        public Builder lastName(String name) {
            lastName = name;
            return this;
        }

        public Builder email(String e) {
            email = e;
            return this;
        }

        public Builder password(String pass) {
            password = pass;
            return this;
        }

        public User build() {
            return new User(this);
        }

    }

    public User(Builder builder) {
        firstName = builder.firstName;
        lastName = builder.lastName;
        email = builder.email;
        password = builder.password;
        role = builder.role;
        tokens = builder.tokens;
        favoriteRecipes = builder.favoriteRecipes;
        groceryList = builder.groceryList;
    }
}
