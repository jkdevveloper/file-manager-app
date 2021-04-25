package org.jkdev.entity;

public class PDFContent {

    private String fileName;
    private String content;
    private String contentType;
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

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
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

