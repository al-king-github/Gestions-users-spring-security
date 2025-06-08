package sn.estm.Dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sn.estm.Entites.users;
import sn.estm.Repository.usersRepository;

import java.util.List;
import java.util.Optional;

@Service
public class usersDao {
    @Autowired
    private usersRepository repo;
    public users saveUsers(users user)
    {
        return repo.save(user);
    }
    public List<users> findAllUsers()
    {
        return repo.findAll();
    }
    public users findUsersById(Integer id)
    {
        Optional<users> optionalUsers = repo.findById(id);
        return optionalUsers.get();
    }
    public void deleteUsers (Integer id)
    {
        repo.deleteById(id);
    }
    public users updateUsers(users user) {
        users usersExistant = findUsersById(user.getId());
        usersExistant.setNom(user.getNom());
        usersExistant.setPrenom(user.getPrenom());
        usersExistant.setEmail(user.getEmail());
        usersExistant.setPassword(user.getPassword());
        usersExistant.setRole(user.getRole());
        usersExistant.setAdresse(user.getAdresse());
        usersExistant.setTelephone(user.getTelephone());
        usersExistant.setStatus(user.getStatus());
        usersExistant.setPhotoProfil(user.getPhotoProfil());
        usersExistant.setDateCreation(user.getDateCreation());
        return repo.save(usersExistant);
    }
}
