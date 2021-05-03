package org.jkdev.file.processing.processor;

import io.netty.handler.codec.base64.Base64Decoder;
import org.apache.camel.*;
import org.jkdev.file.storage.api.FileStorageDTO;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@ApplicationScoped
public class FtpFileReader implements Processor {

    @Inject
    private ConsumerTemplate ftpFileConsumer;

    @Override
    public void process(Exchange exchange) throws IOException {
        String fileIdentifier = exchange.getIn().getHeader("fileIdentifier", String.class);
        String fileOwner = exchange.getIn().getHeader("fileOwner", String.class);

        String fileContent = ftpFileConsumer.receive("ftp://test@localhost:21/" + fileOwner + "?password=test&passiveMode=true&stepwise=true&binary=true&disconnect=true&fileName=" + fileIdentifier).getIn().getBody(String.class);

        /*ftpFileConsumer.cleanUp();
        ftpFileConsumer.close();*/

        Base64.Encoder base64Encoder = Base64.getMimeEncoder();

        FileStorageDTO fileStorageDTO = new FileStorageDTO().fileContent(base64Encoder.encodeToString(fileContent.getBytes(StandardCharsets.UTF_8))).fileIdentifier(fileIdentifier);

        exchange.getIn().setBody(fileStorageDTO);
    }
}
