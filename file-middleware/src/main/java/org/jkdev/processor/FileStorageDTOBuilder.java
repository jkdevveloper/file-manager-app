package org.jkdev.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.commons.io.FileUtils;
import org.jkdev.entity.PDFContent;
import org.jkdev.file.storage.api.FileStorageDTO;

import javax.enterprise.context.ApplicationScoped;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@ApplicationScoped
public class FileStorageDTOBuilder implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {
        PDFContent pdfContent = exchange.getIn().getBody(PDFContent.class);

        FileStorageDTO fileStorageDTO = new FileStorageDTO();

        fileStorageDTO.setFileContent(pdfContent.getContent());
        fileStorageDTO.setFileIdentifier(exchange.getIn().getHeader("fileIdentifier", String.class));
        fileStorageDTO.setFileOwner(pdfContent.getOwner());

        exchange.getIn().setBody(fileStorageDTO);
    }
}
