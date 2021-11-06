/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gt.com.api.radiance.queries;

import com.mongodb.client.model.ReturnDocument;
import dev.morphia.Datastore;
import dev.morphia.ModifyOptions;
import dev.morphia.aggregation.experimental.stages.Lookup;
import dev.morphia.query.MorphiaCursor;
import dev.morphia.query.Query;
import dev.morphia.query.experimental.filters.Filters;
import dev.morphia.query.experimental.updates.UpdateOperators;
import gt.com.api.radiance.entities.Article;
import java.util.List;
import java.util.regex.Pattern;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author malopez
 */
public final class ArticleQuery {

    private static final Logger LOGGER = LoggerFactory.getLogger(ArticleQuery.class);
    private static Datastore ds;

    private ArticleQuery() {
    }

    public static void setDataStore(Datastore datastore) {
        ds = datastore;
    }

    public static List<Article> getArticleList(Pattern regexp) {
        try {
            LOGGER.info(regexp.pattern());
            LOGGER.info(regexp.toString());
            MorphiaCursor<Article> pipeline = ds.aggregate(Article.class)
                    .match(Filters.eq("isDelete", Boolean.FALSE))
                    .sort(dev.morphia.aggregation.experimental.stages.Sort.sort().ascending("tittle"))
                    .lookup(Lookup.lookup("User").localField("userId")
                            .foreignField("_id").as("user"))
                    .lookup(Lookup.lookup("Tag").localField("tagsId")
                            .foreignField("_id").as("tags"))
//                    .unwind(Unwind.unwind("tags"))
                    .match(Filters.or(Filters.eq("tittle", regexp), Filters.eq("tags.name", regexp)))
                    .execute(Article.class);
            return pipeline.toList();
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return null;
        }
    }

    public static Article findArticle(ObjectId id) {
        Query<Article> getArticle = ds.find(Article.class)
                .filter(Filters.eq("_id", id), Filters.eq("isDelete", false), Filters.ne("userId", null),
                        Filters.ne("tagsId", null));
        try {
            return getArticle.first();
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return null;
        }
    }

    public static ObjectId saveArticle(Article article) {
        try {
            return ds.save(article).getId();
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return null;
        }
    }

    public static Boolean verifyArticleExists(ObjectId id) {
        Query<Article> verifyArticle = ds.find(Article.class)
                .filter(Filters.and(Filters.eq("_id", id), Filters.eq("isDelete", false)));
        return verifyArticle.first() != null;
    }

    public static Article updateArticle(Article article, ObjectId id) {
        try {
            Article updateArticle = ds.find(Article.class)
                    .filter(Filters.eq("_id", id))
                    .modify(
                            UpdateOperators.set("tittle", article.getTittle()),
                            UpdateOperators.set("content", article.getContent()),
                            UpdateOperators.set("lastModifyDate", article.getLastModifyDate()),
                            UpdateOperators.set("image", article.getImage()),
                            UpdateOperators.set("tagsId", article.getTagsId())
                    )
                    .execute(new ModifyOptions().returnDocument(ReturnDocument.AFTER));
            return updateArticle;
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return null;
        }
    }

    public static Boolean deleteArticle(ObjectId id) {
        try {
            ds.find(Article.class).filter(Filters.eq("_id", id))
                    .modify(
                            UpdateOperators.set("isDelete", Boolean.TRUE)
                    )
                    .execute(new ModifyOptions().returnDocument(ReturnDocument.AFTER));
            return true;
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return false;
        }
    }

}
