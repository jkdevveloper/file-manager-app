package org.jkdev.mapper;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.jkdev.entity.ContentType;
import org.jkdev.entity.PDFContent;
import org.jkdev.file.storage.api.FileStorageDTO;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class EntityMapper implements Processor {

    @Override
    public void process(Exchange exchange){

        FileStorageDTO fileStorageDTO = exchange.getIn().getBody(FileStorageDTO.class);
        String fileName = exchange.getIn().getHeader("fileName", String.class);

        PDFContent pdfContent = new PDFContent();
        pdfContent.setContent(fileStorageDTO.getFileContent());
        pdfContent.setOwner(fileStorageDTO.getFileOwner());
        pdfContent.setFileName(fileName);
        pdfContent.setContentType(ContentType.PDF);

        exchange.getIn().setBody(pdfContent);
    }
}
