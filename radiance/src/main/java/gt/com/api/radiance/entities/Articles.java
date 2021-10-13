/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gt.com.api.radiance.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import java.util.List;
import java.util.Objects;
import org.bson.types.ObjectId;

/**
 *
 * @author malopez
 */
@Entity(value = "Article", useDiscriminator = false)
public class Articles {

    @Id
    private ObjectId id;
    private String creationDate;
    private String tittle;
    private String content;
    private ObjectId userId;
    private String lastModifyDate;
    private String image;
    private List<ObjectId> tagsIds;
    private Boolean isDelete;
    @JsonIgnore
    private Users user;
    @JsonIgnore
    private List<Tags> tags;

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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<ObjectId> getTagsIds() {
        return tagsIds;
    }

    public void setTagsIds(List<ObjectId> tagsIds) {
        this.tagsIds = tagsIds;
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

    public List<Tags> getTags() {
        return tags;
    }

    public void setTags(List<Tags> tags) {
        this.tags = tags;
    }

    @Override
    public String toString() {
        return "Articles{" + "id=" + id + ", creationDate=" + creationDate + ", tittle=" + tittle
                + ", content=" + content + ", userId=" + userId + ", lastModifyDate=" + lastModifyDate
                + ", image=" + image + ", tagsIds=" + tagsIds + ", isDelete=" + isDelete + '}';
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + Objects.hashCode(this.id);
        hash = 67 * hash + Objects.hashCode(this.creationDate);
        hash = 67 * hash + Objects.hashCode(this.tittle);
        hash = 67 * hash + Objects.hashCode(this.content);
        hash = 67 * hash + Objects.hashCode(this.userId);
        hash = 67 * hash + Objects.hashCode(this.lastModifyDate);
        hash = 67 * hash + Objects.hashCode(this.image);
        hash = 67 * hash + Objects.hashCode(this.tagsIds);
        hash = 67 * hash + Objects.hashCode(this.isDelete);
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
        if (!Objects.equals(this.image, other.image)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.userId, other.userId)) {
            return false;
        }
        if (!Objects.equals(this.tagsIds, other.tagsIds)) {
            return false;
        }
        return Objects.equals(this.isDelete, other.isDelete);
    }

}
