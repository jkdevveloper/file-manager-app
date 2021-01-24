package org.jkdev.processor;


import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.jkdev.file.properties.api.FilePropertiesDTO;

import javax.enterprise.context.ApplicationScoped;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.UUID;

@ApplicationScoped
public class FilePropertiesDTOBuilder implements Processor {

    @Override
    public void process(Exchange exchange) {
        String fileIdentifier = UUID.randomUUID().toString();

        FilePropertiesDTO fileProperties = new FilePropertiesDTO();
        fileProperties.setFileName(exchange.getIn().getHeader("fileName", String.class));
        fileProperties.setFileOwner(exchange.getIn().getHeader("owner", String.class));
        fileProperties.setFileIdentifier(fileIdentifier);
        fileProperties.setDateUploaded(ZonedDateTime.now(ZoneOffset.UTC).toLocalDate().toString());

        exchange.getIn().setHeader("fileIdentifier", fileIdentifier);
        exchange.getIn().setBody(fileProperties);
    }
}
