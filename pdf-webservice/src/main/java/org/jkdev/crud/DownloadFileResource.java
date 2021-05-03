package org.jkdev.crud;

import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import io.quarkus.security.identity.SecurityIdentity;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jkdev.client.FileClient;
import org.jkdev.entity.PDFContent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import java.io.File;
import java.io.FileOutputStream;

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

    Logger logger = LoggerFactory.getLogger(DownloadFileResource.class);

    @Path("download")
    @GET
    public TemplateInstance downloadFile(@QueryParam("id") String id, @QueryParam("fileName") String fileName) {
        logger.info("Got some shit");

        String username = userInfo.getPrincipal().getName();

        logger.info(username);
        logger.info("username, fileName: {}, {}", username, fileName);

        PDFContent pdfContent = fileClient.getFile(username, fileName);
        logger.info("Got some shit {}", pdfContent);


        String home = System.getProperty("user.home");
        File file = new File(home + "/Downloads/" + pdfContent.getFileName() + "." + pdfContent.getContentType());

        try (FileOutputStream fos = new FileOutputStream(file)) {
            byte[] decoded = java.util.Base64.getDecoder().decode(pdfContent.getContent());
            fos.write(decoded);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return files.data("username", username);
    }

}
