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

    private String user;
    private String role;
    private String exp;
    private String iat;

    public UserLoad() {
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getExp() {
        return exp;
    }

    public void setExp(String exp) {
        this.exp = exp;
    }

    public String getIat() {
        return iat;
    }

    public void setIat(String iat) {
        this.iat = iat;
    }

    @Override
    public String toString() {
        return "UserLoad{" + "user=" + user + ", role=" + role + '}';
    }

    @Override
    public String getName() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
