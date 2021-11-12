/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gt.com.api.radiance.resources;

import gt.com.api.radiance.controllers.PaymentController;
import gt.com.api.radiance.entities.Payment;
import gt.com.api.radiance.verify.ApiVersionValidator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author malopez
 */
@Api("Payment")
@Path("/api/payment")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PaymentResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentResource.class);
    private static final PaymentController PAYMENT_CONTROLLER = new PaymentController();

    @ApiOperation(value = "Get a payment list", notes = "Get a list of payments by user")
    @GET
    @Path("/{userId}")
    public List<Payment> getPayments(@PathParam("userId") String user,
            @Context HttpServletRequest request) {
        long startTime = System.currentTimeMillis();
        ApiVersionValidator.validate(request);
//        UserLoad userLoad = Authenticator.tokenValidation(request);
        List<Payment> paymentList = PAYMENT_CONTROLLER.getPaymentList(user);
        if (paymentList == null) {
            LOGGER.error("Time of not GET payment list: " + (System.currentTimeMillis() - startTime)
                    + " milliseconds, statusCode:" + Response.Status.BAD_REQUEST);
            throw new WebApplicationException("Cannot get payment list ", Response.Status.BAD_REQUEST);
        }
        LOGGER.info("Time to GET payment list: " + (System.currentTimeMillis() - startTime)
                + " milliseconds, statusCode:" + Response.Status.OK);
        return paymentList;
    }

//    @ApiOperation(value = "Create payment", notes = "Insert new payment")
//    @POST
//    public Payment postPayment(Payment payment, @Context HttpServletRequest request) {
//        long startTime = System.currentTimeMillis();
//        ApiVersionValidator.validate(request);
////        UserLoad userLoad = Authenticator.tokenValidation(request);
//        //verification of required fields
//        if (payment.getDate().equals("") || payment.getAmount().equals("") || payment.getId() != null) {
//            LOGGER.error("Time of not save payment: " + (System.currentTimeMillis() - startTime)
//                    + " milliseconds, statusCode:" + Response.Status.NOT_ACCEPTABLE.getStatusCode());
//            throw new WebApplicationException("Fields are missing ", Response.Status.NOT_ACCEPTABLE);
//        }
//        Payment newPayment = PAYMENT_CONTROLLER.savePayment(payment);
//        if (newPayment == null) {
//            LOGGER.error("Time of not save new payment: " + (System.currentTimeMillis() - startTime)
//                    + " milliseconds, statusCode:" + Response.Status.BAD_REQUEST);
//            throw new WebApplicationException("Cannot post payment ", Response.Status.BAD_REQUEST);
//        }
//        LOGGER.info("Time to POST payment: " + (System.currentTimeMillis() - startTime)
//                + " milliseconds, statusCode:" + Response.Status.OK);
//        return newPayment;
//    }

}
