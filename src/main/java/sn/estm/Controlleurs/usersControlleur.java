package sn.estm.Controlleurs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sn.estm.Dao.usersDao;
import sn.estm.Entites.users;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@RestController
public class usersControlleur {

    @Autowired
    private usersDao bd;
    private ConcurrentHashMap<String, Boolean> onlineUsers = new ConcurrentHashMap<>();

    @PostMapping("/addUser")
    public ResponseEntity<users> createUser(@RequestBody users user) {
        bd.saveUsers(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
    @GetMapping("/user")
    public ResponseEntity <List<users>>  listAllUser()
    {
        List<users> liste=bd.findAllUsers();
        return  new ResponseEntity<>(liste,HttpStatus.OK) ;
    }
    // Liste seulement les utilisateurs en ligne
    @GetMapping("/users/online")
    public ResponseEntity<?> getOnlineUsers() {
        return ResponseEntity.ok(onlineUsers.keySet());
    }
    @GetMapping("/user/{id}")
    public ResponseEntity <users>  listAllUsersOne(@PathVariable Integer id)
    {
        users user =bd.findUsersById(id);
        return  new ResponseEntity<>(user,HttpStatus.OK) ;
    }
    @DeleteMapping("/user/{id}")
    public ResponseEntity <String> deleteUserOne (@PathVariable Integer id)
    {
        bd.deleteUsers(id);
        System.out.println("l'utisateur a ete bien  supprimé avec succe id=" + id);
        return  new ResponseEntity<>(HttpStatus.OK);
    }
    @PutMapping("/user/{id}")
    public ResponseEntity <users> UpdateOneUsers(@PathVariable Integer id,@RequestBody users user)
    {
        users userExistant = bd.findUsersById(user.getId());
        userExistant.setNom(user.getNom());
        userExistant.setPrenom(user.getPrenom());
        userExistant.setEmail(user.getEmail());
        userExistant.setPassword(user.getPassword());
        userExistant.setRole(user.getRole());
        userExistant.setAdresse(user.getAdresse());
        userExistant.setTelephone(user.getTelephone());
        userExistant.setDateCreation(user.getDateCreation());
        userExistant.setStatus(user.getStatus());
        userExistant.setPhotoProfil(user.getPhotoProfil());
        users updatedUser = bd.saveUsers(userExistant);
        System.out.println("L'utilisateur a été bien modifié avec succès, id=" + id);
        return  new ResponseEntity<users>(updatedUser,HttpStatus.OK) ;
    }
}

