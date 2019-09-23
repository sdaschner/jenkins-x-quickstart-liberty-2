/*******************************************************************************
 * Copyright (c) 2018 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - Initial implementation
 *******************************************************************************/
package it.io.openliberty.sample.health;

import org.junit.Test;

import javax.json.JsonArray;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class HealthIT {

    private static Map<String, String> dataWhenServicesUP = Map.of("SystemResource", "UP");

    @Test
    public void testIfServicesAreUp() {
        JsonArray servicesStates = HealthTestUtil.connectToHealthEndpoint(200);
        checkServicesStates(dataWhenServicesUP, servicesStates);
    }

//    @Test
//    public void testIfServicesAreDown() {
//        JsonArray servicesStates = HealthTestUtil.connectToHealthEndpoint(200);
//        checkServicesStates(dataWhenServicesUP, servicesStates);
//    }

    private void checkServicesStates(Map<String, String> testData, JsonArray servicesStates) {
        testData.forEach((service, expectedState) ->
                assertEquals("The state of " + service + " service is not matching the ", expectedState,
                        HealthTestUtil.getActualState(service, servicesStates))
        );

    }
}
