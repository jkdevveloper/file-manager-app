package org.jkdev.crud.routes.properties;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.ListJacksonDataFormat;
import org.apache.camel.http.base.HttpOperationFailedException;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.jkdev.file.properties.api.FilePropertiesDTO;

import java.util.List;

public class GetFilePropertiesRoute extends RouteBuilder {

    public static final String GET_FILE_PROPERTIES = "direct:getFileProperties";

    @Override
    public void configure() throws Exception {
        from(GET_FILE_PROPERTIES)
                .id("getFilePropertiesRoute")
                .removeHeaders("CamelHttp*")
                .doTry()
                    .toD("http://localhost:8082/properties-service/get-file-properties?fileName=${header.fileName}&fileOwner=${header.owner}")
                .endDoTry()
                .doCatch(HttpOperationFailedException.class)
                    .log("getting file properties failed")
                .end();
    }
}
