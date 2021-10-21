/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gt.com.api.radiance.verify;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import gt.com.api.radiance.dtos.UserLoad;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import java.io.IOException;
import java.util.Optional;
import org.jose4j.jwt.consumer.JwtContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author malopez
 */
public class JwtLoginAuthenticator implements Authenticator<JwtContext, UserLoad> {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtLoginAuthenticator.class);
    private ObjectMapper objectMapper;

    @Override
    public Optional<UserLoad> authenticate(JwtContext c) throws AuthenticationException {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.setConfig(
            this.objectMapper.getSerializationConfig().without(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS));
        this.objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        this.objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            c.getJwtClaims().getClaimsMap();
            Object jsonString = c.getJwtClaims().getClaimsMap();
            byte[] jsonObject = this.objectMapper.writeValueAsBytes(jsonString);
            UserLoad userLoad = this.objectMapper.readValue(jsonObject, UserLoad.class);
            return Optional.of(userLoad);
        } catch (IOException e) {
            LOGGER.error("JwtPaymentsAuthenticator:error" + e.getMessage());
            return Optional.empty();
        }
    }

}
