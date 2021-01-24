package org.jkdev;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import io.quarkus.qute.TemplateInstance;
import io.quarkus.qute.Template;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Path("panel")
public class HelloResource {

    @Inject
    Template panel;

    Logger logger = LoggerFactory.getLogger(HelloResource.class);

    @GET
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance get(@QueryParam("name") String name) {

        return panel.data("name", name);
    }
}