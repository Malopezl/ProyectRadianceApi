/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gt.com.api.radiance.controllers;

import gt.com.api.radiance.dtos.SubscriptionTypeModel;
import gt.com.api.radiance.entities.SubscriptionType;
import gt.com.api.radiance.queries.SubscriptionTypeQuery;
import java.util.ArrayList;
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

    public List<SubscriptionTypeModel> getSubscriptionType() {
        List<SubscriptionTypeModel> types = new ArrayList();
        List<SubscriptionType> subscriptionTypes = SubscriptionTypeQuery.getSubscriptionType();
        if (subscriptionTypes == null) {
            return null;
        }
        subscriptionTypes.stream().map(subscriptionType -> {
            SubscriptionTypeModel type = new SubscriptionTypeModel();
            type.setSubscriptionTypeId(subscriptionType.getId().toString());
            type.setName(subscriptionType.getName());
            type.setDescription(subscriptionType.getDescription());
            type.setPrice(subscriptionType.getPrice());
            return type;
        }).forEachOrdered(type -> {
            types.add(type);
        });
        return types;
    }

    public SubscriptionTypeModel getSubscriptionType(String id) {
        ObjectId subscriptionTypeId = new ObjectId(id);
        SubscriptionType subscriptionType = SubscriptionTypeQuery.getSubscriptionType(subscriptionTypeId);
        if (subscriptionType == null) {
            return null;
        }
        SubscriptionTypeModel type = new SubscriptionTypeModel();
        type.setSubscriptionTypeId(subscriptionType.getId().toString());
        type.setName(subscriptionType.getName());
        type.setDescription(subscriptionType.getDescription());
        type.setPrice(subscriptionType.getPrice());
        return type;
    }

    public Boolean verifySubscriptionTypeExists(String id) {
        ObjectId subscriptionTypeId = new ObjectId(id);
        return SubscriptionTypeQuery.verifySubscriptionTypeExists(subscriptionTypeId);
    }

    public SubscriptionTypeModel saveSubscriptionType(SubscriptionTypeModel subscriptionType) {
        SubscriptionType type = new SubscriptionType();
        type.setName(subscriptionType.getName());
        type.setDescription(subscriptionType.getDescription());
        type.setPrice(subscriptionType.getPrice());
        type.setIsDelete(Boolean.FALSE);
        ObjectId subscriptionTypeId = SubscriptionTypeQuery.saveSubscriptionType(type);
        if (subscriptionTypeId != null) {
            subscriptionType.setSubscriptionTypeId(subscriptionTypeId.toString());
            return subscriptionType;
        } else {
            LOGGER.error("Failed to save subscription type");
            return null;
        }
    }

    public SubscriptionTypeModel updateSubscriptionType(String id, SubscriptionTypeModel subscriptionType) {
        ObjectId subscriptionTypeId = new ObjectId(id);
        SubscriptionType oldSubscriptionType = new SubscriptionType();
        oldSubscriptionType.setName(subscriptionType.getName());
        oldSubscriptionType.setDescription(subscriptionType.getDescription());
        oldSubscriptionType.setPrice(subscriptionType.getPrice());
        oldSubscriptionType = SubscriptionTypeQuery.updateSubscriptionType(oldSubscriptionType, subscriptionTypeId);
        if (oldSubscriptionType == null) {
            LOGGER.error("Unable to update the subscription type");
            return null;
        }
        subscriptionType.setSubscriptionTypeId(id);
        return subscriptionType;
    }

    public Boolean deleteSubscriptionType(String id) {
        ObjectId subscriptionTypeId = new ObjectId(id);
        return SubscriptionTypeQuery.deleteSubscriptionType(subscriptionTypeId);
    }
}
