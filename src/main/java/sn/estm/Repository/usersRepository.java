package sn.estm.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sn.estm.Entites.users;

public interface usersRepository extends JpaRepository <users, Integer> {
    users findByEmail(String username);
    users findByToken(String token);
}
