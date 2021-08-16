package org.jkdev.client;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.jkdev.entity.FileContent;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
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
    Response deleteFile(@QueryParam("fileOwner") String fileOwner, @QueryParam("fileIdentifier") String fileIdentifier);


    @GET
    @Path("/getPDF")
    FileContent getFile(@QueryParam("owner") String owner, @QueryParam("fileName") String fileName);

}
