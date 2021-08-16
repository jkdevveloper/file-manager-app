package org.jkdev;

import org.jkdev.file.storage.api.FileStorageDTO;
import org.jkdev.service.FileStorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("storage-service")
public class FileStorageResource {

    private static final Logger logger = LoggerFactory.getLogger(FileStorageResource.class);

    @Inject
    FileStorageService fileStorageService;

    @POST
    @Path("save-file")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response saveOrUpdateFile(FileStorageDTO fileStorageDTO) {
        logger.info("GOT SAVE FILE REQUEST");
        fileStorageService.saveFile(fileStorageDTO);

        return Response.ok().build();

    }

    @DELETE
    @Path("delete-file")
    public Response deleteFile(@QueryParam("fileOwner") String fileOwner,
                               @QueryParam("fileIdentifier") String fileIdentifier) {

        fileStorageService.deleteFile(fileIdentifier, fileOwner);

        return Response.ok().build();
    }

    @GET
    @Path("/get-pdf")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFile(@QueryParam("fileOwner") String fileOwner,
                            @QueryParam("fileIdentifier") String fileIdentifier) {

        FileStorageDTO fileStorageDTO = fileStorageService.getFile(fileIdentifier, fileOwner);

        return Response.ok().entity(fileStorageDTO).build();
    }
}
