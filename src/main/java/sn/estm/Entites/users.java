package sn.estm.Entites;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name="users")
public class users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(length = 100)
    private String nom;
    @Column(length = 100)
    private String prenom;
    @Column(length = 100)
    private String email;
    @Column(length = 100)
    private String password;
    @Column(length = 100)
    private String telephone;
    @Column(length = 100)
    private String adresse;
    @Column(length = 100)
    private String photoProfil;
    private Boolean status;
    @Column(length = 100)
    private String token;
    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime DateCreation;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }



    @PrePersist
    public void prePersist() {
        this.DateCreation = LocalDateTime.now();
        if (this.photoProfil == null || this.photoProfil.isEmpty()) {
            this.photoProfil = "/images/default.png";
        }
        this.status = true;
    }

    public String getPhotoProfil() {
        return photoProfil;
    }

    public void setPhotoProfil(String photoProfil) {
        this.photoProfil = photoProfil;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }


    public LocalDateTime getDateCreation() {
        return DateCreation;
    }

    public void setDateCreation(LocalDateTime dateCreation) {
        DateCreation = dateCreation;
    }



    @ManyToOne
    @JoinColumn(name = "role_id")
    private role role;

    public role getRole() {
        return role;
    }

    public void setRole(role role) {
        this.role = role;
    }

    public Integer getId() {
        return id;
    }
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
