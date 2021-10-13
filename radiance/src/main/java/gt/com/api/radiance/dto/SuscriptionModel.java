/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gt.com.api.radiance.dto;

import gt.com.api.radiance.entities.SuscriptionTypes;

/**
 *
 * @author malopez
 */
public class SuscriptionModel {

    private String finalizationDate;
    private Boolean status;
    private SuscriptionTypes suscriptionsType;

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

    public SuscriptionTypes getSuscriptionsType() {
        return suscriptionsType;
    }

    public void setSuscriptionsType(SuscriptionTypes suscriptionsType) {
        this.suscriptionsType = suscriptionsType;
    }

    @Override
    public String toString() {
        return "SuscriptionModel{" + "finalizationDate=" + finalizationDate + ", status=" + status
                + ", suscriptionsType=" + suscriptionsType + '}';
    }

}
