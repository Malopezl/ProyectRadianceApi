/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gt.com.api.radiance.dtos;

import java.security.Principal;

/**
 *
 * @author malopez
 */
public class UserLoad implements Principal {

    private String userId;
    private String role;

    public UserLoad() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "UserLoad{" + "userId=" + userId + ", role=" + role + '}';
    }

    @Override
    public String getName() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
