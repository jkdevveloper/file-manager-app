package org.jkdev.file.processing.routes;

import org.apache.camel.builder.RouteBuilder;
import org.jkdev.file.processing.processor.FtpFileReader;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class FtpReadRoute extends RouteBuilder {

    public static final String GET_FILE_FROM_FTP = "direct:getFileFromFtpServer";

    @Inject
    FtpFileReader ftpFileReader;

    @Override
    public void configure() {

        from(GET_FILE_FROM_FTP)
                .id("getFileFromFtpServerRoute")
                .log("${headers}")
               // .pollEnrich("ftp://test@127.0.0.1:21/?password=test&passiveMode=true&stepwise=true&disconnect=true&binary=true&fileName=${header.fileOwner}/${header.fileIdentifier}")
                .process(ftpFileReader)
                .log("${body}")
                .log("Got file from ftp server");
    }
}
