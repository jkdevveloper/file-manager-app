package org.jkdev.mapper;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.jkdev.entity.MiddlewareFileContent;
import org.jkdev.file.storage.api.FileStorageDTO;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class FileContentEnricher implements Processor {

    @Override
    public void process(Exchange exchange){

        final FileStorageDTO fileStorageDTO = exchange.getIn().getBody(FileStorageDTO.class);
        final String fileName = exchange.getIn().getHeader("fileName", String.class);

        MiddlewareFileContent middlewareFileContent = new MiddlewareFileContent();
        middlewareFileContent.setContent(fileStorageDTO.getFileContent());
        middlewareFileContent.setOwner(fileStorageDTO.getFileOwner());
        middlewareFileContent.setFileName(fileName);

        exchange.getIn().setBody(middlewareFileContent);
    }
}
