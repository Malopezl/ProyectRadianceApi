/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gt.com.api.radiance.verify;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import gt.com.api.radiance.dtos.UserLoad;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import org.jose4j.jwt.consumer.JwtContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author malopez
 */
public final class Authenticator {

    private static final Logger LOGGER = LoggerFactory.getLogger(Authenticator.class);

    private Authenticator() {
    }

    public static UserLoad tokenValidation(HttpServletRequest request) {
        final String authorization = request.getHeader("Authorization");
        String token = authorization.replaceAll("Bearer", "");
        ObjectMapper map = new ObjectMapper();
        JwtConsumer jwtConsumer = new JwtConsumerBuilder()
                .setSkipAllValidators()
                .setDisableRequireSignature()
                .setSkipSignatureVerification()
                .build();
        JwtContext context;
        UserLoad user = null;
        try {
            context = jwtConsumer.process(token);
            user = map.readValue(context.getJwtClaims().toJson(), UserLoad.class);
        } catch (JsonProcessingException | InvalidJwtException e) {
            LOGGER.error(e.getClass() == JsonProcessingException.class ? "Invalid json data in token " + e.getMessage()
                    : "Invalid token " + e.getMessage());
            throw new WebApplicationException(e.getClass() == JsonProcessingException.class
                    ? "Invalid json data in token " + e.getMessage()
                    : "Invalid token " + e.getMessage(), Response.Status.UNAUTHORIZED);
        }
        if (Long.valueOf(user.getExp()) > System.currentTimeMillis()) {
            LOGGER.error("expired token");
            throw new WebApplicationException(Response.Status.UNAUTHORIZED);
        }
        return user;
    }
}
