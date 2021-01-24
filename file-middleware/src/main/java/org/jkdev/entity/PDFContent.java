package org.jkdev.entity;

public class PDFContent {

    private String fileName;
    private String content;
    private ContentType contentType;
    private String owner;

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ContentType getContentType() {
        return contentType;
    }

    public void setContentType(ContentType contentType) {
        this.contentType = contentType;
    }

    @Override
    public String toString() {
        return "PDFContent{" +
                "fileName='" + fileName + '\'' +
                ", content='" + content + '\'' +
                ", contentType=" + contentType +
                '}';
    }
}
