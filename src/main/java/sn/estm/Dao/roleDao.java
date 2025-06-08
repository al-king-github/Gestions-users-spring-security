package sn.estm.Dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sn.estm.Entites.role;
import sn.estm.Repository.roleRepository;

import java.util.List;
import java.util.Optional;

@Service
public class roleDao {
    @Autowired
    private roleRepository repo;
    public role saveRole(role Role)
    {
        return repo.save(Role);
    }

    public List<role> findAllRole()
    {
        return repo.findAll();
    }

    public role findRoleById(Integer id)
    {
        Optional<role> optionalRole = repo.findById(id);
        return optionalRole.get();
    }
    public void deleteRole (Integer id)
    {
        repo.deleteById(id);


    }
    public role updateRole(role roles)
    {
        role roleExistant = findRoleById(roles.getId());
        roleExistant.setType(roles.getType());
        return repo.save(roleExistant);
    }
}
