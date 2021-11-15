/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gt.com.api.radiance.resources;

import gt.com.api.radiance.controllers.LoginController;
import gt.com.api.radiance.dtos.ChangePassword;
import gt.com.api.radiance.dtos.LoginModel;
import gt.com.api.radiance.dtos.UserForLogin;
import gt.com.api.radiance.dtos.UserLoad;
import gt.com.api.radiance.entities.User;
import gt.com.api.radiance.helper.EmailSender;
import gt.com.api.radiance.verify.ApiVersionValidator;
import gt.com.api.radiance.verify.Authenticator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.io.UnsupportedEncodingException;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
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
@Api("Login")
@Path("/api")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class LoginResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginResource.class);
    private final LoginController loginController = new LoginController();

    public LoginResource() {
    }

    @ApiOperation(value = "Login", notes = "User login")
    @POST
    @Path("/login")
    public LoginModel login(UserForLogin user, @Context HttpServletRequest request) {
        long startTime = System.currentTimeMillis();
        ApiVersionValidator.validate(request);
        if (user.getUsername() == null || user.getPassword() == null) {
            LOGGER.error("Time of not POST login: " + (System.currentTimeMillis() - startTime)
                    + " milliseconds, statusCode:" + Response.Status.NOT_ACCEPTABLE.getStatusCode());
            throw new WebApplicationException("Fields are missing.", Response.Status.NOT_ACCEPTABLE);
        }
        LoginModel loginModel = new LoginModel();
        try {
            loginModel = loginController.login(request, user);
            if (loginModel == null) {
                LOGGER.error("Time of not POST login" + (System.currentTimeMillis() - startTime)
                    + " milliseconds, statusCode:" + Response.Status.BAD_REQUEST.getStatusCode());
                throw new WebApplicationException("Failed to create token", Response.Status.BAD_REQUEST);
            }
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
            LOGGER.error("Time of not POST login" + (System.currentTimeMillis() - startTime)
                    + " milliseconds, statusCode:" + Response.Status.BAD_REQUEST.getStatusCode());
            throw new WebApplicationException("User or password invalid", Response.Status.BAD_REQUEST);
        }
        LOGGER.info("Time of POST Login: " + (System.currentTimeMillis() - startTime) + " milliseconds, statusCode:"
                + Response.Status.OK.getStatusCode());
        return loginModel;
    }

    @ApiOperation(value = "Update password", notes = "Update user password")
    @PUT
    @Path("/changepassword")
    public Response updatePassword(ChangePassword password, @Context HttpServletRequest request) {
        long startTime = System.currentTimeMillis();
        ApiVersionValidator.validate(request);
        UserLoad userLoad = Authenticator.tokenValidation(request);
        if (password.getPassword().equals("") || password.getNewPassword().equals("")) {
            LOGGER.error("Time of not PUT password: " + (System.currentTimeMillis() - startTime)
                    + " milliseconds, statusCode:" + Response.Status.NOT_ACCEPTABLE.getStatusCode());
            throw new WebApplicationException("Fields are missing.", Response.Status.NOT_ACCEPTABLE);
        }
        if (!loginController.comparePassword(password, userLoad)) {
            LOGGER.error("Time of not PUT password" + (System.currentTimeMillis() - startTime)
                    + " milliseconds, statusCode:" + Response.Status.CONFLICT.getStatusCode());
            throw new WebApplicationException("Actual password not equal", Response.Status.CONFLICT);
        }
        if (!loginController.changePassword(password, userLoad)) {
            LOGGER.error("Time of not PUT password" + (System.currentTimeMillis() - startTime)
                    + " milliseconds, statusCode:" + Response.Status.BAD_REQUEST.getStatusCode());
            throw new WebApplicationException("Failed to change password", Response.Status.BAD_REQUEST);
        }
        LOGGER.info("Time of PUT password: " + (System.currentTimeMillis() - startTime) + " milliseconds, statusCode:"
                + Response.Status.OK.getStatusCode());
        return Response.status(Response.Status.OK).entity("OK").build();
    }

    @ApiOperation(value = "Update password", notes = "Update user password")
    @GET
    @Path("/recoverpassword")
    public Response getPassword(@QueryParam("mail") @DefaultValue("") String mail,
            @Context HttpServletRequest request) {
        long startTime = System.currentTimeMillis();
        ApiVersionValidator.validate(request);
        if (mail.equals("")) {
            LOGGER.error("Time of not send mail: " + (System.currentTimeMillis() - startTime)
                    + " milliseconds, statusCode:" + Response.Status.NOT_ACCEPTABLE.getStatusCode());
            throw new WebApplicationException("Fields are missing.", Response.Status.NOT_ACCEPTABLE);
        }
        //Verify mail is registered and verified
        User user = loginController.verifyMailExists(mail);
        if (user == null) {
            LOGGER.error("Time of not send mail" + (System.currentTimeMillis() - startTime)
                    + " milliseconds, statusCode:" + Response.Status.CONFLICT.getStatusCode());
            throw new WebApplicationException("Mail not registered in DB", Response.Status.CONFLICT);
        }
        try {
            EmailSender.sendMail(mail, user.getPassword());
            LOGGER.info("Time of send mail: " + (System.currentTimeMillis() - startTime)
                    + " milliseconds, statusCode:" + Response.Status.OK.getStatusCode());
            return Response.status(Response.Status.OK).entity("OK").build();
        } catch (UnsupportedEncodingException | MessagingException e) {
            LOGGER.error("Time of not send mail" + (System.currentTimeMillis() - startTime)
                    + " milliseconds, statusCode:" + Response.Status.BAD_REQUEST.getStatusCode());
            throw new WebApplicationException(e.getClass() == UnsupportedEncodingException.class
                    ? "Failed to create a message with the specified information " + e.getMessage()
                    : "Failed to send mail " + e.getMessage(), Response.Status.BAD_REQUEST);
        }
    }

}
