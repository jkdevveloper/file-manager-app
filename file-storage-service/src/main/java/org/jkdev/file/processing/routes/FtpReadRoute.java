package org.jkdev.file.processing.routes;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.jkdev.file.processing.processor.FtpFileReader;
import org.jkdev.file.storage.api.FileStorageDTO;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class FtpReadRoute extends RouteBuilder {

    public static final String GET_FILE_FROM_FTP = "direct:getFileFromFtpServer";

    @Inject
    private FtpFileReader ftpFileReader;

    @Override
    public void configure() throws Exception {

        from(GET_FILE_FROM_FTP)
                .id("getFileFromFtpServerRoute")
                .pollEnrich("ftp://test@localhost:21/?password=test&passiveMode=true&fileName=${header.fileOwner}/${header.fileIdentifier}")
                .log("${body}")
                .marshal().json(JsonLibrary.Jackson, FileStorageDTO.class)
                .log("Getting file from ftp server");
    }
}
