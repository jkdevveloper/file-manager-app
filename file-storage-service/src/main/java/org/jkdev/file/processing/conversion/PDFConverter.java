package org.jkdev.file.processing.conversion;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.jkdev.file.storage.api.FileStorageDTO;

import javax.enterprise.context.ApplicationScoped;
import java.io.ByteArrayOutputStream;
import java.util.Base64;

@ApplicationScoped
public class PDFConverter implements Processor {


    @Override
    public void process(Exchange exchange) throws Exception {
        FileStorageDTO fileStorageDTO = exchange.getIn().getBody(FileStorageDTO.class);

        String base64String = fileStorageDTO.getFileContent();

        PDDocument pdfDoc = PDDocument.load(Base64.getMimeDecoder().decode(base64String));

        ByteArrayOutputStream documentByteArrayOutputStream = new ByteArrayOutputStream();

        pdfDoc.save(documentByteArrayOutputStream);
        pdfDoc.close();

        exchange.getIn().setBody(documentByteArrayOutputStream);
    }
}
