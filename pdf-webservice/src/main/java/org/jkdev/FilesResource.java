package org.jkdev;

import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jkdev.client.FilePropertiesClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("files")
public class FilesResource {

    @Inject
    @RestClient
    FilePropertiesClient filePropertiesClient;

    @Inject
    Template files;

    Logger logger = LoggerFactory.getLogger(PanelResource.class);

    @GET
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance get() {

        return files.data("files", filePropertiesClient.getFileProperties("Jakub"));
    }


}
