/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gt.com.api.radiance.queries;

import dev.morphia.Datastore;
import dev.morphia.query.FindOptions;
import dev.morphia.query.Query;
import dev.morphia.query.Sort;
import dev.morphia.query.experimental.filters.Filters;
import gt.com.api.radiance.entities.Payment;
import java.util.List;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author malopez
 */
public final class PaymentQuery {

    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentQuery.class);
    private static Datastore ds;

    private PaymentQuery() {
    }

    public static void setDataStore(Datastore datastore) {
        ds = datastore;
    }

    public static List<Payment> getPaymentList(ObjectId userId) {
        Query<Payment> getPayments = ds.find(Payment.class).filter(Filters.eq("userId", userId));
        try {
            return getPayments.iterator(new FindOptions().sort(Sort.descending("date"))).toList();
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return null;
        }
    }

//    public static Payment findPayment(ObjectId id) {
//        Query<Payment> getPayment = ds.find(Payment.class)
//                .filter(Filters.and(Filters.eq("_id", id), Filters.eq("isDelete", false)));
//        try {
//            return getPayment.first();
//        } catch (Exception e) {
//            return null;
//        }
//    }

    public static ObjectId savePayment(Payment payment) {
        try {
            return ds.save(payment).getId();
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return null;
        }
    }

}
