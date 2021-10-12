/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gt.com.api.radiance.queries;

import dev.morphia.Datastore;
import dev.morphia.query.MorphiaCursor;
import dev.morphia.query.Query;
import dev.morphia.query.experimental.filters.Filters;
import gt.com.api.radiance.entities.SuscriptionTypes;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author malopez
 */
public final class SuscriptionTypeQuery {

    private static final Logger LOGGER = LoggerFactory.getLogger(SuscriptionTypeQuery.class);
    private static Datastore ds;

    private SuscriptionTypeQuery() {
    }

    public static void setDataStore(Datastore datastore) {
        ds = datastore;
    }

    public static List<SuscriptionTypes> getSuscriptionTypes() {
        Query<SuscriptionTypes> getTypes = ds.find(SuscriptionTypes.class)
                .filter(Filters.and(Filters.eq("isDelete", false)));
        List<SuscriptionTypes> list = new ArrayList<>();
        MorphiaCursor<SuscriptionTypes> cursor = getTypes.iterator();
        while (cursor.hasNext()) {
            SuscriptionTypes next = cursor.next();
            list.add(next);
        }
        return list;
    }
}
