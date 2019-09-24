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
package io.openliberty.sample.system;

import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Liveness;
import org.eclipse.microprofile.health.Readiness;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@Readiness
@Liveness
@ApplicationScoped
public class SystemHealth implements HealthCheck {

    @Inject
    SystemConfig systemConfig;

    public boolean isHealthy() {
        if (systemConfig.isInMaintenance()) {
            return false;
        }
        return true;
    }

    @Override
    public HealthCheckResponse call() {
        if (!isHealthy()) {
            return HealthCheckResponse.named(SystemResource.class.getSimpleName())
                    .withData("services", "not available").down().build();
        }
        return HealthCheckResponse.named(SystemResource.class.getSimpleName())
                .withData("services", "available").up().build();
    }
}