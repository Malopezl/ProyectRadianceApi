/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gt.com.api.radiance.queries;

import dev.morphia.Datastore;
import dev.morphia.query.Query;
import dev.morphia.query.experimental.filters.Filters;
import gt.com.api.radiance.entities.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author malopez
 */
public final class UserQuery {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserQuery.class);
    private static Datastore ds;

    private UserQuery() {
    }

    public static void setDataStore(Datastore datastore) {
        ds = datastore;
    }

    public static User findUser(String username) {
        Query<User> getUser = ds.find(User.class).filter(Filters.eq("user", username),
                Filters.eq("isDelete", Boolean.FALSE));
        try {
            return getUser.first();
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return null;
        }
    }
}
