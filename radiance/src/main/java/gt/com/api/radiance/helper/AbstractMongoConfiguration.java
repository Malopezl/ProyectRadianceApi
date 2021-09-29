/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gt.com.api.radiance.helper;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import static com.google.common.base.Preconditions.checkNotNull;
import com.mongodb.client.MongoClient;
import dev.morphia.Datastore;
import dev.morphia.Morphia;
import io.dropwizard.lifecycle.Managed;
import io.dropwizard.setup.Environment;

/**
 *
 * @author malopez
 */
public abstract class AbstractMongoConfiguration implements MongoConfiguration {

    private String dbName;

    private Environment environment;

    @JsonIgnore
    @Override
    public AbstractMongoConfiguration using(Environment environment) {
        this.environment = checkNotNull(environment);
        return this;
    }

    @JsonIgnore
    @Override
    public Datastore buildDatastore() {
        final MongoClient client = buildClient();

        final Datastore datastore = Morphia.createDatastore(client, checkNotNull(getDbName()));

        environment.lifecycle().manage(
                new Managed() {
                @Override
                public void start() throws Exception {
                }

                @Override
                public void stop() throws Exception {
                    client.close();
                }
            }
        );
        return datastore;
    }

    @JsonIgnore
    protected abstract MongoClient buildClient();

    @JsonProperty
    public String getDbName() {
        return dbName;
    }

    @JsonProperty
    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

}
