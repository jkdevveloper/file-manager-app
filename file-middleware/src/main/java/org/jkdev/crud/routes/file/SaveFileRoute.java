package org.jkdev.crud.routes.file;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.ClaimCheckOperation;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.jkdev.crud.routes.properties.SaveFilePropertiesRoute;
import org.jkdev.file.storage.api.FileStorageDTO;
import org.jkdev.processor.FilePropertiesDTOBuilder;
import org.jkdev.processor.FileStorageDTOBuilder;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class SaveFileRoute extends RouteBuilder {

    public static final String SAVE_PDF = "direct:saveFile";

    @Inject
    FileStorageDTOBuilder fileStorageDTOBuilder;

    @Inject
    FilePropertiesDTOBuilder filePropertiesDTOBuilder;

    @Override
    public void configure() throws Exception {

        from(SAVE_PDF)
                .id("saveFileRoute")

                .setHeader("owner", simple("${body.owner}"))
                .setHeader("fileContent", simple("${body.content}"))
                .setHeader("extension", simple("${body.contentType}"))
                .setHeader("fileName", simple("${body.fileName}"))

                .claimCheck(ClaimCheckOperation.Push, "body")
                .process(filePropertiesDTOBuilder)
                .wireTap(SaveFilePropertiesRoute.SAVE_PDF_PROPERTIES)
                .claimCheck(ClaimCheckOperation.Pop, "body")
                .process(fileStorageDTOBuilder)
                .removeHeader("fileContent")
                .log("${headers}")
                .marshal().json(JsonLibrary.Jackson, FileStorageDTO.class)
                .removeHeaders("CamelHttp*")
                .to("http://file.storage.service:8081/storage-service/save-file")
                .log("Saved file with name ${header.fileName}")
                .removeHeaders("*")
                .setBody(constant("OK"))
                .setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
                .setHeader(Exchange.HTTP_RESPONSE_CODE, constant("200"))
                .end();


    }
}
