package org.jkdev.crud.routes.file;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.jkdev.file.properties.api.FilePropertiesDTO;
import org.jkdev.file.storage.api.FileStorageDTO;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;

@ApplicationScoped
public class GetFileRoute extends RouteBuilder {

    public static final String GET_PDF = "direct:getFile";

    @Inject
    ObjectMapper objectMapper;

    @Override
    public void configure() throws Exception {

        from(GET_PDF)
                .id("getFileRoute")
                .log("get PDF for owner: ${header.owner}, with name: ${header.fileName}")
                    .log("querying properties")
                    .removeHeaders("CamelHttp*")
                    .setHeader(Exchange.HTTP_METHOD, constant("GET"))
                    .log("${headers} sending to get properties")
                    .toD("http://localhost:8082/properties-service/get-file-properties?fileOwner=${header.owner}&fileName=${header.fileName}")
                    .process(e -> {
                        List<FilePropertiesDTO> filePropertiesDTOS = objectMapper.readValue(e.getIn().getBody(String.class), new TypeReference<>() {});
                        FilePropertiesDTO filePropertiesDTO = filePropertiesDTOS.get(0);
                        e.getIn().setHeader("fileIdentifier", filePropertiesDTO.getFileIdentifier());
                        e.getIn().setHeader("fileOwner", filePropertiesDTO.getFileOwner());
                    })
                    .log("${headers} after get properties")
                    .removeHeaders("CamelHttp*")
                    .setHeader(Exchange.HTTP_METHOD, constant("GET"))
                    .to("http://localhost:8081/storage-service/getPDFFromFTP")
                    .unmarshal().json(JsonLibrary.Jackson, FileStorageDTO.class)
                    .log("${body}")
                .end();
    }
}
