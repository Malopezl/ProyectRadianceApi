/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gt.com.api.radiance.dtos;

import gt.com.api.radiance.entities.SubscriptionType;

/**
 *
 * @author malopez
 */
public class SubscriptionModel {

    private String finalizationDate;
    private Boolean status;
    private SubscriptionType subscriptionType;

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

    public SubscriptionType getSubscriptionType() {
        return subscriptionType;
    }

    public void setSubscriptionType(SubscriptionType subscriptionType) {
        this.subscriptionType = subscriptionType;
    }

    @Override
    public String toString() {
        return "SubscriptionModel{" + "finalizationDate=" + finalizationDate + ", status=" + status
                + ", subscriptionType=" + subscriptionType + '}';
    }

}
