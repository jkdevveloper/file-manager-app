package org.jkdev.crud.routes.file;

import org.apache.camel.builder.RouteBuilder;
import org.jkdev.crud.routes.properties.GetFilePropertiesRoute;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class GetFileRoute extends RouteBuilder {

    public static final String GET_PDF = "direct:getFile";

    @Override
    public void configure() throws Exception {

        from(GET_PDF)
                .id("getFileRoute")
                .log("get PDF with id: ${header.id}, with name: ${header.fileName}")
                .to(GetFilePropertiesRoute.GET_FILE_PROPERTIES)
                // process that data and then get file
                .to(GetFileRoute.GET_PDF)
                .end();
    }
}
