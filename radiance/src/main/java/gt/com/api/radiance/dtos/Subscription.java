/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gt.com.api.radiance.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import gt.com.api.radiance.entities.SubscriptionType;
import java.util.Objects;
import org.bson.types.ObjectId;

/**
 *
 * @author malopez
 */
public class Subscription {

    private String finalizationDate;
    private Boolean status;
    private ObjectId subscriptionTypeId;
    @JsonIgnore
    private SubscriptionType subscriptionType;

    public Subscription() {
    }

    public String getFinalizationDate() {
        return finalizationDate;
    }

    public void setFinalizationDate(String finalizationDate) {
        this.finalizationDate = finalizationDate;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public ObjectId getSubscriptionTypeId() {
        return subscriptionTypeId;
    }

    public void setSubscriptionTypeId(ObjectId subscriptionTypeId) {
        this.subscriptionTypeId = subscriptionTypeId;
    }

    public SubscriptionType getSubscriptionType() {
        return subscriptionType;
    }

    public void setSubscriptionType(SubscriptionType subscriptionType) {
        this.subscriptionType = subscriptionType;
    }

    @Override
    public String toString() {
        return "Subscription{" + "finalizationDate=" + finalizationDate + ", status=" + status
                + ", subscriptionTypeId=" + subscriptionTypeId + '}';
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + Objects.hashCode(this.finalizationDate);
        hash = 89 * hash + Objects.hashCode(this.status);
        hash = 89 * hash + Objects.hashCode(this.subscriptionTypeId);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Subscription other = (Subscription) obj;
        if (!Objects.equals(this.finalizationDate, other.finalizationDate)) {
            return false;
        }
        if (!Objects.equals(this.status, other.status)) {
            return false;
        }
        return Objects.equals(this.subscriptionTypeId, other.subscriptionTypeId);
    }

}
