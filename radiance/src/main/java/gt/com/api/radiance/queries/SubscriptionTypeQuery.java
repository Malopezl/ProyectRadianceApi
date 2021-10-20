/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gt.com.api.radiance.queries;

import com.mongodb.client.model.ReturnDocument;
import dev.morphia.Datastore;
import dev.morphia.ModifyOptions;
import dev.morphia.query.MorphiaCursor;
import dev.morphia.query.Query;
import dev.morphia.query.experimental.filters.Filters;
import dev.morphia.query.experimental.updates.UpdateOperators;
import gt.com.api.radiance.entities.SubscriptionTypes;
import java.util.ArrayList;
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

    public static List<SubscriptionTypes> getSubscriptionTypes() {
        Query<SubscriptionTypes> getTypes = ds.find(SubscriptionTypes.class)
                .filter(Filters.and(Filters.eq("isDelete", false)));
        List<SubscriptionTypes> list = new ArrayList<>();
        MorphiaCursor<SubscriptionTypes> cursor = getTypes.iterator();
        try {
            while (cursor.hasNext()) {
                SubscriptionTypes next = cursor.next();
                list.add(next);
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return null;
        }
        return list;
    }

    public static SubscriptionTypes getSubscriptionType(ObjectId id) {
        Query<SubscriptionTypes> getType = ds.find(SubscriptionTypes.class)
                .filter(Filters.and(Filters.eq("_id", id), Filters.eq("isDelete", false)));
        try {
            return getType.first();
        } catch (Exception e) {
            return null;
        }
    }

    public static Boolean verifySubscriptionTypeExists(ObjectId id) {
        Query<SubscriptionTypes> verifyType = ds.find(SubscriptionTypes.class)
                .filter(Filters.and(Filters.eq("_id", id), Filters.eq("isDelete", false)));
        return verifyType.first() != null;
    }

    public static ObjectId saveSubscriptionType(SubscriptionTypes subscriptionType) {
        try {
            ObjectId id = ds.save(subscriptionType).getId();
            return id;
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return null;
        }
    }

    public static SubscriptionTypes updateSubscriptionType(SubscriptionTypes subscriptionType, ObjectId id) {
        try {
            SubscriptionTypes updateSubscriptionType = ds.find(SubscriptionTypes.class)
                    .filter(Filters.and(Filters.eq("_id", id))
                            .modify(
                                    UpdateOperators.set("name", subscriptionType.getName()),
                                    UpdateOperators.set("price", subscriptionType.getPrice()),
                                    UpdateOperators.set("description", subscriptionType.getDescription())
                            )
                            .execute(new ModifyOptions().returnDocument(ReturnDocument.AFTER)));
            return updateSubscriptionType;
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return null;
        }
    }


}
