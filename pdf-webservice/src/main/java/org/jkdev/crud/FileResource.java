package org.jkdev.crud;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import io.quarkus.security.identity.SecurityIdentity;
import org.apache.pdfbox.io.IOUtils;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.jkdev.client.FileClient;
import org.jkdev.client.FilePropertiesClient;
import org.jkdev.entity.PDFContent;
import org.jkdev.file.properties.api.FilePropertiesDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import java.io.InputStream;
import java.util.*;

@Path("")
public class FileResource {

    Logger logger = LoggerFactory.getLogger(FileResource.class);

    @Inject
    @RestClient
    FileClient fileClient;

    @Inject
    SecurityIdentity userInfo;

    @Inject
    @RestClient
    FilePropertiesClient filePropertiesClient;

    @Inject
    Template files;

    ObjectMapper objectMapper = new ObjectMapper().registerModules(new Jdk8Module());

    @POST
    @Path("files/sendFile")
    @Consumes({MediaType.MULTIPART_FORM_DATA})
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance getFileAndSendViaREST(@MultipartForm MultipartFormDataInput upload) {
        Map<String, List<InputPart>> uploadForm = upload.getFormDataMap();
        List<String> fileNames = new ArrayList<>();

        List<InputPart> inputParts = uploadForm.get("file");

        logger.info("Files count:{}", inputParts.size());

        String fileName = null;

        for (InputPart inputPart : inputParts) {
            try {
                MultivaluedMap<String, String> header = inputPart.getHeaders();
                fileName = getFileName(header);
                InputStream inputStream = inputPart.getBody(InputStream.class, null);

                byte[] bytes = IOUtils.toByteArray(inputStream);

                String fileData = Base64.getMimeEncoder().encodeToString(bytes);
                PDFContent pdfContent = new PDFContent();
                pdfContent.setContent(fileData);
                pdfContent.setFileName(fileName);
                pdfContent.setOwner(userInfo.getPrincipal().getName());
                pdfContent.setContentType("PDF");

                fileClient.sendFile(objectMapper.writeValueAsString(pdfContent));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        List<FilePropertiesDTO> filePropertiesDTOS = Collections.emptyList();
        try {
            filePropertiesDTOS = filePropertiesClient.getFileProperties(userInfo.getPrincipal().getName());
        } catch (Exception exception){
            logger.error("Exception during properties querying");
        }

        return files.data("files", filePropertiesDTOS, "username", userInfo.getPrincipal().getName());
    }

    private String getFileName(MultivaluedMap<String, String> header) {
        String[] contentDisposition = header.getFirst("Content-Disposition").split(";");
        for (String filename : contentDisposition) {
            if ((filename.trim().startsWith("filename"))) {
                String[] name = filename.split("=");
                String finalFileName = name[1].trim().replaceAll("\"", "");
                return finalFileName;
            }
        }
        return "unknown";
    }

    @GET
    @Path("files")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance get() {
        List<FilePropertiesDTO> filePropertiesDTOS = Collections.emptyList();
        try {
            filePropertiesDTOS = filePropertiesClient.getFileProperties(userInfo.getPrincipal().getName());
        } catch (Exception exception){
            logger.error("Exception during properties querying");
        }

        return files.data("files", filePropertiesDTOS, "username", userInfo.getPrincipal().getName());
    }
}
