/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gt.com.api.radiance.resources;

import gt.com.api.radiance.controllers.SuscriptionTypeController;
import gt.com.api.radiance.entities.SuscriptionTypes;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
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
@Api("SuscriptionType")
@Path("/suscriptiontype")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SuscriptionTypeResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(SuscriptionTypeResource.class);
    private static final SuscriptionTypeController SUSCRIPTION_TYPE_CONTROLLER = new SuscriptionTypeController();

    @ApiOperation(value = "Get a suscription type list", notes = "Get a list of suscription types")
    @GET
    public List<SuscriptionTypes> getSuscriptionType(@Context HttpServletRequest request) {
        long startTime = System.currentTimeMillis();
        List<SuscriptionTypes> suscriptionTypes = SUSCRIPTION_TYPE_CONTROLLER.getSuscriptionTypes();
        if (suscriptionTypes == null) {
            LOGGER.error("Cannot get suscription types list. " + (System.currentTimeMillis() - startTime)
                    + " milliseconds, statusCode:" + Response.Status.BAD_REQUEST);
            throw new WebApplicationException("Cannot get notification's page.", Response.Status.BAD_REQUEST);
        }
        LOGGER.info("Time to Get suscription type list " + (System.currentTimeMillis() - startTime)
                + " milliseconds, statusCode:" + Response.Status.OK);
        return suscriptionTypes;
    }
}
