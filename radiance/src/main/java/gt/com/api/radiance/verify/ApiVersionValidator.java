/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gt.com.api.radiance.verify;

import gt.com.api.radiance.dtos.ApiVersion;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author malopez
 */
public final class ApiVersionValidator {

    private ApiVersionValidator() {
    }

    private static Integer apiVersion = ApiVersion.getInstance(0).getVersion();
    private static final Logger LOGGER = LoggerFactory.getLogger(ApiVersionValidator.class);

    public static void validate(HttpServletRequest request) {
        final String versionString = request.getHeader("Api-version");
        if (versionString == null || versionString.equals("")) {
            LOGGER.error("Api version not declared in the header or cannot be null, status code: "
                    + Response.status(Response.Status.PRECONDITION_REQUIRED));
            throw new WebApplicationException(
                    (versionString == null)
                            ? "Api version not declared in the header."
                            : "Api version field cannot be null.",
                    Response.Status.PRECONDITION_REQUIRED);
        }
        final int version = Integer.parseInt(versionString);
        if (apiVersion.toString().equals(version)) {
            LOGGER.error("Api version not equal, status code: "
                    + Response.status(Response.Status.CONFLICT));
            throw new WebApplicationException("Api version not equal.", Response.Status.CONFLICT);

        }
    }

}
