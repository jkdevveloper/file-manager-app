package org.jkdev.service;

import org.jkdev.entity.FileProperties;
import org.jkdev.file.properties.api.FilePropertiesDTO;

import java.util.List;

public interface FilePropertiesService {

    void saveFileProperties(FilePropertiesDTO filePropertiesDTO);

    List<FilePropertiesDTO> getFilePropertiesList();

    List<FilePropertiesDTO> getFilePropertiesByOwner(String fileOwner);

    void deleteFileProperties(String fileIdentifier, String fileOwner);

    List<FilePropertiesDTO> getFileProperties(String fileName, String fileOwner, String dateUploaded, String fileIdentifier);

    void updateFileProperties(FilePropertiesDTO filePropertiesDTO);

}
