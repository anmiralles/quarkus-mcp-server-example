package me.amiralles.mcp.client;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import me.amiralles.mcp.model.Application;
import me.amiralles.mcp.model.ApplicationStatus;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.List;

@Path("/applications")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RegisterRestClient(configKey = "applications-api")
public interface ApplicationsClient {

    @GET
    List<Application> list_applications();

    @GET
    @Path("/application/{id}")
    Application get_application_by_id(@PathParam("id") Long id);

    @GET
    @Path("/country/{countryName}")
    List<Application> get_applications_by_country(@PathParam("countryName") String countryName);

    @GET
    @Path("/country/{countryName}/status/{status}")
    List<Application> get_applications_by_country_and_status(
            @PathParam("countryName") String countryName,
            @PathParam("status") ApplicationStatus status);

    @POST
    String add_application(Application application);

}