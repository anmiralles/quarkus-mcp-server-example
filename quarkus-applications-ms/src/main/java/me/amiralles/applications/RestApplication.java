/*
 * Copyright Debezium Authors.
 *
 * Licensed under the Apache Software License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package me.amiralles.applications;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

/**
 * Defines the application path for the Order Service rest application.
 */
@ApplicationPath("/")
public class RestApplication extends Application {
}
