/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gt.com.api.radiance.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import gt.com.api.radiance.entities.SuscriptionTypes;
import java.util.Objects;
import org.bson.types.ObjectId;

/**
 *
 * @author malopez
 */
public class Suscriptions {

    private String finalizationDate;
    private Boolean status;
    private ObjectId suscriptionTypeId;
    @JsonIgnore
    private SuscriptionTypes suscriptionsType;

    public Suscriptions() {
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

    public ObjectId getSuscriptionTypeId() {
        return suscriptionTypeId;
    }

    public void setSuscriptionTypeId(ObjectId suscriptionTypeId) {
        this.suscriptionTypeId = suscriptionTypeId;
    }

    public SuscriptionTypes getSuscriptionsType() {
        return suscriptionsType;
    }

    public void setSuscriptionsType(SuscriptionTypes suscriptionsType) {
        this.suscriptionsType = suscriptionsType;
    }

    @Override
    public String toString() {
        return "Suscriptions{" + "finalizationDate=" + finalizationDate + ", status=" + status
                + ", suscriptionTypeId=" + suscriptionTypeId + '}';
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + Objects.hashCode(this.finalizationDate);
        hash = 89 * hash + Objects.hashCode(this.status);
        hash = 89 * hash + Objects.hashCode(this.suscriptionTypeId);
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
        final Suscriptions other = (Suscriptions) obj;
        if (!Objects.equals(this.finalizationDate, other.finalizationDate)) {
            return false;
        }
        if (!Objects.equals(this.status, other.status)) {
            return false;
        }
        if (!Objects.equals(this.suscriptionTypeId, other.suscriptionTypeId)) {
            return false;
        }
        return true;
    }

}
