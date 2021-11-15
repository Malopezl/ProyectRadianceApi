package gt.com.api.radiance.helper;

import io.dropwizard.Configuration;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class RadianceConfiguration extends Configuration {

    @Valid
    @NotNull
    private SwaggerBundleConfiguration swagger = new SwaggerBundleConfiguration();

    @Valid
    @NotNull
    private String applicationName;

    @Valid
    @NotNull
    private MongoConfiguration mongo;

    @Valid
    @NotNull
    private String secretKey;

    @Valid
    @NotNull
    private Integer apiVersion;

    public SwaggerBundleConfiguration getSwagger() {
        return swagger;
    }

    public void setSwagger(SwaggerBundleConfiguration swagger) {
        this.swagger = swagger;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public MongoConfiguration getMongo() {
        return mongo;
    }

    public void setMongo(MongoConfiguration mongo) {
        this.mongo = mongo;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public Integer getApiVersion() {
        return apiVersion;
    }

    public void setApiVersion(Integer apiVersion) {
        this.apiVersion = apiVersion;
    }

}
