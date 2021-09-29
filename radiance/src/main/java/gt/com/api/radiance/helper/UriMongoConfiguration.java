/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gt.com.api.radiance.helper;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

/**
 *
 * @author malopez
 */
@JsonTypeName("uriConfig")
public class UriMongoConfiguration extends AbstractMongoConfiguration {

    @JsonSerialize(using = MongoClientUriSerializer.class)
    private MongoClientURI uri;

    @Override
    protected MongoClient buildClient() {
        final MongoClient mongoClient = MongoClients.create(uri.toString());

        String database = uri.getDatabase();
        if (database != null) {
            setDbName(database);
        }

        return mongoClient;
    }

    @JsonProperty
    public MongoClientURI getUri() {
        return uri;
    }

    @JsonProperty
    public void setUri(MongoClientURI uri) {
        this.uri = uri;
    }
}
