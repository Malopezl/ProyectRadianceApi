/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gt.com.api.radiance.controllers;

import gt.com.api.radiance.dtos.ArticleModel;
import gt.com.api.radiance.dtos.TagModel;
import gt.com.api.radiance.dtos.UserLoad;
import gt.com.api.radiance.dtos.UserModel;
import gt.com.api.radiance.entities.Article;
import gt.com.api.radiance.entities.User;
import gt.com.api.radiance.helper.FormatDate;
import gt.com.api.radiance.queries.ArticleQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author malopez
 */
public class ArticleController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ArticleController.class);

    public ArticleController() {
    }

    public List<ArticleModel> getArticles(String filter, String filterTag) {
        Pattern regexp = Pattern.compile(filter, Pattern.CASE_INSENSITIVE);
        List<Article> articleList = ArticleQuery.getArticleList(regexp);
        if (articleList == null) {
            return null;
        }
//        TagController tagController = new TagController();
        List<ArticleModel> articles = new ArrayList();
        articleList.stream().map(article -> {
            ArticleModel articleModel = new ArticleModel();
            articleModel.setArticleId(article.getId().toString());
            articleModel.setCreationDate(FormatDate.convertTime(article.getCreationDate()));
            articleModel.setTittle(article.getTittle());
            articleModel.setDescription(article.getDescription());
            articleModel.setContent(article.getContent());
            UserModel user = new UserModel();
            User u = article.getUser().get(0);
            user.setUserId(u.getId().toString());
            user.setName(u.getName());
            user.setMail(u.getMail());
            user.setImage(u.getImage());
            articleModel.setUser(user);
            articleModel.setLastModifyDate(FormatDate.convertTime(article.getLastModifyDate()));
            articleModel.setImage(article.getImage());
            List<TagModel> tags = new ArrayList();
            article.getTags().stream().map(tag -> {
                TagModel tagModel = new TagModel();
                tagModel.setName(tag.getName());
                tagModel.setColor(tag.getColor());
                tagModel.setIcon(tag.getIcon());
                return tagModel;
            }).forEachOrdered(tagModel -> {
                tags.add(tagModel);
            });
            articleModel.setTags(tags);
            return articleModel;
        }).forEachOrdered(articleModel -> {
            articles.add(articleModel);
        });
        return articles;
    }

    public ArticleModel getArticle(String id) throws Exception {
        UserController userController = new UserController();
        TagController tagController = new TagController();
        ObjectId articleId = new ObjectId(id);
        Article article = ArticleQuery.findArticle(articleId);
        if (article == null) {
            return null;
        }
        ArticleModel articleModel = new ArticleModel();
        articleModel.setArticleId(article.getId().toString());
        articleModel.setCreationDate(FormatDate.convertTime(article.getCreationDate()));
        articleModel.setTittle(article.getTittle());
        articleModel.setDescription(article.getDescription());
        articleModel.setContent(article.getContent());
        UserModel user = userController.getUser(article.getUserId().toString());
        articleModel.setUser(user);
        articleModel.setLastModifyDate(FormatDate.convertTime(article.getLastModifyDate()));
        articleModel.setImage(article.getImage());
        List<TagModel> tags = tagController.findTags(article.getTagsId(), "");
        if (tags == null) {
            LOGGER.error("Cannot get tag list");
            throw new Exception("Unable to get tag list");
        }
        articleModel.setTags(tags);
        return articleModel;
    }

    public ArticleModel saveArticle(ArticleModel articleModel, UserLoad userLoad) {
        UserController userController = new UserController();
        Article article = new Article();
        article.setCreationDate(String.valueOf(System.currentTimeMillis()));
        article.setTittle(articleModel.getTittle());
        article.setDescription(articleModel.getDescription());
        article.setContent(articleModel.getContent());
        UserModel user = userController.findUsername(userLoad.getUser());
        article.setUserId(new ObjectId(user.getUserId()));
        article.setLastModifyDate(String.valueOf(System.currentTimeMillis()));
        article.setImage(articleModel.getImage());
        List<ObjectId> tags = new ArrayList();
        articleModel.getTags().forEach(tag -> {
            tags.add(new ObjectId(tag.getTagId()));
        });
        article.setTagsId(tags);
        article.setIsDelete(Boolean.FALSE);
        ObjectId articleId = ArticleQuery.saveArticle(article);
        if (articleId == null) {
            LOGGER.error("Failed to save article");
            return null;
        }
        articleModel.setUser(user);
        articleModel.setCreationDate(FormatDate.convertTime(article.getCreationDate()));
        articleModel.setLastModifyDate(FormatDate.convertTime(article.getLastModifyDate()));
        articleModel.setArticleId(articleId.toString());
        return articleModel;
    }

    public boolean verifyArticleExists(String id) {
        ObjectId articleId = new ObjectId(id);
        return ArticleQuery.verifyArticleExists(articleId);
    }

    public ArticleModel updateArticle(String id, ArticleModel articleModel) {
        ObjectId articleId = new ObjectId(id);
        Article article = new Article();
        article.setTittle(articleModel.getTittle());
        article.setDescription(articleModel.getDescription());
        article.setContent(articleModel.getContent());
        article.setLastModifyDate(String.valueOf(System.currentTimeMillis()));
        article.setImage(articleModel.getImage());
        List<ObjectId> tags = new ArrayList();
        articleModel.getTags().forEach(tag -> {
            tags.add(new ObjectId(tag.getTagId()));
        });
        article.setTagsId(tags);
        article = ArticleQuery.updateArticle(article, articleId);
        if (article == null) {
            LOGGER.error("Unable to update the article");
            return null;
        }
        articleModel.setLastModifyDate(FormatDate.convertTime(article.getLastModifyDate()));
        articleModel.setArticleId(id);
        return articleModel;
    }

    public Boolean deleteArticle(String id) {
        ObjectId articleId = new ObjectId(id);
        return ArticleQuery.deleteArticle(articleId);
    }

}
