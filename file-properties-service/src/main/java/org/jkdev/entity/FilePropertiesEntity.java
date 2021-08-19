package org.jkdev.entity;

import javax.persistence.*;

@Entity
@Table(name = "FileProperties")
public class FilePropertiesEntity {

    private Long id;
    private String fileName;
    private String fileOwner;
    private String dateUploaded;
    private String fileIdentifier;

    public FilePropertiesEntity() {
    }

    public FilePropertiesEntity(String fileName, String fileOwner, String dateUploaded, String fileIdentifier) {
        this.fileName = fileName;
        this.fileOwner = fileOwner;
        this.dateUploaded = dateUploaded;
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

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileOwner() {
        return fileOwner;
    }

    public void setFileOwner(String fileOwner) {
        this.fileOwner = fileOwner;
    }

    public String getDateUploaded() {
        return dateUploaded;
    }

    public void setDateUploaded(String dateUploaded) {
        this.dateUploaded = dateUploaded;
    }

    @Column(unique = true)
    public String getFileIdentifier() {
        return fileIdentifier;
    }

    public void setFileIdentifier(String fileIdentifier) {
        this.fileIdentifier = fileIdentifier;
    }

    @Override
    public String toString() {
        return "FileProperties{" +
                "id=" + id +
                ", fileName='" + fileName + '\'' +
                ", fileOwner='" + fileOwner + '\'' +
                ", dateUploaded='" + dateUploaded + '\'' +
                ", fileIdentifier='" + fileIdentifier + '\'' +
                '}';
    }
}
