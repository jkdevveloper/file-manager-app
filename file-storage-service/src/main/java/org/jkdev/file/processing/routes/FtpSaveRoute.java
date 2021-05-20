package org.jkdev.file.processing.routes;

import org.apache.camel.ProducerTemplate;
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

    @Inject
    ProducerTemplate producerTemplate;

    @Override
    public void configure() {
        from(SAVE_FILE_TO_FTP)
                .id("saveFileToFtpServerRoute")
                .choice()
                    .when(simple("${header.saveAs} == 'image'"))
                        .process(imageConverter)
                    .otherwise()
                        .process(pdfConverter)
                .end()
                // TODO error handling
                .log("${headers}")
                .process(exchange -> {
                    String fileOwner = exchange.getIn().getHeader("fileOwner", String.class);
                    String fileIdentifier = exchange.getIn().getHeader("fileIdentifier", String.class);
                    producerTemplate.send("ftp://localhost:21/?passiveMode=true&stepWise=true&synchronous=true&binary=true&disconnect=true&fileName=" + fileOwner + "/" + fileIdentifier, exchange);
                    producerTemplate.cleanUp();
                })
                //.to("ftp://test@localhost:21/?password=test&passiveMode=true&binary=true&disconnect=true&fileName=${header.fileOwner}/${header.fileIdentifier}")
                .log("Saved file to ftp server");

    }
}
