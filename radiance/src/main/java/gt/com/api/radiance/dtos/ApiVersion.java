/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gt.com.api.radiance.dtos;

/**
 *
 * @author malopez
 */
public class ApiVersion {

    private final Integer version;
    private static ApiVersion myApiVersion;

    public ApiVersion(Integer version) {
        this.version = version;
    }

    public static ApiVersion getInstance(Integer version) {
        if (myApiVersion == null) {
            myApiVersion = new ApiVersion(version);
        }
        return myApiVersion;
    }

    public Integer getVersion() {
        return version;
    }

}
