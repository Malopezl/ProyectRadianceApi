package gt.com.api.radiance;

import dev.morphia.Datastore;
import gt.com.api.radiance.dtos.ApiVersion;
import gt.com.api.radiance.helper.MongoConfiguration;
import gt.com.api.radiance.helper.MorphiaPackageBundle;
import gt.com.api.radiance.helper.RadianceConfiguration;
import gt.com.api.radiance.queries.ArticleQuery;
import gt.com.api.radiance.queries.CommentQuery;
import gt.com.api.radiance.queries.PaymentQuery;
import gt.com.api.radiance.queries.SubscriptionTypeQuery;
import gt.com.api.radiance.queries.TagQuery;
import gt.com.api.radiance.queries.UserQuery;
import gt.com.api.radiance.resources.ArticleResource;
import gt.com.api.radiance.resources.CommentResource;
import gt.com.api.radiance.resources.LoginResource;
import gt.com.api.radiance.resources.PaymentResource;
import gt.com.api.radiance.resources.SubscriptionTypeResource;
import gt.com.api.radiance.resources.TagResource;
import gt.com.api.radiance.resources.UserResource;
import gt.com.api.radiance.verify.JwtRegister;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.federecio.dropwizard.swagger.SwaggerBundle;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;
import java.util.EnumSet;
import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import org.eclipse.jetty.servlets.CrossOriginFilter;
import org.jose4j.lang.JoseException;
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
                    final Environment environment) throws JoseException {
        ApiVersion.getInstance(configuration.getApiVersion());
        //Jwt
        JwtRegister jwtRegister = new JwtRegister();
        jwtRegister.register(environment);

        //MongoDB datastore
        Datastore datastore = morphiaBundle.getDatastore();
        UserQuery.setDataStore(datastore);
        SubscriptionTypeQuery.setDataStore(datastore);
        TagQuery.setDataStore(datastore);
        PaymentQuery.setDataStore(datastore);
        ArticleQuery.setDataStore(datastore);
        CommentQuery.setDataStore(datastore);

        //Configure CORS parameters
        final FilterRegistration.Dynamic cors
                = environment.servlets().addFilter("CORS", CrossOriginFilter.class);
        cors.setInitParameter(CrossOriginFilter.ALLOWED_ORIGINS_PARAM, "*");
        cors.setInitParameter(CrossOriginFilter.ALLOWED_HEADERS_PARAM,
                "X-Requested-With,Content-Type,Accept,Origin,Authorization,Api-version");
        cors.setInitParameter(CrossOriginFilter.ALLOWED_METHODS_PARAM, "OPTIONS,GET,PUT,POST,DELETE,HEAD");
        cors.setInitParameter(CrossOriginFilter.ALLOW_CREDENTIALS_PARAM, "true");
        cors.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");

        //Resource register
        environment.jersey().register(new LoginResource());
        environment.jersey().register(new SubscriptionTypeResource());
        environment.jersey().register(new TagResource());
        environment.jersey().register(new UserResource());
        environment.jersey().register(new PaymentResource());
        environment.jersey().register(new ArticleResource());
        environment.jersey().register(new CommentResource());
    }

}
