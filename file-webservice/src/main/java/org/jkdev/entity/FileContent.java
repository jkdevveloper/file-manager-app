package org.jkdev.entity;

public class FileContent {

    private String fileName;
    private String content;
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

    @Override
    public String toString() {
        return "FileContent{" +
                "fileName='" + fileName + '\'' +
                "fileOwner='" + owner + '\'' +
                '}';
    }
}

