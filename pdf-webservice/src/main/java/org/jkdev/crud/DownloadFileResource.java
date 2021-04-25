package org.jkdev.crud;

import io.quarkus.oidc.UserInfo;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import io.quarkus.security.identity.SecurityIdentity;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jkdev.client.FileClient;
import org.jkdev.entity.PDFContent;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

@ApplicationScoped
@Path("")
public class DownloadFileResource {

    @Inject
    SecurityIdentity userInfo;

    @Inject
    Template files;

    @Inject
    @RestClient
    FileClient fileClient;

    @Path("download")
    @GET
    public TemplateInstance downloadFile(@QueryParam("id") String id){
        String username = userInfo.getPrincipal().getName();

        PDFContent pdfContent = fileClient.getFile(id, username);

        return files.data("username", username);
    }

}
