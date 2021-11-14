/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gt.com.api.radiance.controllers;

import gt.com.api.radiance.dtos.PaymentModel;
import gt.com.api.radiance.entities.Payment;
import gt.com.api.radiance.helper.FormatDate;
import gt.com.api.radiance.queries.PaymentQuery;
import java.util.ArrayList;
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

    public List<PaymentModel> getPaymentList(String userId) {
        List<Payment> paymentList = PaymentQuery.getPaymentList(new ObjectId(userId));
        if (paymentList == null) {
            return null;
        }
        List<PaymentModel> payments = new ArrayList();
        paymentList.stream().map(payment -> {
            PaymentModel paymentModel = new PaymentModel();
            paymentModel.setPaymentId(payment.getId().toString());
            paymentModel.setAmount(payment.getAmount());
            paymentModel.setDate(FormatDate.convertTime(payment.getDate()));
            return paymentModel;
        }).forEachOrdered(paymentModel -> {
            paymentModel.setUserId(userId);
            payments.add(paymentModel);
        });
        return payments;
    }

    public static Boolean savePayment(Payment payment) {
        ObjectId paymentId = PaymentQuery.savePayment(payment);
        if (paymentId == null) {
            LOGGER.error("Failed to save payment");
            return false;
        }
        return true;
    }

}
