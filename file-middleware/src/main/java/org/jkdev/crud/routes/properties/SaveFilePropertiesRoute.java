package org.jkdev.crud.routes.properties;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.http.conn.HttpHostConnectException;
import org.jkdev.file.properties.api.FilePropertiesDTO;
import org.jkdev.processor.FilePropertiesDTOBuilder;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class SaveFilePropertiesRoute extends RouteBuilder {

    public static final String SAVE_PDF_PROPERTIES = "direct:saveFileProperties";

    @Inject
    private FilePropertiesDTOBuilder filePropertiesDTOBuilder;

    @Override
    public void configure() throws Exception {
        from("direct:saveFileProperties")
                .id("saveFilePropertiesRoute")
                .marshal().json(JsonLibrary.Jackson, FilePropertiesDTO.class)
                .removeHeaders("CamelHttp*")
                .doTry()
                    .to("http://localhost:8082/properties-service/save-file-properties")
                .endDoTry()
                .doCatch(HttpHostConnectException.class)
                    .log("Failed to POST data to file-properties-service, ${body}")
                    .log("${headers}")
                .end()
                .choice()
                    .when(simple("${header.CamelHttpResponseCode} == 200"))
                        .log("File properties saved successfully, ${header.fileName}")
                    .endChoice()
                .end();
    }
}
