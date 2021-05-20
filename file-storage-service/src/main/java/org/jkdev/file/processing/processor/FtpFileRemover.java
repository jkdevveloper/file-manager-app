package org.jkdev.file.processing.processor;

import org.apache.camel.ConsumerTemplate;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class FtpFileRemover implements Processor {

//    @Inject
//    private ConsumerTemplate consumerTemplate;

    @Override
    public void process(Exchange exchange) {
        ConsumerTemplate consumerTemplate= exchange.getContext().createConsumerTemplate();
        String fileIdentifier = exchange.getIn().getHeader("fileIdentifier", String.class);
        String fileOwner = exchange.getIn().getHeader("fileOwner", String.class);

        consumerTemplate
                .receive("ftp://test@127.0.0.1:21/?password=test&useList=false&synchronous=true&passiveMode=true&stepwise=true&fileName=" + fileOwner + "/" + fileIdentifier + "&delete=true");
    }
}
