/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gt.com.api.radiance.helper;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.mongodb.MongoClientURI;
import java.io.IOException;

/**
 *
 * @author malopez
 */
public class MongoClientUriSerializer extends JsonSerializer<MongoClientURI> {

    @Override
    public void serialize(MongoClientURI t, JsonGenerator jg, SerializerProvider sp) throws IOException {
        if (t != null) {
            jg.writeString(t.getURI());
        }
    }

}
