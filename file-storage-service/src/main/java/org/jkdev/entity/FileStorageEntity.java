package org.jkdev.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name = "FileStorage")
public class FileStorageEntity {

    private Long id;
    private String fileContent;
    private String fileOwner;
    private String fileIdentifier;

    public FileStorageEntity(){}

    public FileStorageEntity(String fileContent, String fileOwner, String fileIdentifier) {
        this.fileContent = fileContent;
        this.fileOwner = fileOwner;
        this.fileIdentifier = fileIdentifier;
    }

    @Id
    @GeneratedValue
    public Long getId(){
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setFileContent(String fileContent) {
        this.fileContent = fileContent;
    }

    @Lob
    public String getFileContent() {
        return fileContent;
    }

    public String getFileOwner() {
        return fileOwner;
    }

    public void setFileOwner(String fileOwner) {
        this.fileOwner = fileOwner;
    }

    public String getFileIdentifier() {
        return fileIdentifier;
    }

    public void setFileIdentifier(String fileIdentifier) {
        this.fileIdentifier = fileIdentifier;
    }
}
