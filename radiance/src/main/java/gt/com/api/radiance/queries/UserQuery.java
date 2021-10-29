/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gt.com.api.radiance.queries;

import com.mongodb.client.model.ReturnDocument;
import dev.morphia.Datastore;
import dev.morphia.ModifyOptions;
import dev.morphia.aggregation.AggregationPipeline;
import dev.morphia.query.Query;
import dev.morphia.query.Sort;
import dev.morphia.query.experimental.filters.Filters;
import dev.morphia.query.experimental.updates.UpdateOperators;
import gt.com.api.radiance.entities.User;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author malopez
 */
public final class UserQuery {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserQuery.class);
    private static Datastore ds;

    private UserQuery() {
    }

    public static void setDataStore(Datastore datastore) {
        ds = datastore;
    }

    public static User findUser(String username) {
        Query<User> getUser = ds.find(User.class).filter(Filters.eq("user", username),
                Filters.eq("isDelete", Boolean.FALSE));
        try {
            return getUser.first();
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return null;
        }
    }

    public static List<User> getUserList(int size, int page, Pattern regexp) {
        List<User> users = new ArrayList();
//        int p = size * (page - 1);
//        FindOptions findOptions = new FindOptions().skip(p).limit(size);
        Query<User> getUsers = ds.find(User.class).filter(Filters.eq("isDelete", Boolean.FALSE))
                .filter(Filters.or(Filters.eq("name", regexp)));
        AggregationPipeline pipeline = ds.createAggregation(User.class)
                .match(getUsers)
                .sort(Sort.ascending("name"))
                .lookup("SubscriptionType", "subscription.subscriptionTypeId", "_id", "subscriptionType")
                .unwind("subscriptionType");
        Iterator<User> iterator = pipeline.aggregate(User.class);
        while (iterator.hasNext()) {
            User next = iterator.next();
            users.add(next);
        }
        try {
            return users;
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return null;
        }
    }

//    public static Long totalPages(Pattern regexp) {
//        Long pages = 0L;
//        Query<User> getTagsCount = ds.find(User.class).filter(Filters.eq("isDelete", Boolean.FALSE))
//                .filter(Filters.or(Filters.eq("name", regexp)));
//        try {
//            pages = Long.valueOf(getTagsCount.iterator().toList().size());
//        } catch (Exception e) {
//            LOGGER.error(e.getMessage());
//        }
//        return pages;
//    }

    public static User findUser(ObjectId id) {
        Query<User> getUser = ds.find(User.class)
                .filter(Filters.and(Filters.eq("_id", id), Filters.eq("isDelete", false)));
        try {
            return getUser.first();
        } catch (Exception e) {
            return null;
        }
    }

    public static ObjectId saveUser(User user) {
        try {
            return ds.save(user).getId();
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return null;
        }
    }

    public static Boolean verifyUserExists(ObjectId id) {
        Query<User> verifyUser = ds.find(User.class)
                .filter(Filters.and(Filters.eq("_id", id), Filters.eq("isDelete", false)));
        return verifyUser.first() != null;
    }

    public static User updateUser(User user, ObjectId id) {
        try {
            User updateUser = ds.find(User.class)
                    .filter(Filters.eq("_id", id))
                    .modify(
                            UpdateOperators.set("name", user.getName()),
                            UpdateOperators.set("phoneNumber", user.getPhoneNumber()),
                            UpdateOperators.set("mail", user.getMail()),
                            UpdateOperators.set("image", user.getImage()),
                            UpdateOperators.set("user", user.getUser())
                    )
                    .execute(new ModifyOptions().returnDocument(ReturnDocument.AFTER));
            return updateUser;
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return null;
        }
    }

    public static Boolean deleteUser(ObjectId id) {
        try {
            ds.find(User.class).filter(Filters.eq("_id", id))
                    .modify(
                            UpdateOperators.set("subscription.status", Boolean.FALSE),
                            UpdateOperators.set("isDelete", Boolean.FALSE)
                    )
                    .execute(new ModifyOptions().returnDocument(ReturnDocument.AFTER));
            return true;
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return false;
        }
    }

}
