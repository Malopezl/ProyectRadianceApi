/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gt.com.api.radiance.dtos;

import gt.com.api.radiance.entities.Tag;
import gt.com.api.radiance.entities.User;
import java.util.List;

/**
 *
 * @author malopez
 */
public class ArticleModel {

    private String articleId;
    private String creationDate;
    private String tittle;
    private String content;
    private User user;
    private String lastModifyDate;
    private String image;
    private List<Tag> tags;

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    @Override
    public String toString() {
        return "ArticleModel{" + "articleId=" + articleId + ", creationDate=" + creationDate + ", tittle=" + tittle
                + ", content=" + content + ", user=" + user + ", lastModifyDate=" + lastModifyDate + ", image=" + image
                + ", tags=" + tags + '}';
    }

}
