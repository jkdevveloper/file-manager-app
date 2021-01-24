package org.jkdev.rest;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.jkdev.crud.routes.properties.GetFilePropertiesRoute;
import org.jkdev.file.properties.api.FilePropertiesDTO;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class FilePropertiesRestRoute extends RouteBuilder {

    private static final String GET_FILE_PROPERTIES_REST_ROUTE = "/getFileProperties";
    private static final String BASE_PATH = "fileproperties";
    private static final String GET_FILE_PROPERTIES_REST_ROUTE_ID = "getPDFPropertiesRESTRoute";

    @Override
    public void configure() throws Exception {

        rest(BASE_PATH).get(GET_FILE_PROPERTIES_REST_ROUTE)
                .id(GET_FILE_PROPERTIES_REST_ROUTE_ID)
                .param().name("owner").endParam()
                .route()
                .to(GetFilePropertiesRoute.GET_FILE_PROPERTIES)
                .log("get pdf properties, fileName: ${header.fileName}, owner: ${header.owner}");
    }
}
