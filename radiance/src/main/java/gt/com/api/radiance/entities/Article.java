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
public class Article {

    @Id
    private ObjectId id;
    private String creationDate;
    private String tittle;
    private String description;
    private String content;
    private ObjectId userId;
    private String lastModifyDate;
    private String image;
    private List<ObjectId> tagsId;
    private Boolean isDelete;
    @JsonIgnore
    private List<User> user;
    @JsonIgnore
    private List<Tag> tags;

    public Article() {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public List<ObjectId> getTagsId() {
        return tagsId;
    }

    public void setTagsId(List<ObjectId> tagsId) {
        this.tagsId = tagsId;
    }

    public Boolean getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }

    public List<User> getUser() {
        return user;
    }

    public void setUser(List<User> user) {
        this.user = user;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    @Override
    public String toString() {
        return "Article{" + "id=" + id + ", creationDate=" + creationDate + ", tittle=" + tittle
                + ", content=" + content + ", userId=" + userId + ", lastModifyDate=" + lastModifyDate
                + ", image=" + image + ", tagsId=" + tagsId + ", isDelete=" + isDelete + ", user=" + user
                + ", tags=" + tags + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + Objects.hashCode(this.id);
        hash = 41 * hash + Objects.hashCode(this.creationDate);
        hash = 41 * hash + Objects.hashCode(this.tittle);
        hash = 41 * hash + Objects.hashCode(this.content);
        hash = 41 * hash + Objects.hashCode(this.userId);
        hash = 41 * hash + Objects.hashCode(this.lastModifyDate);
        hash = 41 * hash + Objects.hashCode(this.image);
        hash = 41 * hash + Objects.hashCode(this.tagsId);
        hash = 41 * hash + Objects.hashCode(this.isDelete);
        hash = 41 * hash + Objects.hashCode(this.user);
        hash = 41 * hash + Objects.hashCode(this.tags);
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
        final Article other = (Article) obj;
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
        if (!Objects.equals(this.tagsId, other.tagsId)) {
            return false;
        }
        if (!Objects.equals(this.isDelete, other.isDelete)) {
            return false;
        }
        if (!Objects.equals(this.user, other.user)) {
            return false;
        }
        return Objects.equals(this.tags, other.tags);
    }

}
