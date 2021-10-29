/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gt.com.api.radiance.dtos;

import java.util.List;
import java.util.Objects;

/**
 *
 * @author malopez
 */
public class UserPage extends Page {

    private List<UserModel> users;

    public UserPage() {
    }

    public List<UserModel> getUsers() {
        return users;
    }

    public void setUsers(List<UserModel> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "UserPage{" + "users=" + users + '}';
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 79 * hash + Objects.hashCode(this.users);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final UserPage other = (UserPage) obj;
        return Objects.equals(this.users, other.users);
    }

}
