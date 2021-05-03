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
        String fileOwner = exchange.getIn().getHeader("fileOwner", String.class);

        Exchange exchange1 = consumerTemplate
                .receive("ftp://test@172.22.0.2:21/" + fileOwner + "/?password=test&useList=false&passiveMode=true&stepwise=true&fileName=" + fileOwner + "/" + fileIdentifier + "&delete=true");
        System.out.println(exchange1);
    }
}
