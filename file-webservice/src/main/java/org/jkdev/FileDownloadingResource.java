package org.jkdev;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jkdev.client.FileClient;
import org.jkdev.entity.FileContent;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

@Path("/download")
public class FileDownloadingResource {

    @Inject
    @RestClient
    FileClient fileClient;

    @GET
    @Produces("application/json")
    public FileContent downloadFile(@QueryParam("fileName") String fileName, @QueryParam("owner") String fileOwner) {
        return fileClient.getFile(fileOwner, fileName);
    }
}
