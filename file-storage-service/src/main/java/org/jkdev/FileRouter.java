package org.jkdev;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.jkdev.file.processing.routes.FtpDeleteRoute;
import org.jkdev.file.processing.routes.FtpReadRoute;
import org.jkdev.file.processing.routes.FtpSaveRoute;
import org.jkdev.file.storage.api.FileStorageDTO;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class FileRouter extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        from("kafka:test?brokers=localhost:9091&groupId=hello")
                .id("ftpRoute")
                .log("Processing operation of: ${header.operation}")
                .log("${body}")
                .choice()
                    .when(header("operation").isEqualTo("save"))
                        .unmarshal().json(JsonLibrary.Jackson, FileStorageDTO.class)
                        .process(e -> {
                            e.getIn().setHeader("fileIdentifier", e.getIn().getBody(FileStorageDTO.class).getFileIdentifier());
                            e.getIn().setHeader("fileOwner", e.getIn().getBody(FileStorageDTO.class).getFileOwner());
                        })
                        .log("save")
                        .to(FtpSaveRoute.SAVE_FILE_TO_FTP)
                    .endChoice()
                    .when(header("operation").isEqualTo("delete"))
                        .to(FtpDeleteRoute.DELETE_FILE_FROM_FTP)
                    .endChoice()
                    .otherwise()
                        .throwException(new Exception("Ftp operation not supported!"));


        rest().get("/getPDF?")
                .route()
                .id("restPDFReadRoute")
                .to(FtpReadRoute.GET_FILE_FROM_FTP)
                .marshal().json(JsonLibrary.Jackson, FileStorageDTO.class)
                .end();

    }
}
