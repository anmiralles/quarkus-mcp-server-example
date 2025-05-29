package me.amiralles.applications.boundary;

import io.quarkus.qute.Location;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import me.amiralles.applications.entity.Application;
import me.amiralles.applications.entity.ApplicationStatus;
import org.jboss.logging.Logger;
import org.jboss.resteasy.reactive.multipart.FileUpload;

import java.util.List;

import static jakarta.ws.rs.core.Response.Status.CREATED;

@ApplicationScoped
@Produces(MediaType.TEXT_HTML)
@Path("application.html")
public class ApplicationsAddResource {

    private static final Logger LOGGER = Logger.getLogger(ApplicationsAddResource.class.getName());

    @Location("application.html")
    Template template;

    @GET
    public TemplateInstance get() {
        return template.instance();
    }

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Transactional
    public Response addApplication(FormUploadInput input) {
        LOGGER.infof("Received request to create new application: %s %s %s",
                input.name, input.surname, input.personalId);
        Application application = new Application(input.name, input.surname, input.personalId,
                ApplicationStatus.STARTED, input.country);
        application.status = ApplicationStatus.STARTED;
        application.persist();
        LOGGER.info("Application successfully persisted to database");
        return Response.ok(application + " processed").status(CREATED).build();
    }

    public static class FormUploadInput {
        @FormParam("name")
        public String name;

        @FormParam("surname")
        public String surname;

        @FormParam("personalId")
        public String personalId;

        @FormParam("country")
        public String country;
    }
}
