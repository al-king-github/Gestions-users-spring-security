package sn.estm.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sn.estm.Entites.role;

public interface roleRepository extends JpaRepository<role , Integer>{
    role findByType(String type);
}
