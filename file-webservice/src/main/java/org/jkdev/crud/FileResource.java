package org.jkdev.crud;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import io.quarkus.security.identity.SecurityIdentity;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.jkdev.client.FileClient;
import org.jkdev.client.FilePropertiesClient;
import org.jkdev.entity.FileContent;
import org.jkdev.file.properties.api.FilePropertiesDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.Map;

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

    public static ByteArrayOutputStream toByteArray(InputStream in) throws IOException {
        ByteArrayOutputStream baout = new ByteArrayOutputStream();
        copy(in, baout);
        return baout;
    }

    public static long copy(InputStream input, OutputStream output) throws IOException {
        byte[] buffer = new byte[4096];
        long count = 0L;

        int n;
        for (; -1 != (n = input.read(buffer)); count += n) {
            output.write(buffer, 0, n);
        }

        return count;
    }

    @POST
    @Path("files/sendFile")
    @Consumes({MediaType.MULTIPART_FORM_DATA})
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance getFileAndSendViaREST(@MultipartForm MultipartFormDataInput upload) {
        Map<String, List<InputPart>> uploadForm = upload.getFormDataMap();

        List<InputPart> inputParts = uploadForm.get("file");

        logger.info("Files count:{}", inputParts.size());

        String fileName;

        for (InputPart inputPart : inputParts) {
            try {
                MultivaluedMap<String, String> header = inputPart.getHeaders();
                fileName = getFileName(header);
                InputStream inputStream = inputPart.getBody(InputStream.class, null);

                ByteArrayOutputStream baos = toByteArray(inputStream);
                byte[] bytes = baos.toByteArray();

                baos.close();
                inputStream.close();
                String fileData = Base64.getMimeEncoder().encodeToString(bytes);
                FileContent fileContent = new FileContent();
                logger.info(fileData);
                fileContent.setContent(fileData);
                fileContent.setFileName(fileName);
                fileContent.setOwner(userInfo.getPrincipal().getName());

                fileClient.sendFile(objectMapper.writeValueAsString(fileContent));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        List<FilePropertiesDTO> filePropertiesDTOS = Collections.emptyList();
        try {
            filePropertiesDTOS = filePropertiesClient.getFileProperties(userInfo.getPrincipal().getName());
        } catch (Exception exception) {
            logger.error("Exception during properties querying");
        }

        return files.data("files", filePropertiesDTOS, "username", userInfo.getPrincipal().getName());
    }

    private String getFileName(MultivaluedMap<String, String> header) {
        String[] contentDisposition = header.getFirst("Content-Disposition").split(";");
        for (String filename : contentDisposition) {
            if ((filename.trim().startsWith("filename"))) {
                String[] name = filename.split("=");
                return name[1].trim().replaceAll("\"", "");
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
        } catch (Exception exception) {
            logger.error("Exception during properties querying");
        }

        return files.data("files", filePropertiesDTOS, "username", userInfo.getPrincipal().getName());
    }
}
