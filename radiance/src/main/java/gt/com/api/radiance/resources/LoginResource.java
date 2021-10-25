/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gt.com.api.radiance.resources;

import gt.com.api.radiance.controllers.LoginController;
import gt.com.api.radiance.dtos.LoginModel;
import gt.com.api.radiance.dtos.UserForLogin;
import gt.com.api.radiance.verify.ApiVersionValidator;
import io.swagger.annotations.ApiOperation;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author malopez
 */
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
}
