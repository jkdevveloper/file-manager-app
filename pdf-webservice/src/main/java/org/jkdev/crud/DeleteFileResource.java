package org.jkdev.crud;

import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import io.quarkus.security.identity.SecurityIdentity;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.resteasy.annotations.Query;
import org.jkdev.client.FileClient;
import org.jkdev.client.FilePropertiesClient;
import org.jkdev.file.properties.api.FilePropertiesDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Collections;
import java.util.List;

@Path("")
@ApplicationScoped
public class DeleteFileResource {

    @Inject
    @RestClient
    FilePropertiesClient filePropertiesClient;

    @Inject
    @RestClient
    FileClient fileClient;

    @Inject
    SecurityIdentity userInfo;

    @Inject
    Template files;

    Logger logger = LoggerFactory.getLogger(DeleteFileResource.class);


    @GET
    @Path("delete")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance deleteFile(@QueryParam("id") String id, @QueryParam("fileIdentifier") String fileIdentifier){

        fileClient.deleteFile(id, fileIdentifier, userInfo.getPrincipal().getName());

        List<FilePropertiesDTO> filePropertiesDTOS = Collections.emptyList();
        try {
            filePropertiesDTOS = filePropertiesClient.getFileProperties(userInfo.getPrincipal().getName());
        } catch (Exception exception){
            logger.error("Exception during properties querying");
        }

        return files.data("username", userInfo.getPrincipal().getName(), "files", filePropertiesDTOS);
    }

}
