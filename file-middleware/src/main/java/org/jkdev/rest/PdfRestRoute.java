package org.jkdev.rest;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.jkdev.crud.routes.file.DeleteFileRoute;
import org.jkdev.crud.routes.file.GetFileRoute;
import org.jkdev.crud.routes.file.SaveFileRoute;
import org.jkdev.crud.routes.properties.DeleteFilePropertiesRoute;
import org.jkdev.crud.routes.properties.GetFilePropertiesRoute;
import org.jkdev.crud.routes.properties.SaveFilePropertiesRoute;
import org.jkdev.entity.PDFContent;

import javax.ws.rs.core.MediaType;

public class PdfRestRoute extends RouteBuilder {

    private static final String BASE_PATH = "/files";
    private static final String DELETE_FILE = "/deletePDF";
    private static final String SEND_FILE = "/sendPDF";
    private static final String GET_FILE = "/getPDF";

    private static final String DELETE_FILE_ROUTE_ID = "deletePDFRESTRoute";
    private static final String SEND_FILE_ROUTE_ID = "sendPDFRESTRoute";
    private static final String GET_FILE_ROUTE_ID = "getPDFRESTRoute";

    @Override
    public void configure() {

        rest(BASE_PATH).delete(DELETE_FILE)
                .param().name("id").required(true).endParam()
                .param().name("fileIdentifier").required(true).endParam()
                .param().name("fileOwner").required(true).endParam()
                .produces(MediaType.APPLICATION_JSON)
                .id(DELETE_FILE_ROUTE_ID)
                .route()
                    .doTry()
                        .to(DeleteFilePropertiesRoute.DELETE_FILE_PROPERTIES_ROUTE)
                        .to(DeleteFileRoute.DELETE_PDF)
                    .endDoTry()
                    .doCatch(Exception.class)
                        .setBody().constant("Error deleting pdf")
                        .setHeader(Exchange.HTTP_RESPONSE_CODE, constant("500"))
                    .end()
        .endRest();

        rest(BASE_PATH).post(SEND_FILE)
                .consumes(MediaType.APPLICATION_JSON)
                .id(SEND_FILE_ROUTE_ID)
                .route()
                    .unmarshal().json(JsonLibrary.Jackson, PDFContent.class)
                    .doTry()
                        .to(SaveFileRoute.SAVE_PDF)
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
                    .to(GetFileRoute.GET_PDF)
                .endDoTry()
                .doCatch(Exception.class)
                    .setBody().constant("Error getting pdf")
                    .setHeader(Exchange.HTTP_RESPONSE_CODE, constant("500"))
                .end()
        .endRest();
    }
}
