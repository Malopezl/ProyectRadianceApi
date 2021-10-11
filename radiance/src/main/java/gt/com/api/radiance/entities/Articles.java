/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gt.com.api.radiance.entities;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import java.util.List;
import java.util.Objects;
import org.bson.types.ObjectId;

/**
 *
 * @author malopez
 */
@Entity(value = "article", useDiscriminator = false)
public class Articles {

    @Id
    private ObjectId id;
    private String creationDate;
    private String tittle;
    private String content;
    private ObjectId userId;
    private String lastModifyDate;
    private List<Tags> tag;
    private Boolean isDelete;

    public Articles() {
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ObjectId getUserId() {
        return userId;
    }

    public void setUserId(ObjectId userId) {
        this.userId = userId;
    }

    public String getLastModifyDate() {
        return lastModifyDate;
    }

    public void setLastModifyDate(String lastModifyDate) {
        this.lastModifyDate = lastModifyDate;
    }

    public List<Tags> getTag() {
        return tag;
    }

    public void setTag(List<Tags> tag) {
        this.tag = tag;
    }

    public Boolean getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }

    @Override
    public String toString() {
        return "Articles{" + "id=" + id + ", creationDate=" + creationDate + ", tittle=" + tittle
                + ", content=" + content + ", userId=" + userId + ", lastModifyDate=" + lastModifyDate
                + ", tag=" + tag + ", isDelete=" + isDelete + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 13 * hash + Objects.hashCode(this.id);
        hash = 13 * hash + Objects.hashCode(this.creationDate);
        hash = 13 * hash + Objects.hashCode(this.tittle);
        hash = 13 * hash + Objects.hashCode(this.content);
        hash = 13 * hash + Objects.hashCode(this.userId);
        hash = 13 * hash + Objects.hashCode(this.lastModifyDate);
        hash = 13 * hash + Objects.hashCode(this.tag);
        hash = 13 * hash + Objects.hashCode(this.isDelete);
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
        final Articles other = (Articles) obj;
        if (!Objects.equals(this.creationDate, other.creationDate)) {
            return false;
        }
        if (!Objects.equals(this.tittle, other.tittle)) {
            return false;
        }
        if (!Objects.equals(this.content, other.content)) {
            return false;
        }
        if (!Objects.equals(this.lastModifyDate, other.lastModifyDate)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.userId, other.userId)) {
            return false;
        }
        if (!Objects.equals(this.tag, other.tag)) {
            return false;
        }
        return Objects.equals(this.isDelete, other.isDelete);
    }

}
