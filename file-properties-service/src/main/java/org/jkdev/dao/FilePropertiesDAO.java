package org.jkdev.dao;

import org.jkdev.entity.FileProperties;

import java.util.List;

public interface FilePropertiesDAO {

    void saveFileProperties(FileProperties fileProperties);

    void deleteFilePropertiesById(Long id);

    List<FileProperties> getFilePropertiesById(String id);

    List<FileProperties> getFilePropertiesByFileName(String fileName);

    List<FileProperties> getFilePropertiesByFilters(String fileName, String fileOwner, String dateUploaded, String fileIdentifier);

    List<FileProperties> getFileProperties();

    List<FileProperties> getFilePropertiesByOwner(String fileOwner);

    void updateFileProperties(FileProperties fileProperties);

}
