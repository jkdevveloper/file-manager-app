package org.jkdev.file.processing.routes;

import org.apache.camel.builder.RouteBuilder;
import org.jkdev.file.processing.processor.FtpFileRemover;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class FtpDeleteRoute extends RouteBuilder {

    public static final String DELETE_FILE_FROM_FTP = "direct:deleteFileFromFtpServer";

    @Inject
    private FtpFileRemover ftpFileRemover;

    @Override
    public void configure() throws Exception {
        from(DELETE_FILE_FROM_FTP)
                .id("deleteFileFromFtpServerRoute")
                // error handling
                .log("Deleting file: ${header.fileIdentifier}")
                //.pollEnrich("ftp://test@localhost:21/?password=test&passiveMode=true&fileName=${header.fileOwner}/${header.fileIdentifier}&delete=true")

                .log("Deleted file ${header.fileIdentifier}");
    }
}
