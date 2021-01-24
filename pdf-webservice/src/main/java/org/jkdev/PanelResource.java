package org.jkdev;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import io.quarkus.oidc.UserInfo;
import io.quarkus.qute.TemplateInstance;
import io.quarkus.qute.Template;
import io.quarkus.security.identity.SecurityIdentity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Path("panel")
public class PanelResource {

    @Inject
    Template panel;

    Logger logger = LoggerFactory.getLogger(PanelResource.class);

    @Inject
    SecurityIdentity userInfo;

    @GET
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance get() {

        return panel.data("username", userInfo.getPrincipal().getName());
    }
}