package org.jkdev.client;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.jkdev.file.properties.api.FilePropertiesDTO;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import java.util.List;

@Path("fileproperties")
@RegisterRestClient
public interface FilePropertiesClient {

    @GET
    @Path("/getFileProperties")
    List<FilePropertiesDTO> getFileProperties(@QueryParam("owner") String owner);

}
