package me.amiralles.mcp;

import io.quarkiverse.mcp.server.Tool;
import io.quarkiverse.mcp.server.ToolArg;
import io.quarkus.runtime.Startup;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import me.amiralles.mcp.client.ApplicationsClient;
import me.amiralles.mcp.model.Application;
import me.amiralles.mcp.model.ApplicationStatus;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;

import java.util.List;

@ApplicationScoped
public class McpApplicationsServer {

    private static final Logger LOGGER = Logger.getLogger(McpApplicationsServer.class);

    @Inject
    @ConfigProperty(name = "server.url")
    String baseUrl;

    @Inject
    @RestClient
    ApplicationsClient applicationsClient;

    @Startup
    void init() {
        LOGGER.info("Starting Favorite Coffee MCP server, application URL: " + baseUrl);
    }

    @Tool(description = "Lists all asylum requests in the system")
    public List<Application> list_applications() {
        LOGGER.info("MCP Tool: Listing all asylum requests");
        return applicationsClient.list_applications();
    }

    @Tool(description = "Lists an asylum request by its id")
    public Application get_application_by_id(
            @ToolArg(description = "The id of the asylum request to retrieve", required = true)
            Long id) {
        LOGGER.infof("MCP Tool: Getting asylum request by id: %d", id);
        return applicationsClient.get_application_by_id(id);
    }

    @Tool(description = "Lists asylum requests filtered by country name")
    public List<Application> get_applications_by_country(
            @ToolArg(description = "The name of the country to filter by", required = true)
            String countryName) {
        LOGGER.infof("MCP Tool: Getting asylum requests by country: %s", countryName);
        return applicationsClient.get_applications_by_country(countryName);
    }

    @Tool(description = "Lists asylum requests filtered by both country name and request status")
    public List<Application> get_applications_by_country_and_status(
            @ToolArg(description = "The name of the country to filter by", required = true)
            String countryName,
            @ToolArg(description = "The application status to filter by (STARTED, PREPROCESSED, PROCESSED, COMPLETED, CANCELED, ERROR)", required = true) 
            ApplicationStatus status) {
        LOGGER.infof("MCP Tool: Getting applications by country: %s and status: %s", countryName, status);
        return applicationsClient.get_applications_by_country_and_status(countryName, status);
    }

    @Tool(description = "Add asylum request to the system")
    public String add_application(
            @ToolArg(description = "The data for the asylum request (name, surname, personalId and country)", required = true) Application application) {
        LOGGER.infof("Received request to create new application: %s %s %s",
                application.name, application.surname, application.personalId);
        return applicationsClient.add_application(application);
    }

}