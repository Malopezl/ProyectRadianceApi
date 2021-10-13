/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gt.com.api.radiance.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import java.util.Objects;
import org.bson.types.ObjectId;

/**
 *
 * @author malopez
 */
@Entity(value = "Comment", useDiscriminator = false)
public class Comments {

    @Id
    private ObjectId id;
    private String comment;
    private String creationDate;
    private ObjectId userId;
    private ObjectId articleId;
    private Boolean isDelete;
    @JsonIgnore
    private Users user;

    public Comments() {
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public ObjectId getUserId() {
        return userId;
    }

    public void setUserId(ObjectId userId) {
        this.userId = userId;
    }

    public ObjectId getArticleId() {
        return articleId;
    }

    public void setArticleId(ObjectId articleId) {
        this.articleId = articleId;
    }

    public Boolean getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Comments{" + "id=" + id + ", comment=" + comment + ", creationDate=" + creationDate
                + ", userId=" + userId + ", articleId=" + articleId + ", isDelete=" + isDelete + '}';
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + Objects.hashCode(this.id);
        hash = 89 * hash + Objects.hashCode(this.comment);
        hash = 89 * hash + Objects.hashCode(this.creationDate);
        hash = 89 * hash + Objects.hashCode(this.userId);
        hash = 89 * hash + Objects.hashCode(this.articleId);
        hash = 89 * hash + Objects.hashCode(this.isDelete);
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
        final Comments other = (Comments) obj;
        if (!Objects.equals(this.comment, other.comment)) {
            return false;
        }
        if (!Objects.equals(this.creationDate, other.creationDate)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.userId, other.userId)) {
            return false;
        }
        if (!Objects.equals(this.articleId, other.articleId)) {
            return false;
        }
        return Objects.equals(this.isDelete, other.isDelete);
    }

}
