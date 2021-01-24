package org.jkdev;

import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("postLogout")
public class PostLogoutResource {

    @Inject
    Template postLogout;

    @GET
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance get() {
        return postLogout.instance();
    }

}
