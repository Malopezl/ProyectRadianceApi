/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gt.com.api.radiance.queries;

import dev.morphia.Datastore;
import dev.morphia.aggregation.experimental.stages.Lookup;
import dev.morphia.aggregation.experimental.stages.Unwind;
import dev.morphia.query.MorphiaCursor;
import dev.morphia.query.experimental.filters.Filters;
import gt.com.api.radiance.entities.Comment;
import java.util.List;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author malopez
 */
public final class CommentQuery {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommentQuery.class);
    private static Datastore ds;

    private CommentQuery() {
    }

    public static void setDataStore(Datastore datastore) {
        ds = datastore;
    }

    public static List<Comment> getCommentList(ObjectId articleId) {
        try {
            MorphiaCursor<Comment> pipeline = ds.aggregate(Comment.class)
                    .match(Filters.eq("articleId", articleId), Filters.eq("isDelete", Boolean.FALSE))
                    .sort(dev.morphia.aggregation.experimental.stages.Sort.sort().descending("_id"))
                    .lookup(Lookup.lookup("User").localField("userId")
                            .foreignField("_id").as("user"))
                    .unwind(Unwind.unwind("user"))
                    .execute(Comment.class);
            return pipeline.toList();
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return null;
        }
    }

    public static ObjectId saveComment(Comment comment) {
        try {
            return ds.save(comment).getId();
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return null;
        }
    }

}
