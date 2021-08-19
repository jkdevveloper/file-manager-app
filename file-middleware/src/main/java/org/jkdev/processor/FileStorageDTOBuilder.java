package org.jkdev.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.jkdev.entity.MiddlewareFileContent;
import org.jkdev.file.storage.api.FileStorageDTO;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class FileStorageDTOBuilder implements Processor {

    @Override
    public void process(Exchange exchange) {
        MiddlewareFileContent middlewareFileContent = exchange.getIn().getBody(MiddlewareFileContent.class);

        FileStorageDTO fileStorageDTO = new FileStorageDTO();

        fileStorageDTO.setFileContent(middlewareFileContent.getContent());
        fileStorageDTO.setFileIdentifier(exchange.getIn().getHeader("fileIdentifier", String.class));
        fileStorageDTO.setFileOwner(middlewareFileContent.getOwner());

        exchange.getIn().setBody(fileStorageDTO);
    }
}
