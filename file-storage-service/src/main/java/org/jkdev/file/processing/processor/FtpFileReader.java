package org.jkdev.file.processing.processor;

import org.apache.camel.*;
import org.jkdev.file.storage.api.FileStorageDTO;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class FtpFileReader implements Processor {

    @Inject
    private ConsumerTemplate consumerTemplate;

    @Override
    public void process(Exchange exchange) throws Exception {
        String fileIdentifier = exchange.getIn().getHeader("fileIdentifier", String.class);

        String fileContent = consumerTemplate.receive("ftp://test@localhost:21/?passiveMode=true&stepwise=false&password=test&fileName=" + fileIdentifier).getIn().getBody(String.class);

        FileStorageDTO fileStorageDTO = new FileStorageDTO().fileContent(fileContent).fileIdentifier(fileIdentifier);

        exchange.getIn().setBody(fileStorageDTO);
    }
}
