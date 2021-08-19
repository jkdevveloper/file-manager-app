package org.jkdev.rest;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.jkdev.crud.routes.file.DeleteFileRoute;
import org.jkdev.crud.routes.file.GetFileRoute;
import org.jkdev.crud.routes.file.SaveFileRoute;
import org.jkdev.crud.routes.properties.DeleteFilePropertiesRoute;
import org.jkdev.entity.MiddlewareFileContent;
import org.jkdev.mapper.FileContentEnricher;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.MediaType;

@ApplicationScoped
public class FileRestRoute extends RouteBuilder {

    private static final String BASE_PATH = "/files";
    private static final String DELETE_FILE = "/deletePDF";
    private static final String SEND_FILE = "/sendPDF";
    private static final String GET_FILE = "/getPDF";

    private static final String DELETE_FILE_ROUTE_ID = "deletePDFRESTRoute";
    private static final String SEND_FILE_ROUTE_ID = "sendPDFRESTRoute";
    private static final String GET_FILE_ROUTE_ID = "getPDFRESTRoute";

    @Inject
    FileContentEnricher fileContentEnricher;

    @Override
    public void configure() {

        rest(BASE_PATH).delete(DELETE_FILE)
                .param().name("fileIdentifier").required(true).endParam()
                .param().name("fileOwner").required(true).endParam()
                .produces(MediaType.APPLICATION_JSON)
                .id(DELETE_FILE_ROUTE_ID)
                .route()
                .log("Requesting to delete file with data: ${headers}")
                    .doTry()
                        .to(DeleteFilePropertiesRoute.DELETE_FILE_PROPERTIES_ROUTE)
                        .to(DeleteFileRoute.DELETE_FILE)
                    .endDoTry()
                    .doCatch(Exception.class)
                        .log("Error deleting file, fileIdentifier: ${header.fileIdentifier}, fileOwner: ${header.fileOwner}")
                        .setHeader(Exchange.HTTP_RESPONSE_CODE, constant("500"))
                    .end()
        .endRest();

        rest(BASE_PATH).post(SEND_FILE)
                .consumes(MediaType.APPLICATION_JSON)
                .id(SEND_FILE_ROUTE_ID)
                .route()
                    .unmarshal().json(JsonLibrary.Jackson, MiddlewareFileContent.class)
                    .doTry()
                        .filter(simple("${body.content} != null"))
                        .to(SaveFileRoute.SAVE_FILE)
                    .endDoTry()
                    .doCatch(Exception.class)
                        .setBody().constant("Error saving pdf")
                        .setHeader(Exchange.HTTP_RESPONSE_CODE, constant("500"))
                    .end()
        .endRest();

        rest(BASE_PATH).get(GET_FILE)
                .param().name("fileName").endParam()
                .param().name("owner").endParam()
                .produces(MediaType.APPLICATION_JSON)
                .id(GET_FILE_ROUTE_ID)
                .route()
                .doTry()
                    .to(GetFileRoute.GET_FILE)
                    .process(fileContentEnricher)
                    .marshal().json(JsonLibrary.Jackson, MiddlewareFileContent.class)
                .endDoTry()
                .doCatch(Exception.class)
                    .log("Error occurred during file fetching, fileName: ${header.fileName}, fileOwner: ${header.owner}")
                    .setHeader(Exchange.HTTP_RESPONSE_CODE, constant("500"))
                .end()
        .endRest();
    }
}
