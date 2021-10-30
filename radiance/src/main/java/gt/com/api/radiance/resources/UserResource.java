/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gt.com.api.radiance.resources;

import gt.com.api.radiance.controllers.UserController;
import gt.com.api.radiance.dtos.UserModel;
import gt.com.api.radiance.dtos.UserPage;
import gt.com.api.radiance.verify.ApiVersionValidator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
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
@Api("User")
@Path("/api/user")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserResource.class);
    private static final UserController USER_CONTROLLER = new UserController();

    @ApiOperation(value = "Get a user list", notes = "Get a list of users")
    @GET
    public UserPage getUsers(@QueryParam("page") @DefaultValue("1") Long page,
            @QueryParam("size") @DefaultValue("20") Long size, @QueryParam("filter") @DefaultValue("") String filter,
            @Context HttpServletRequest request) {
        long startTime = System.currentTimeMillis();
        ApiVersionValidator.validate(request);
//        UserLoad userLoad = Authenticator.tokenValidation(request);
        UserPage users = USER_CONTROLLER.getUserPage(size, page, filter);
        if (users == null) {
            LOGGER.error("Time of not GET user list: " + (System.currentTimeMillis() - startTime)
                    + " milliseconds, statusCode:" + Response.Status.BAD_REQUEST);
            throw new WebApplicationException("Cannot get user list ", Response.Status.BAD_REQUEST);
        }
        LOGGER.info("Time to GET user list: " + (System.currentTimeMillis() - startTime)
                + " milliseconds, statusCode:" + Response.Status.OK);
        return users;
    }

    @ApiOperation(value = "Get specific user", notes = "Get specific user by id")
    @GET
    @Path("/{id}")
    public UserModel getUser(@PathParam("id") String id, @Context HttpServletRequest request) {
        long startTime = System.currentTimeMillis();
        ApiVersionValidator.validate(request);
//        UserLoad userLoad = Authenticator.tokenValidation(request);
        UserModel userModel = USER_CONTROLLER.getUser(id);
        if (userModel == null) {
            LOGGER.error("Time of not GET user: " + (System.currentTimeMillis() - startTime)
                    + " milliseconds, statusCode:" + Response.Status.BAD_REQUEST);
            throw new WebApplicationException("Cannot get user ", Response.Status.BAD_REQUEST);
        }
        LOGGER.info("Time to GET user: " + (System.currentTimeMillis() - startTime)
                + " milliseconds, statusCode:" + Response.Status.OK);
        return userModel;
    }

    @ApiOperation(value = "Create user", notes = "Insert new user")
    @POST
    public UserModel postUser(UserModel userModel, @Context HttpServletRequest request) {
        long startTime = System.currentTimeMillis();
        ApiVersionValidator.validate(request);
//        UserLoad userLoad = Authenticator.tokenValidation(request);
        //verification of required fields
        if (userModel.getName().equals("") || userModel.getUser().equals("") || userModel.getPassword().equals("")
                || userModel.getRole().equals("")) {
            LOGGER.error("Time of not save user: " + (System.currentTimeMillis() - startTime)
                    + " milliseconds, statusCode:" + Response.Status.NOT_ACCEPTABLE.getStatusCode());
            throw new WebApplicationException("Fields are missing ", Response.Status.NOT_ACCEPTABLE);
        }
        UserModel newUser = USER_CONTROLLER.saveUser(userModel);
        if (newUser == null) {
            LOGGER.error("Time of not save new user: " + (System.currentTimeMillis() - startTime)
                    + " milliseconds, statusCode:" + Response.Status.BAD_REQUEST);
            throw new WebApplicationException("Cannot post user ", Response.Status.BAD_REQUEST);
        }
        LOGGER.info("Time to POST user: " + (System.currentTimeMillis() - startTime)
                + " milliseconds, statusCode:" + Response.Status.OK);
        return newUser;
    }

    @ApiOperation(value = "Update specific user", notes = "Modify specific user")
    @PUT
    @Path("/{id}")
    public UserModel putUser(UserModel userModel, @PathParam("id") String id,
            @Context HttpServletRequest request) {
        long startTime = System.currentTimeMillis();
        ApiVersionValidator.validate(request);
//        UserLoad userLoad = Authenticator.tokenValidation(request);
        //verification of required fields
        if (userModel.getName().equals("") || userModel.getUser().equals("") || userModel.getRole().equals("")) {
            LOGGER.error("Time of not update user: " + (System.currentTimeMillis() - startTime)
                    + " milliseconds, statusCode:" + Response.Status.NOT_ACCEPTABLE.getStatusCode());
            throw new WebApplicationException("Fields are missing ", Response.Status.NOT_ACCEPTABLE);
        }
        //verificate tag exists
        if (USER_CONTROLLER.verifyUserExists(id)) {
            LOGGER.error("Time of not update user: " + (System.currentTimeMillis() - startTime)
                    + " milliseconds, statusCode:" + Response.Status.NOT_FOUND.getStatusCode());
            throw new WebApplicationException("User not found, userID: " + id,
                    Response.Status.NOT_FOUND);
        }
        UserModel updateUser = USER_CONTROLLER.updateUser(id, userModel);
        if (updateUser == null) {
            LOGGER.error("Time of not update user: " + (System.currentTimeMillis() - startTime)
                    + " milliseconds, statusCode:" + Response.Status.BAD_REQUEST);
            throw new WebApplicationException("Cannot put user ", Response.Status.BAD_REQUEST);
        }
        LOGGER.info("Time to PUT user: " + (System.currentTimeMillis() - startTime)
                + " milliseconds, statusCode:" + Response.Status.OK);
        return updateUser;
    }

    @ApiOperation(value = "Delete user", notes = "Soft delete specific user")
    @DELETE
    @Path("/{id}")
    public Response deleteUser(@PathParam("id") String id, @Context HttpServletRequest request) {
        long startTime = System.currentTimeMillis();
        ApiVersionValidator.validate(request);
//        UserLoad userLoad = Authenticator.tokenValidation(request);
        //verificate tag exists
        if (USER_CONTROLLER.verifyUserExists(id)) {
            LOGGER.error("Time of not delete user: " + (System.currentTimeMillis() - startTime)
                    + " milliseconds, statusCode:" + Response.Status.NOT_FOUND.getStatusCode());
            throw new WebApplicationException("User not found, userID: " + id,
                    Response.Status.NOT_FOUND);
        }
        if (!USER_CONTROLLER.deleteUser(id)) {
            LOGGER.error("Time of not DELETE user: " + (System.currentTimeMillis() - startTime)
                    + " milliseconds, statusCode:" + Response.Status.BAD_REQUEST);
            throw new WebApplicationException("Cannot delete user ", Response.Status.BAD_REQUEST);
        }
        LOGGER.info("Time to DELETE user: " + (System.currentTimeMillis() - startTime)
                + " milliseconds, statusCode:" + Response.Status.OK);
        return Response.status(Response.Status.OK).entity("OK").build();
    }

}
