/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gt.com.api.radiance.entities;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import java.util.Objects;
import org.bson.types.ObjectId;

/**
 *
 * @author malopez
 */
@Entity(value = "payment", useDiscriminator = false)
public class Payments {

    @Id
    private ObjectId id;
    private String date;
    private String amount;
    private String months;
    private ObjectId userId;

    public Payments() {
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getMonths() {
        return months;
    }

    public void setMonths(String months) {
        this.months = months;
    }

    public ObjectId getUserId() {
        return userId;
    }

    public void setUserId(ObjectId userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Payments{" + "id=" + id + ", date=" + date + ", amount=" + amount + ", months=" + months
                + ", userId=" + userId + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.id);
        hash = 89 * hash + Objects.hashCode(this.date);
        hash = 89 * hash + Objects.hashCode(this.amount);
        hash = 89 * hash + Objects.hashCode(this.months);
        hash = 89 * hash + Objects.hashCode(this.userId);
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
        final Payments other = (Payments) obj;
        if (!Objects.equals(this.date, other.date)) {
            return false;
        }
        if (!Objects.equals(this.amount, other.amount)) {
            return false;
        }
        if (!Objects.equals(this.months, other.months)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return Objects.equals(this.userId, other.userId);
    }

}
