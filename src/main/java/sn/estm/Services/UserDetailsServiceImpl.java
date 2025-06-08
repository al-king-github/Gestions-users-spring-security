package sn.estm.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import sn.estm.Entites.users;
import sn.estm.Repository.usersRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private usersRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Recherche de l'utilisateur par email
        users user = userRepository.findByEmail(username);

        // Gestion du cas où l'utilisateur n'est pas trouvé
        if (user == null) {
            System.out.println("Utilisateur introuvable pour l'email : " + username);
            throw new UsernameNotFoundException("Aucun utilisateur trouvé avec l'email : " + username);
        }
        System.out.println("Utilisateur trouvé : " + user.getEmail());

        // Retourne un UserDetails à partir de l'utilisateur trouvé
        return new UserDetailsImpl(user);
    }
}
