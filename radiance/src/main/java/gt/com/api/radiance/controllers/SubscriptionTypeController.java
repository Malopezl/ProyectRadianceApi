/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gt.com.api.radiance.controllers;

import gt.com.api.radiance.entities.SubscriptionType;
import gt.com.api.radiance.queries.SubscriptionTypeQuery;
import java.util.List;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author malopez
 */
public class SubscriptionTypeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SubscriptionTypeController.class);

    public SubscriptionTypeController() {
    }

    public List<SubscriptionType> getSubscriptionType() {
        return SubscriptionTypeQuery.getSubscriptionType();
    }

    public static SubscriptionType getSubscriptionType(String id) {
        ObjectId subscriptionTypeId = new ObjectId(id);
        return SubscriptionTypeQuery.getSubscriptionType(subscriptionTypeId);
    }

    public Boolean verifySubscriptionTypeExists(String id) {
        ObjectId subscriptionTypeId = new ObjectId(id);
        return SubscriptionTypeQuery.verifySubscriptionTypeExists(subscriptionTypeId);
    }

    public SubscriptionType saveSubscriptionType(SubscriptionType subscriptionType) {
        subscriptionType.setIsDelete(Boolean.FALSE);
        ObjectId subscriptionTypeId = SubscriptionTypeQuery.saveSubscriptionType(subscriptionType);
        if (subscriptionTypeId != null) {
            subscriptionType.setId(subscriptionTypeId);
            return subscriptionType;
        } else {
            LOGGER.error("Failed to save subscription type");
            return null;
        }
    }

    public SubscriptionType updateSubscriptionType(String id, SubscriptionType subscriptionType) {
        ObjectId subscriptionTypeId = new ObjectId(id);
        SubscriptionType oldSubscriptionType = SubscriptionTypeQuery.getSubscriptionType(subscriptionTypeId);
        oldSubscriptionType.setName(subscriptionType.getName());
        oldSubscriptionType.setPrice(subscriptionType.getPrice());
        oldSubscriptionType.setDescription(subscriptionType.getDescription());
        subscriptionType = SubscriptionTypeQuery.updateSubscriptionType(subscriptionType, subscriptionTypeId);

        if (subscriptionType == null) {
            LOGGER.error("Unable to update the subscription type");
            return null;
        }
        return oldSubscriptionType;
    }

    public Boolean deleteSubscriptionType(String id) {
        ObjectId subscriptionTypeId = new ObjectId(id);
        return SubscriptionTypeQuery.deleteSubscriptionType(subscriptionTypeId);
    }
}
