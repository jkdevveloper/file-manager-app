package org.jkdev.file.processing.processor;

import org.apache.camel.ConsumerTemplate;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.jkdev.file.storage.api.FileStorageDTO;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@ApplicationScoped
public class FtpFileReader implements Processor {

    @Inject
    ConsumerTemplate ftpFileConsumer;

    @Override
    public void process(Exchange exchange) {

        String fileIdentifier = exchange.getIn().getHeader("fileIdentifier", String.class);
        String fileOwner = exchange.getIn().getHeader("fileOwner", String.class);

        String fileContent = ftpFileConsumer.receive("ftp://one@localhost:21/" + fileOwner + "?password=1234&passiveMode=true&synchronous=true&disconnect=true&fileName=" + fileIdentifier).getIn().getBody(String.class);

        Base64.Encoder base64Encoder = Base64.getEncoder();

        FileStorageDTO fileStorageDTO = new FileStorageDTO().fileOwner(fileOwner).fileContent(base64Encoder.encodeToString(fileContent.getBytes(StandardCharsets.UTF_8))).fileIdentifier(fileIdentifier);

        exchange.getIn().setBody(fileStorageDTO);
    }
}
