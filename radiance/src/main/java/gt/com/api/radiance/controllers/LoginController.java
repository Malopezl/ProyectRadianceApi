/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gt.com.api.radiance.controllers;

import gt.com.api.radiance.dtos.LoginModel;
import gt.com.api.radiance.dtos.UserForLogin;
import gt.com.api.radiance.dtos.UserLoad;
import gt.com.api.radiance.entities.Payment;
import gt.com.api.radiance.entities.SubscriptionType;
import gt.com.api.radiance.entities.User;
import gt.com.api.radiance.helper.Roles;
import gt.com.api.radiance.queries.SubscriptionTypeQuery;
import gt.com.api.radiance.queries.UserQuery;
import gt.com.api.radiance.verify.Token;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author malopez
 */
public class LoginController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

    public LoginModel login(@Context HttpServletRequest request, UserForLogin userLogin) throws Exception {
        User user = UserQuery.findUser(userLogin.getUsername());
        if (user == null) {
            LOGGER.error("User not found");
            throw new Exception("User not found in DB");
        }
        if (!userLogin.getPassword().equals(user.getPassword())) {
            throw new Exception("Incorrect password");
        }
        if (user.getRole().equals("")) {
            LOGGER.error("User has no role");
            return null;
        }
        if (user.getSubscription() != null && !user.getSubscription().getStatus()) {
            LOGGER.error("User has no active subscription");
            return null;
        }
        if (!user.getRole().equals(Roles.Role.Administrador.toString())
                && Long.valueOf(user.getSubscription().getFinalizationDate()) <= System.currentTimeMillis()) {
            LOGGER.info(String.valueOf(System.currentTimeMillis()));
            Payment payment = new Payment();
            payment.setDate(String.valueOf(System.currentTimeMillis()));
            SubscriptionType subscriptionType = SubscriptionTypeQuery.getSubscriptionType(
                    user.getSubscription().getSubscriptionTypeId());
            payment.setAmount(subscriptionType.getPrice());
            payment.setUserId(user.getId());
            if (!PaymentController.savePayment(payment)) {
                LOGGER.error("Failed to save payment, deleting user");
                throw new Exception("Cannot save payment for user: " + userLogin.getUsername());
            }
        }
        LoginModel loginModel = new LoginModel();
        loginModel.setRole(user.getRole());
        UserLoad userLoad = new UserLoad();
        userLoad.setUser(user.getUser());
        userLoad.setRole(user.getRole());

        String token = Token.createToken(userLoad);
        if (token == null) {
            LOGGER.error("Cannot generate token");
            throw new Exception("Cannot generate token");
        }
        loginModel.setAccessToken(token);
        return loginModel;
    }
}
