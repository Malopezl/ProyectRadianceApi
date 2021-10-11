/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gt.com.api.radiance.entities;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import java.util.Objects;
import org.bson.types.ObjectId;

/**
 *
 * @author malopez
 */
@Entity(value = "role", useDiscriminator = false)
public class Roles {

    @Id
    private ObjectId id;
    private String name;
    private Integer type;
    private Boolean isDelete;

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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Boolean getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }

    @Override
    public String toString() {
        return "Roles{" + "id=" + id + ", name=" + name + ", type=" + type + ", isDelete=" + isDelete + '}';
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 11 * hash + Objects.hashCode(this.id);
        hash = 11 * hash + Objects.hashCode(this.name);
        hash = 11 * hash + Objects.hashCode(this.type);
        hash = 11 * hash + Objects.hashCode(this.isDelete);
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
        final Roles other = (Roles) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.type, other.type)) {
            return false;
        }
        return Objects.equals(this.isDelete, other.isDelete);
    }

}
