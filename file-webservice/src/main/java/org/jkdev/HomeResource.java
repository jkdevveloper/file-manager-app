package org.jkdev;

import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("")
@ApplicationScoped
public class HomeResource {

    @Inject
    Template index;

    @GET
    @Path("index")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance getHomeSite(){
        return index.instance();
    }
}
