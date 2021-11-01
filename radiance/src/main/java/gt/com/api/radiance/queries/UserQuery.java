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
import dev.morphia.aggregation.experimental.stages.Unwind;
import dev.morphia.query.MorphiaCursor;
import dev.morphia.query.Query;
import dev.morphia.query.experimental.filters.Filters;
import dev.morphia.query.experimental.updates.UpdateOperators;
import gt.com.api.radiance.entities.User;
import java.util.List;
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

    public static List<User> getUserList() {
        try {
            MorphiaCursor<User> pipeline = ds.aggregate(User.class)
                    .match(Filters.eq("isDelete", Boolean.FALSE), Filters.ne("subscription", null))
                    .sort(dev.morphia.aggregation.experimental.stages.Sort.sort().descending("name"))
                    .lookup(Lookup.lookup("SubscriptionType").localField("subscription.subscriptionTypeId")
                            .foreignField("_id").as("subscriptionType"))
                    .unwind(Unwind.unwind("subscriptionType"))
                    .execute(User.class);
            return pipeline.toList();
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return null;
        }
    }

    public static User findUser(ObjectId id) {
        Query<User> getUser = ds.find(User.class)
                .filter(Filters.eq("_id", id), Filters.eq("isDelete", false));
        try {
            return getUser.first();
        } catch (Exception e) {
            LOGGER.error("userID: " + id.toString() + " " + e.getMessage());
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
