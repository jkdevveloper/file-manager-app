package org.jkdev.crud.routes.file;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class DeleteFileRoute extends RouteBuilder {

    public static final String DELETE_PDF = "direct:deleteFile";

    @Override
    public void configure() throws Exception {

        from(DELETE_PDF)
                .id("deleteFileRoute")
                .removeHeaders("CamelHttp*")
                .setHeader(Exchange.HTTP_METHOD, simple("DELETE"))
                .to("http://file.storage.service:8081/storage-service/delete-file?fileIdentifier=${header.fileIdentifier}&fileOwner=${header.fileOwner}");
    }
}
