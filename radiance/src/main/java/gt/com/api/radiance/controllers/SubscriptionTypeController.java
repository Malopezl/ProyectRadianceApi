/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gt.com.api.radiance.controllers;

import gt.com.api.radiance.entities.SubscriptionTypes;
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

    public List<SubscriptionTypes> getSubscriptionTypes() {
        return SubscriptionTypeQuery.getSubscriptionTypes();
    }

    public SubscriptionTypes getSubscriptionType(String id) {
        ObjectId subscriptionTypeId = new ObjectId(id);
        return SubscriptionTypeQuery.getSubscriptionType(subscriptionTypeId);
    }

    public SubscriptionType verifySubscriptionTypeExists(String id) {
        ObjectId subscriptionTypeId = new ObjectId(id);

    }

    public SubscriptionTypes saveSubscriptionType(SubscriptionTypes subscriptionType) {
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

    public SubscriptionTypes updateSubscriptionType(String id, SubscriptionTypes subscriptionTypes) {
        ObjectId subscriptionTypeId = new ObjectId(id);
        SubscriptionTypes oldSubscriptionType = SubscriptionTypeQuery.getSubscriptionType(subscriptionTypeId);
        oldSubscriptionType.setName(subscriptionTypes.getName());
        oldSubscriptionType.setPrice(subscriptionTypes.getPrice());
        oldSubscriptionType.setDescription(subscriptionTypes.getDescription());
        subscriptionTypes = SubscriptionTypeQuery.updateSubscriptionType(subscriptionTypes, subscriptionTypeId);

        if (subscriptionTypes == null) {
            LOGGER.error("Unable to update the subscription type");
            return null;
        }
        return oldSubscriptionType;
    }
}
