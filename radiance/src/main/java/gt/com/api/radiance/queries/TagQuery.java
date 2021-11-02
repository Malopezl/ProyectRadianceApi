/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gt.com.api.radiance.queries;

import com.mongodb.client.model.ReturnDocument;
import dev.morphia.Datastore;
import dev.morphia.ModifyOptions;
import dev.morphia.query.Query;
import dev.morphia.query.experimental.filters.Filters;
import dev.morphia.query.experimental.updates.UpdateOperators;
import gt.com.api.radiance.entities.Tag;
import java.util.List;
import java.util.regex.Pattern;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author malopez
 */
public final class TagQuery {

    private static final Logger LOGGER = LoggerFactory.getLogger(TagQuery.class);
    private static Datastore ds;

    private TagQuery() {
    }

    public static void setDataStore(Datastore datastore) {
        ds = datastore;
    }

    public static List<Tag> getTagList(Pattern regexp) {
//        int p = size * (page - 1);
//        FindOptions findOptions = new FindOptions().skip(p).limit(size);
        Query<Tag> getTags = ds.find(Tag.class).filter(Filters.eq("isDelete", Boolean.FALSE))
                .filter(Filters.or(Filters.eq("name", regexp), Filters.eq("color", regexp)));
        try {
            return getTags.iterator().toList();
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return null;
        }
    }

//    public static Long totalPages() {
//        Long pages = 0L;
//        Query<Tag> getTagsCount = ds.find(Tag.class).filter(Filters.eq("isDelete", Boolean.FALSE));
//        try {
//            pages = Long.valueOf(getTagsCount.iterator().toList().size());
//        } catch (Exception e) {
//            LOGGER.error(e.getMessage());
//        }
//        return pages;
//    }

    public static Tag findTag(ObjectId id) {
        Query<Tag> getTag = ds.find(Tag.class)
                .filter(Filters.and(Filters.eq("_id", id), Filters.eq("isDelete", false)));
        try {
            return getTag.first();
        } catch (Exception e) {
            return null;
        }
    }

    public static ObjectId saveTag(Tag tag) {
        try {
            return ds.save(tag).getId();
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return null;
        }
    }

    public static Boolean verifyTagExists(ObjectId id) {
        Query<Tag> verifyTag = ds.find(Tag.class)
                .filter(Filters.and(Filters.eq("_id", id), Filters.eq("isDelete", false)));
        return verifyTag.first() != null;
    }

    public static Tag updateTag(Tag tag, ObjectId id) {
        try {
            Tag updateTag = ds.find(Tag.class)
                    .filter(Filters.eq("_id", id))
                    .modify(
                            UpdateOperators.set("name", tag.getName()),
                            UpdateOperators.set("icon", tag.getIcon()),
                            UpdateOperators.set("color", tag.getColor())
                    )
                    .execute(new ModifyOptions().returnDocument(ReturnDocument.AFTER));
            return updateTag;
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return null;
        }
    }

    public static Boolean deleteTag(ObjectId id) {
        try {
            ds.find(Tag.class).filter(Filters.eq("_id", id))
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
