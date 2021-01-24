package org.jkdev.crud.routes.properties;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.http.base.HttpOperationFailedException;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.jkdev.crud.routes.file.DeleteFileRoute;
import org.jkdev.file.properties.api.FilePropertiesDTO;

public class DeleteFilePropertiesRoute extends RouteBuilder {

    public static final String DELETE_FILE_PROPERTIES_ROUTE = "direct:deleteFileProperties";
    private static final String DELETE_FILE_PROPERTIES_ROUTE_ID = "deleteFilePropertiesRoute";

    @Override
    public void configure() throws Exception {
        from(DELETE_FILE_PROPERTIES_ROUTE)
                .id(DELETE_FILE_PROPERTIES_ROUTE_ID)
                .removeHeaders("CamelHttp*")
                .setHeader(Exchange.HTTP_METHOD, simple("DELETE"))
                .doTry()
                    .toD("http://localhost:8082/properties-service/delete-file-properties?id=${header.id}")
                    .to(DeleteFileRoute.DELETE_PDF)
                .endDoTry()
                .doCatch(HttpOperationFailedException.class)
                    .log("Failed to delete file properties with id: ${header.id}")
                .end()
                .end();
    }
}
