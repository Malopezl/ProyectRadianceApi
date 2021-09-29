/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gt.com.api.radiance.helper;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import dev.morphia.Datastore;
import io.dropwizard.jackson.Discoverable;
import io.dropwizard.setup.Environment;

/**
 *
 * @author malopez
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
    @JsonSubTypes.Type(value = UriMongoConfiguration.class, name = "uriConfig")
})
public interface MongoConfiguration extends Discoverable {

    MongoConfiguration using(Environment environment);

    Datastore buildDatastore();
}
