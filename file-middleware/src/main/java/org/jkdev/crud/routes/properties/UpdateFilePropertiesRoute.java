package org.jkdev.crud.routes.properties;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.http.base.HttpOperationFailedException;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.jkdev.file.properties.api.FilePropertiesDTO;

public class UpdateFilePropertiesRoute extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("direct:updateFileProperties")
                .id("updateFilePropertiesRoute")
                .doTry()
                    .marshal().json(JsonLibrary.Jackson, FilePropertiesDTO.class)
                    .to("http://localhost:8082/file-properties-service/update-file-properties")
                .endDoTry()
                .doCatch(HttpOperationFailedException.class)
                    .log("Updating file properties failed")
                .end();
    }
}
