package org.jkdev.crud.routes.file;

import org.apache.camel.builder.RouteBuilder;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class DeleteFileRoute extends RouteBuilder {

    public static final String DELETE_PDF = "direct:deleteFile";

    @Override
    public void configure() throws Exception {

        from(DELETE_PDF)
                .id("deleteFileRoute")
                .setHeader("operation", simple("delete"))
                .removeHeaders("CamelHttp*")
                .to("http://localhost:8081/storage-service/modify-file");
    }
}
