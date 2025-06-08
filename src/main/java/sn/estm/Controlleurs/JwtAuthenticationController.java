package sn.estm.Controlleurs;



import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import sn.estm.Entites.users;
import sn.estm.Services.UserDetailsImpl;
import sn.estm.jwtTokenUtils.JwtTokenUtil;


@RestController
@CrossOrigin("http://localhost:4200")
public class JwtAuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private UserDetailsService userDetailsService;
    private ConcurrentHashMap<String, Boolean> onlineUsers = new ConcurrentHashMap<>();

    @PostMapping(value = "/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody users user) {
        System.out.println("Email fourni : " + user.getEmail());
        System.out.println("Mot de passe fourni : " + user.getPassword());
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
            final UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
            final String token = jwtTokenUtil.generateToken(userDetails);
            final String role = ((UserDetailsImpl) userDetails).getRole();
            Map<String, String> response = new HashMap<>();
            response.put("role", role);
            response.put("token", token);
            System.out.println("Rôle de l'utilisateur : " + userDetails.getAuthorities());
            System.out.println("Token reçu : " + token);
            onlineUsers.put(user.getEmail(), true);
            return ResponseEntity.ok(response);
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>("Identifiants incorrects", HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }


    @PostMapping("/Logout")
    public ResponseEntity<?> Logout(Authentication authentication) {
        String username = authentication.getName();

        //Retirer l'utilisateur de la liste des utilisateurs en ligne
        onlineUsers.remove(username);

        return ResponseEntity.ok("Déconnexion réussie");
    }

}
