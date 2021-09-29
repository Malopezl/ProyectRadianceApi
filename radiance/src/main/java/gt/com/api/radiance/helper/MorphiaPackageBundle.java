/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gt.com.api.radiance.helper;

import io.dropwizard.Configuration;
import io.dropwizard.setup.Environment;

/**
 *
 * @author malopez
 * @param <T>
 */
public abstract class MorphiaPackageBundle<T extends Configuration> extends BaseMorphiaBundle<T> {

    private final String packageName;

    protected MorphiaPackageBundle(String packageName) {
        this.packageName = packageName;
    }

    @Override
    public void run(T configuration, Environment environment) throws Exception {

        datastore = getMongo(configuration)
                .using(environment)
                .buildDatastore();
        datastore.getMapper().mapPackage(packageName);

        super.run(configuration, environment);
    }

}
