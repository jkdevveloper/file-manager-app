package org.jkdev.file.processing.routes;

import org.apache.camel.builder.RouteBuilder;
import org.jkdev.file.processing.conversion.ImageConverter;
import org.jkdev.file.processing.conversion.PDFConverter;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class FtpSaveRoute extends RouteBuilder {

    public static final String SAVE_FILE_TO_FTP = "direct:saveFileToFtpServer";

    @Inject
    ImageConverter imageConverter;

    @Inject
    PDFConverter pdfConverter;

    @Override
    public void configure() throws Exception {

        from(SAVE_FILE_TO_FTP)
                .id("saveFileToFtpServerRoute")
                .choice()
                    .when(simple("${header.saveAs} == 'image'"))
                        .process(imageConverter)
                    .otherwise()
                        .process(pdfConverter)
                .endChoice()
                // TODO error handling
                .to("ftp://test@localhost:21/?password=test&passiveMode=true&binary=true&fileName=${header.fileOwner}/${header.fileIdentifier}")
                .log("Saved file to ftp server");

    }
}
