/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gt.com.api.radiance.resources;

import gt.com.api.radiance.controllers.SubscriptionTypeController;
import gt.com.api.radiance.entities.SubscriptionTypes;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
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
@Api("SubscriptionType")
@Path("/subscriptiontype")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SubscriptionTypeResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(SubscriptionTypeResource.class);
    private static final SubscriptionTypeController SUSCRIPTION_TYPE_CONTROLLER = new SubscriptionTypeController();

    @ApiOperation(value = "Get a subscription type list", notes = "Get a list of subscription types")
    @GET
    public List<SubscriptionTypes> getSubscriptionType(@Context HttpServletRequest request) {
        long startTime = System.currentTimeMillis();
        List<SubscriptionTypes> subscriptionTypes = SUSCRIPTION_TYPE_CONTROLLER.getSubscriptionTypes();
        if (subscriptionTypes == null) {
            LOGGER.error("Time of not GET subscription type list: " + (System.currentTimeMillis() - startTime)
                    + " milliseconds, statusCode:" + Response.Status.BAD_REQUEST);
            throw new WebApplicationException("Cannot get subscription type list ", Response.Status.BAD_REQUEST);
        }
        LOGGER.info("Time to GET subscription type list: " + (System.currentTimeMillis() - startTime)
                + " milliseconds, statusCode:" + Response.Status.OK);
        return subscriptionTypes;
    }

    @ApiOperation(value = "Get specific subscription type", notes = "Get specific subscription type by id")
    @GET
    @Path("/{id}")
    public SubscriptionTypes getSubscriptionType(@PathParam("id") String id, @Context HttpServletRequest request) {
        long startTime = System.currentTimeMillis();
        SubscriptionTypes subscriptionType = SUSCRIPTION_TYPE_CONTROLLER.getSubscriptionType(id);
        if (subscriptionType == null) {
            LOGGER.error("Time of not GET subscription type: " + (System.currentTimeMillis() - startTime)
                    + " milliseconds, statusCode:" + Response.Status.BAD_REQUEST);
            throw new WebApplicationException("Cannot get subscription type ", Response.Status.BAD_REQUEST);
        }
        LOGGER.info("Time to GET subscription type: " + (System.currentTimeMillis() - startTime)
                + " milliseconds, statusCode:" + Response.Status.OK);
        return subscriptionType;
    }

    @ApiOperation(value = "Create subscription type", notes = "Insert new subscription type")
    @POST
    public SubscriptionTypes postSubscriptionType(SubscriptionTypes subscriptionType,
            @Context HttpServletRequest request) {
        long startTime = System.currentTimeMillis();
        //verification of required fields
        if (subscriptionType.getName().equals("") || subscriptionType.getPrice().equals("")
                || subscriptionType.getDescription().equals("")) {
            LOGGER.error("Time of not save subscription type: " + (System.currentTimeMillis() - startTime)
                    + " milliseconds, statusCode:" + Response.Status.NOT_ACCEPTABLE.getStatusCode());
            throw new WebApplicationException("Fields are missing ", Response.Status.NOT_ACCEPTABLE);
        }
        SubscriptionTypes newSubscriptionType = SUSCRIPTION_TYPE_CONTROLLER.saveSubscriptionType(subscriptionType);
        if (newSubscriptionType == null) {
            LOGGER.error("Time of not save new subscription type: " + (System.currentTimeMillis() - startTime)
                    + " milliseconds, statusCode:" + Response.Status.BAD_REQUEST);
            throw new WebApplicationException("Cannot post subscription type ", Response.Status.BAD_REQUEST);
        }
        LOGGER.info("Time to POST subscription type: " + (System.currentTimeMillis() - startTime)
                + " milliseconds, statusCode:" + Response.Status.OK);
        return newSubscriptionType;
    }

    @ApiOperation(value = "Update specific subscription type", notes = "Modify specific subscription type")
    @PUT
    @Path("/{id}")
    public SubscriptionTypes putSubscriptionType(SubscriptionTypes subscriptionType, @PathParam("id") String id,
            @Context HttpServletRequest request) {
        long startTime = System.currentTimeMillis();
        //verification of required fields
        if (subscriptionType.getName().equals("") || subscriptionType.getPrice().equals("")
                || subscriptionType.getDescription().equals("")) {
            LOGGER.error("Time of not update subscription type: " + (System.currentTimeMillis() - startTime)
                    + " milliseconds, statusCode:" + Response.Status.NOT_ACCEPTABLE.getStatusCode());
            throw new WebApplicationException("Fields are missing ", Response.Status.NOT_ACCEPTABLE);
        }
        //verificate subscription type exists

        SubscriptionTypes updateSubscriptionType = SUSCRIPTION_TYPE_CONTROLLER.updateSubscriptionType(id, subscriptionType);
        if (updateSubscriptionType == null) {
            LOGGER.error("Time of not update subscription type: " + (System.currentTimeMillis() - startTime)
                    + " milliseconds, statusCode:" + Response.Status.BAD_REQUEST);
            throw new WebApplicationException("Cannot put subscription type ", Response.Status.BAD_REQUEST);
        }
        LOGGER.info("Time to PUT subscription type: " + (System.currentTimeMillis() - startTime)
                + " milliseconds, statusCode:" + Response.Status.OK);
        return updateSubscriptionType;
    }

    @ApiOperation(value = "Delete subscription type", notes = "Soft delete specific subscription type")
    @PUT
    @Path("/{id}")
    public Response putSubscriptionType(@PathParam("id") String id, @Context HttpServletRequest request) {
        long startTime = System.currentTimeMillis();
        SubscriptionTypes updateSubscriptionType = SUSCRIPTION_TYPE_CONTROLLER.updateSubscriptionType(id, subscriptionType);
        if (updateSubscriptionType == null) {
            LOGGER.error("Time of not update subscription type: " + (System.currentTimeMillis() - startTime)
                    + " milliseconds, statusCode:" + Response.Status.BAD_REQUEST);
            throw new WebApplicationException("Cannot put subscription type ", Response.Status.BAD_REQUEST);
        }
        LOGGER.info("Time to PUT subscription type: " + (System.currentTimeMillis() - startTime)
                + " milliseconds, statusCode:" + Response.Status.OK);
        return updateSubscriptionType;
    }
}
