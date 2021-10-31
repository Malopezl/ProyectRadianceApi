/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gt.com.api.radiance.controllers;

import gt.com.api.radiance.entities.Payment;
import gt.com.api.radiance.queries.PaymentQuery;
import java.util.List;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author malopez
 */
public class PaymentController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentController.class);

    public PaymentController() {
    }

    public List<Payment> getPaymentList(String userId) {
        return PaymentQuery.getPaymentList(new ObjectId(userId));
    }

    public Payment savePayment(Payment payment) {
        ObjectId paymentId = PaymentQuery.savePayment(payment);
        if (paymentId == null) {
            LOGGER.error("Failed to save payment");
            return null;
        }
        payment.setId(paymentId);
        return payment;
    }

}
