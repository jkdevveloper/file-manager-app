package org.jkdev.dao;

import org.jkdev.entity.FilePropertiesEntity;

import java.util.List;

public interface FilePropertiesDAO {

    void saveFileProperties(FilePropertiesEntity filePropertiesEntity);

    void deleteFileProperties(String fileIdentifier, String fileOwner);

    List<FilePropertiesEntity> getFilePropertiesById(String id);

    List<FilePropertiesEntity> getFilePropertiesByFileName(String fileName);

    List<FilePropertiesEntity> getFilePropertiesByFilters(String fileName, String fileOwner, String dateUploaded, String fileIdentifier);

    List<FilePropertiesEntity> getFileProperties();

    List<FilePropertiesEntity> getFilePropertiesByOwner(String fileOwner);

    void updateFileProperties(FilePropertiesEntity filePropertiesEntity);

}
