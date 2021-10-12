/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gt.com.api.radiance.controllers;

import gt.com.api.radiance.entities.SuscriptionTypes;
import gt.com.api.radiance.queries.SuscriptionTypeQuery;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author malopez
 */
public class SuscriptionTypeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SuscriptionTypeController.class);

    public List<SuscriptionTypes> getSuscriptionTypes() {
        return SuscriptionTypeQuery.getSuscriptionTypes();
    }
}
