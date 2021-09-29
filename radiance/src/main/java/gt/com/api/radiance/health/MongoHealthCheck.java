/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gt.com.api.radiance.health;

import com.codahale.metrics.health.HealthCheck;
import com.mongodb.MongoException;
import dev.morphia.Datastore;
import org.bson.Document;

/**
 *
 * @author malopez
 */
public class MongoHealthCheck extends HealthCheck {

    private final Datastore datastore;

    public MongoHealthCheck(Datastore datastore) {
        this.datastore = datastore;
    }

    @Override
    protected Result check() throws Exception {
        try {
            final Document stats = datastore.getDatabase().runCommand(new Document("dbStats", 1));
            if (stats.containsKey("ok") && (stats.getDouble("ok") == 0d)) {
                return Result.unhealthy("run command dbStats it's not possible");
            }
        } catch (MongoException ex) {
            return Result.unhealthy(ex.getMessage());
        }
        return Result.healthy();
    }

}
