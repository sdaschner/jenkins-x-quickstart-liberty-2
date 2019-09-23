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

import org.apache.cxf.jaxrs.provider.jsrjsonp.JsrJsonpProvider;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;

import static org.junit.Assert.assertEquals;

final class HealthTestUtil {

    private static String baseUrl;
    private final static String HEALTH_ENDPOINT = "health";

    static {
        String host = System.getProperty("liberty.test.host", "localhost");
        String port = System.getProperty("liberty.test.port", "9080");
        baseUrl = "http://" + host + ':' + port + "/";
    }

    static JsonArray connectToHealthEndpoint(int expectedResponseCode) {
        String healthURL = baseUrl + HEALTH_ENDPOINT;
        Client client = ClientBuilder.newClient().register(JsrJsonpProvider.class);
        Response response = client.target(healthURL).request().get();
        assertEquals("Response code is not matching " + healthURL, expectedResponseCode, response.getStatus());
        JsonObject entity = response.readEntity(JsonObject.class);
        client.close();

        System.out.println(">>>");
        System.out.println(entity);

        JsonArray servicesStates = entity.getJsonArray("checks");

        System.out.println(">>>");
        System.out.println(servicesStates);

        return servicesStates;
    }

    static String getActualState(String service, JsonArray servicesStates) {
        String state = "";
        for (Object obj : servicesStates) {
            if (obj instanceof JsonObject) {
                if (service.equals(((JsonObject) obj).getString("name"))) {
                    state = ((JsonObject) obj).getString("status");
                }
            }
        }
        return state;
    }

}
