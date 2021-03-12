# Roles

## Creating Roles

- prefix all with `ROLE_` (assumption of spring security methods)
  
- convention is all caps -- `ROLE_USER`, `ROLE_ADMIN`
  
- `getGrantedAuthorities` method is the key

    ```java
    import org.springframework.security.core.authority.AuthorityUtils;

    ...

    new SimpleGrantedAuthority("ROLE_WHATEVER");
    AuthorityUtils.createAuthorityList("ROLE_ONE", "ROLE_TWO");
    AuthorityUtils.NO_AUTHORITIES;
    ```

    E.g. if you have a single `String role` property on your user:

    ```java
    AuthorityUtils.createAuthorityList(this.role)
    ```

    Or if a user has many `Role`s:

    ```java
    this.roles.stream().map(role -> new SimpleGrantedAuthority(role.getName()))
    ```

## Securing Endpoints

1. with antMatchers in the security config class
   
    ```java
    // note the lack of the ROLE_ prefix here!
    http.authorizeRequests().antMatchers("/secret").hasRole("ADMIN")
    ```

1. `@Secured` on a controller method or class to restrict access based on roles

    ```java
    @Controller
    @Secured({"ROLE_ADMIN", "ROLE_SUPERUSER"})
    class MyController {
        // any methods inside of here will only be accessible by admins or superusers
    }
    ```

    ```java
    import org.springframework.web.bind.annotation.GetMapping;

    @Controller
    class MyController {
        @GetMapping("/public")
        public String publicRoute() {
            // anyone can visit this url
        }
        
        @GetMapping("/secret")
        @Secured({"ROLE_ADMIN", "ROLE_SUPERUSER"})
        public String secret() {
            // only admins and superusers can visit this url
        }
    }
    ```

1. `@PreAuthorize` on a controller method or class to restrict access based on custom expressions

    ```java
    @Controller
    @PreAuthorize("isAuthenticated()")
    class MyController {
        // only logged in users can visit endpoints defined here
    }
    ```

    ```java
    @Controller
    class MyController {
        @GetMapping("/public")
        @PreAuthorize("hasRole('ADMIN')")
        public String publicRoute() {
            // only admins can visit here
        }

        @GetMapping("/secret")
        @PreAuthorize("principal.username == 'grace_hopper'")
        public String secret() {
            // only the user with username "grace_hopper" can visit this route
        }
    }
    ```

## Other

- In views
  
    ```html
    <p>Welcome, <span th:text="${#authoization.principal.username}" />!</p>
    ```

- Inject logged in user (caveats of using this class w/ hibernate!)

    As an alternative to the `SecurityContextHolder` and casting dance.

    ```java
    @GetMapping("/somewhere")
    public String aControllerMethod(@AuthorizationPrincipal UserWithRoles loggedInUser) {
        // Do something with the logged in user
    }
    ```
  
    NB. can also inject `Authorization auth` or `Principal principal`
