package org.jkdev.file.processing.routes;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.tools.imageio.ImageIOUtil;
import org.jkdev.file.storage.api.FileStorageDTO;

import javax.enterprise.context.ApplicationScoped;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Base64;

@ApplicationScoped
public class FtpSaveRoute extends RouteBuilder {

    public static final String SAVE_FILE_TO_FTP = "direct:saveFileToFtpServer";

    @Override
    public void configure() throws Exception {

       // from("timer://foo?fixedRate=true&period=9000")
       //         .to(SAVE_FILE_TO_FTP);

        from(SAVE_FILE_TO_FTP)
                .id("saveFileToFtpServerRoute")
                .setHeader("fileIdentifier", simple("shit.jpeg"))
                .setHeader("fileOwner", simple("mojstary"))
                .process(this::makeImage)
                // error handling
                .to("ftp://test@localhost:21/?password=test&passiveMode=true&binary=true&fileName=${header.fileOwner}/${header.fileIdentifier}")
                .log("Saving file to ftp server");

    }

    private void makeImage(Exchange exchange) throws IOException {
        FileStorageDTO fileStorageDTO = exchange.getIn().getBody(FileStorageDTO.class);

        String base64String = fileStorageDTO.getFileContent();

        PDDocument document = PDDocument.load(Base64.getMimeDecoder().decode(base64String));
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        PDFRenderer pdfRenderer = new PDFRenderer(document);
        for (int page = 0; page < document.getNumberOfPages(); ++page)
        {
            BufferedImage bim = pdfRenderer.renderImageWithDPI(page, 300);

            // suffix in filename will be used as the file format
            ImageIOUtil.writeImage(bim, "jpeg", byteArrayOutputStream,  300);
        }
        document.close();

        InputStream fis = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());

        exchange.getIn().setBody(fis);
    }

    private void makePDF(Exchange exchange) throws IOException {

        FileStorageDTO fileStorageDTO = exchange.getIn().getBody(FileStorageDTO.class);

        String base64String = fileStorageDTO.getFileContent();

        PDDocument docc = PDDocument.load(Base64.getMimeDecoder().decode(base64String));

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        docc.save(baos);

        docc.close();

        exchange.getIn().setBody(baos);
    }
}
