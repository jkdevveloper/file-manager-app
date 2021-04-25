package org.jkdev.file.processing.converting;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.tools.imageio.ImageIOUtil;
import org.jkdev.file.storage.api.FileStorageDTO;

import javax.enterprise.context.ApplicationScoped;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Base64;

@ApplicationScoped
public class ImageConverter implements Processor {

    // convert pdf to series of jpeg

    @Override
    public void process(Exchange exchange) throws Exception {

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
}
