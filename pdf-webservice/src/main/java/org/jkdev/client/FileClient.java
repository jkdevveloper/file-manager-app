package org.jkdev.client;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.jkdev.entity.PDFContent;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("files")
@RegisterRestClient
public interface FileClient {

    @POST
    @Path("/sendPDF")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Response sendFile(String fileContent);

    @DELETE
    @Path("/deletePDF")
    Response deleteFile(@QueryParam("id") String id, @QueryParam("fileIdentifier") String fileIdentifier, @QueryParam("fileOwner") String fileOwner);


    @GET
    @Path("/getPDF")
    PDFContent getFile(@QueryParam("owner") String id, @QueryParam("fileName") String fileIdentifier);

}
