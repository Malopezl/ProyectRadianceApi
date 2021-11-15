/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gt.com.api.radiance.verify;

import gt.com.api.radiance.dtos.UserLoad;
import static org.jose4j.jws.AlgorithmIdentifiers.HMAC_SHA256;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.keys.HmacKey;
import org.jose4j.lang.JoseException;

/**
 *
 * @author malopez
 */
public final class Token {

    private Token() {
    }

    public static String createToken(UserLoad userLoad) throws JoseException {
        // create token
        final JwtClaims claims = new JwtClaims();
        claims.setExpirationTimeMinutesInTheFuture(120);
        claims.setIssuedAtToNow();
        claims.setClaim("user", userLoad.getUser());
        claims.setClaim("role", userLoad.getRole());

        final JsonWebSignature jws = new JsonWebSignature();
        jws.setPayload(claims.toJson());
        jws.setAlgorithmHeaderValue(HMAC_SHA256);
        byte[] tokenSecret = "super-secret".getBytes();
        jws.setKey(new HmacKey(tokenSecret));
        jws.setDoKeyValidation(false);

        String jwt = jws.getCompactSerialization();
        return jwt;
    }
}
