package gt.com.api.radiance;

import dev.morphia.Datastore;
import gt.com.api.radiance.helper.MongoConfiguration;
import gt.com.api.radiance.helper.MorphiaPackageBundle;
import gt.com.api.radiance.helper.RadianceConfiguration;
import gt.com.api.radiance.queries.SubscriptionTypeQuery;
import gt.com.api.radiance.resources.SubscriptionTypeResource;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.federecio.dropwizard.swagger.SwaggerBundle;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main extends Application<RadianceConfiguration> {

    static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(final String[] args) throws Exception {
        new Main().run(args);
    }

    @Override
    public String getName() {
        return "Radiance";
    }

    private final MorphiaPackageBundle<RadianceConfiguration> morphiaBundle
            = new MorphiaPackageBundle<RadianceConfiguration>("gt.com.api.radiance.entities") {
                @Override
                protected MongoConfiguration getMongo(RadianceConfiguration configuration) {
                    return configuration.getMongo();
                }
            };

    @Override
    public void initialize(final Bootstrap<RadianceConfiguration> bootstrap) {
        bootstrap.addBundle(new SwaggerBundle<RadianceConfiguration>() {
            @Override
            protected SwaggerBundleConfiguration getSwaggerBundleConfiguration(
                    RadianceConfiguration configuration) {
                return configuration.getSwagger();
            }
        });
        bootstrap.addBundle(morphiaBundle);
    }

    @Override
    public void run(final RadianceConfiguration configuration,
                    final Environment environment) {
        //MongoDB datastore
        Datastore datastore = morphiaBundle.getDatastore();
        SubscriptionTypeQuery.setDataStore(datastore);

        //Resource register
        environment.jersey().register(new SubscriptionTypeResource());
    }

}
