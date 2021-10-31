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
import gt.com.api.radiance.entities.SubscriptionType;
import java.util.List;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author malopez
 */
public final class SubscriptionTypeQuery {

    private static final Logger LOGGER = LoggerFactory.getLogger(SubscriptionTypeQuery.class);
    private static Datastore ds;

    private SubscriptionTypeQuery() {
    }

    public static void setDataStore(Datastore datastore) {
        ds = datastore;
    }

    public static List<SubscriptionType> getSubscriptionType() {
        try {
            Query<SubscriptionType> getTypes = ds.find(SubscriptionType.class)
                    .filter(Filters.and(Filters.eq("isDelete", false)));
            return getTypes.iterator().toList();
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return null;
        }
    }

    public static SubscriptionType getSubscriptionType(ObjectId id) {
        Query<SubscriptionType> getType = ds.find(SubscriptionType.class)
                .filter(Filters.and(Filters.eq("_id", id), Filters.eq("isDelete", false)));
        try {
            return getType.first();
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return null;
        }
    }

    public static Boolean verifySubscriptionTypeExists(ObjectId id) {
        Query<SubscriptionType> verifyType = ds.find(SubscriptionType.class)
                .filter(Filters.eq("_id", id), Filters.eq("isDelete", false));
        return verifyType.first() != null;
    }

    public static ObjectId saveSubscriptionType(SubscriptionType subscriptionType) {
        try {
            ObjectId id = ds.save(subscriptionType).getId();
            return id;
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return null;
        }
    }

    public static SubscriptionType updateSubscriptionType(SubscriptionType subscriptionType, ObjectId id) {
        try {
            SubscriptionType updateSubscriptionType = ds.find(SubscriptionType.class)
                    .filter(Filters.eq("_id", id))
                    .modify(
                            UpdateOperators.set("name", subscriptionType.getName()),
                            UpdateOperators.set("price", subscriptionType.getPrice()),
                            UpdateOperators.set("description", subscriptionType.getDescription())
                    )
                    .execute(new ModifyOptions().returnDocument(ReturnDocument.AFTER));
            return updateSubscriptionType;
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return null;
        }
    }

    public static Boolean deleteSubscriptionType(ObjectId id) {
        try {
            ds.find(SubscriptionType.class).filter(Filters.eq("_id", id))
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
