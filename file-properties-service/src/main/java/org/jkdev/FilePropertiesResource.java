package org.jkdev;

import org.jkdev.file.properties.api.FilePropertiesDTO;
import org.jkdev.service.FilePropertiesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path(FilePropertiesResource.PROPERTIES_SERVICE)
public class FilePropertiesResource {

    public static final String PROPERTIES_SERVICE = "properties-service";
    private static final String SAVE_FILE_PROPERTIES = "save-file-properties";
    private static final String GET_FILE_PROPERTIES = "get-file-properties";
    private static final String UPDATE_FILE_PROPERTIES = "update-file-properties";
    private static final String DELETE_FILE_PROPERTIES = "delete-file-properties";

    @Inject
    private FilePropertiesService filePropertiesService;

    private static final Logger logger = LoggerFactory.getLogger(FilePropertiesResource.class);

    @POST
    @Path(SAVE_FILE_PROPERTIES)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response saveFileProperties(FilePropertiesDTO filePropertiesDTO) {
        logger.info("Received request to save entity: {}", filePropertiesDTO);
        filePropertiesService.saveFileProperties(filePropertiesDTO);

        return Response.ok().build();
    }

    @GET
    @Path(GET_FILE_PROPERTIES)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFilePropertiesByParameters(@QueryParam("fileOwner") String fileOwner,
                                                  @QueryParam("fileName") String fileName,
                                                  @QueryParam("dateUploaded") String dateUploaded,
                                                  @QueryParam("fileIdentifier") String fileIdentifier) {
        return Response.ok().entity(filePropertiesService.getFileProperties(fileName, fileOwner, dateUploaded, fileIdentifier)).build();
    }

    @PUT
    @Path(UPDATE_FILE_PROPERTIES)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateFileProperties(FilePropertiesDTO filePropertiesDTO) {
        logger.debug("Received request to UPDATE entity: {}", filePropertiesDTO);
        filePropertiesService.updateFileProperties(filePropertiesDTO);

        return Response.ok().build();
    }

    @DELETE
    @Path(DELETE_FILE_PROPERTIES)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteFileProperties(@QueryParam("id") Long id) {
        logger.debug("Received request to DELETE by id: {}", id);
        filePropertiesService.deleteFilePropertiesById(id);

        return Response.ok().build();
    }

}