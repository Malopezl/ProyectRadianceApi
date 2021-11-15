/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gt.com.api.radiance.dtos;

import java.util.List;

/**
 *
 * @author malopez
 */
public class ArticleModel {

    private String articleId;
    private String creationDate;
    private String tittle;
    private String description;
    private String content;
    private UserModel user;
    private String lastModifyDate;
    private String image;
    private List<TagModel> tags;

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

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
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

    public List<TagModel> getTags() {
        return tags;
    }

    public void setTags(List<TagModel> tags) {
        this.tags = tags;
    }

    @Override
    public String toString() {
        return "ArticleModel{" + "articleId=" + articleId + ", creationDate=" + creationDate + ", tittle=" + tittle
                + ", content=" + content + ", user=" + user + ", lastModifyDate=" + lastModifyDate + ", image=" + image
                + ", tags=" + tags + '}';
    }

}
