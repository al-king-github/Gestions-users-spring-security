package sn.estm.Controlleurs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sn.estm.Dao.roleDao;
import sn.estm.Entites.role;

import java.util.List;

@RestController
@CrossOrigin
public class rolesControlleur {

    @Autowired
    private roleDao bd;
    @PostMapping("/role")
    public ResponseEntity<role> createRole(@RequestBody role roles)
    {
        bd.saveRole(roles);
        return new ResponseEntity <role> (roles, HttpStatus.OK);
    }
    @GetMapping("/role")
    public ResponseEntity <List<role>>  listAllRole()
    {
        List<role> liste=bd.findAllRole();
        return  new ResponseEntity<>(liste,HttpStatus.OK) ;
    }
    @GetMapping("/role/{id}")
    public ResponseEntity <role>  listAllRoleOne(@PathVariable Integer id)
    {
        role roles =bd.findRoleById(id);
        return  new ResponseEntity<>(roles,HttpStatus.OK) ;
    }
    @DeleteMapping("/role/{id}")
    public ResponseEntity <String> deleteRoleOne (@PathVariable Integer id)
    {
        bd.deleteRole(id);
        return  new ResponseEntity<>("La role  a ete bien  supprimé avec succe !",HttpStatus.OK);
    }
    @PutMapping("/role/{id}")
    public ResponseEntity <role> UpdateOneRole(@PathVariable Integer id,@RequestBody role roles)
    {
        role roleExistant = bd.findRoleById(roles.getId());
        roleExistant.setType(roles.getType());
        return  new ResponseEntity<role>(roleExistant,HttpStatus.OK) ;
    }
}
