/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gt.com.api.radiance.dto;

import gt.com.api.radiance.entities.Tags;
import gt.com.api.radiance.entities.Users;
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
    private Users user;
    private String lastModifyDate;
    private String image;
    private List<Tags> tags;

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

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
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

    public List<Tags> getTags() {
        return tags;
    }

    public void setTags(List<Tags> tags) {
        this.tags = tags;
    }

    @Override
    public String toString() {
        return "ArticleModel{" + "articleId=" + articleId + ", creationDate=" + creationDate + ", tittle=" + tittle
                + ", content=" + content + ", user=" + user + ", lastModifyDate=" + lastModifyDate + ", image=" + image
                + ", tags=" + tags + '}';
    }

}
