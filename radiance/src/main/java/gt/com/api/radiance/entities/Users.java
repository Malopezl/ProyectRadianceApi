/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gt.com.api.radiance.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import gt.com.api.radiance.dto.Suscriptions;
import java.util.Objects;
import org.bson.types.ObjectId;

/**
 *
 * @author malopez
 */
@Entity(value = "User", useDiscriminator = false)
public class Users {

    @Id
    private ObjectId id;
    private String name;
    private String mail;
    private String phoneNumber;
    private String role;
    private String image;
    private String password;
    private String user;
    private Suscriptions suscription;
    private Boolean isDelete;
    @JsonIgnore
    private SuscriptionTypes suscriptionType;

    public Users() {
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Suscriptions getSuscription() {
        return suscription;
    }

    public void setSuscription(Suscriptions suscription) {
        this.suscription = suscription;
    }

    public Boolean getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }

    public SuscriptionTypes getSuscriptionType() {
        return suscriptionType;
    }

    public void setSuscriptionType(SuscriptionTypes suscriptionType) {
        this.suscriptionType = suscriptionType;
    }

    @Override
    public String toString() {
        return "Users{" + "id=" + id + ", name=" + name + ", mail=" + mail + ", phoneNumber=" + phoneNumber
                + ", role=" + role + ", image=" + image + ", password=" + password + ", user=" + user
                + ", suscription=" + suscription + ", isDelete=" + isDelete + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + Objects.hashCode(this.id);
        hash = 41 * hash + Objects.hashCode(this.name);
        hash = 41 * hash + Objects.hashCode(this.mail);
        hash = 41 * hash + Objects.hashCode(this.phoneNumber);
        hash = 41 * hash + Objects.hashCode(this.role);
        hash = 41 * hash + Objects.hashCode(this.image);
        hash = 41 * hash + Objects.hashCode(this.password);
        hash = 41 * hash + Objects.hashCode(this.user);
        hash = 41 * hash + Objects.hashCode(this.suscription);
        hash = 41 * hash + Objects.hashCode(this.isDelete);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Users other = (Users) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.mail, other.mail)) {
            return false;
        }
        if (!Objects.equals(this.phoneNumber, other.phoneNumber)) {
            return false;
        }
        if (!Objects.equals(this.role, other.role)) {
            return false;
        }
        if (!Objects.equals(this.image, other.image)) {
            return false;
        }
        if (!Objects.equals(this.password, other.password)) {
            return false;
        }
        if (!Objects.equals(this.user, other.user)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.suscription, other.suscription)) {
            return false;
        }
        return Objects.equals(this.isDelete, other.isDelete);
    }

}
