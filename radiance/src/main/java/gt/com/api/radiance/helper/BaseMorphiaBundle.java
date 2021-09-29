/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gt.com.api.radiance.helper;

import dev.morphia.Datastore;
import gt.com.api.radiance.health.MongoHealthCheck;
import io.dropwizard.Configuration;
import io.dropwizard.ConfiguredBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

/**
 *
 * @author malopez
 * @param <T>
 */
public abstract class BaseMorphiaBundle<T extends Configuration> implements ConfiguredBundle<T> {

    protected static final String DEFAULT_NAME = "mongo";
    protected Datastore datastore;


    @Override
    public void initialize(Bootstrap<?> bootstrap) {
    }


    @Override
    public void run(T configuration, Environment environment) throws Exception {
        environment.healthChecks().register(getName(), new MongoHealthCheck(datastore));
    }


    protected String getName() {
        return DEFAULT_NAME;
    }


    public Datastore getDatastore() {
        return datastore;
    }


    protected abstract MongoConfiguration getMongo(T configuration);

}
