package io.github.project.openubl.xml.xmlbuilder.client;

import org.jboss.resteasy.annotations.providers.multipart.PartType;

import javax.ws.rs.FormParam;
import javax.ws.rs.core.MediaType;
import java.io.InputStream;

public class FileUploadForm {

    @FormParam("file")
    @PartType(MediaType.APPLICATION_OCTET_STREAM)
    public InputStream file;

    @FormParam("username")
    @PartType(MediaType.TEXT_PLAIN)
    public String username;

    @FormParam("password")
    @PartType(MediaType.TEXT_PLAIN)
    public String password;

    @FormParam("customId")
    @PartType(MediaType.TEXT_PLAIN)
    public String customId;
}
