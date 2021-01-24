package org.jkdev.file.processing.processor;

import org.apache.camel.ConsumerTemplate;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class FtpFileRemover implements Processor {

    @Inject
    private ConsumerTemplate consumerTemplate;

    @Override
    public void process(Exchange exchange) throws Exception {
        String fileIdentifier = exchange.getIn().getHeader("fileIdentifier", String.class);
        //String fileOwner = exchange.getIn().getHeader("fileOwner", String.class);

        Exchange exchange1 = consumerTemplate
                .receive("ftp://test@localhost:21/home/test?password=test&useList=false&passiveMode=true&stepwise=true&fileName=" + fileIdentifier + "&delete=true");
        System.out.println(exchange1);
    }
}
