/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gt.com.api.radiance.verify;

import gt.com.api.radiance.dtos.UserLoad;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.setup.Environment;
import java.security.Key;
import javax.ws.rs.container.ContainerRequestFilter;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import org.jose4j.keys.HmacKey;
import org.jose4j.lang.JoseException;

/**
 *
 * @author malopez
 */
public class JwtRegister {

    public void register(Environment env) throws JoseException {
        JwtClaims claims = new JwtClaims();
        claims.setExpirationTimeMinutesInTheFuture(5);
        String secret = "super-secret";
        Key key = new HmacKey(secret.getBytes());

        JsonWebSignature jws = new JsonWebSignature();
        jws.setPayload(claims.toJson());
        jws.setAlgorithmHeaderValue(AlgorithmIdentifiers.HMAC_SHA256);
        jws.setKey(key);
        jws.setDoKeyValidation(false);

        jws.getCompactSerialization();

        final JwtConsumer consumer = new JwtConsumerBuilder()
                .setRequireExpirationTime()
                .setVerificationKey(key)
                .setRelaxVerificationKeyValidation()
                .build();

        env.jersey().register(new AuthDynamicFeature((ContainerRequestFilter)
                new JwtAuthFilter.Builder<UserLoad>()
                .setJwtConsumer(consumer)
                .setPrefix("Bearer")
                .setAuthenticator(new JwtLoginAuthenticator())
                .buildAuthFilter()));
    }

}
