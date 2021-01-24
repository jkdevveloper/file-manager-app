package org.jkdev.util;

import org.jkdev.file.properties.api.FilePropertiesDTO;
import org.jkdev.entity.FileProperties;

import javax.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class EntityToDTOMapper {

    public FileProperties mapDTOtoEntity(FilePropertiesDTO filePropertiesDTO) {
        FileProperties fileProperties = new FileProperties();

        fileProperties.setFileOwner(filePropertiesDTO.getFileOwner());
        fileProperties.setFileIdentifier(filePropertiesDTO.getFileIdentifier());
        fileProperties.setFileName(filePropertiesDTO.getFileName());
        fileProperties.setDateUploaded(filePropertiesDTO.getDateUploaded());

        return fileProperties;
    }

    public FilePropertiesDTO mapEntityToDTO(FileProperties fileProperties) {
        FilePropertiesDTO filePropertiesDTO = new FilePropertiesDTO();

        filePropertiesDTO.setId(fileProperties.getId());
        filePropertiesDTO.setFileOwner(fileProperties.getFileOwner());
        filePropertiesDTO.setFileIdentifier(fileProperties.getFileIdentifier());
        filePropertiesDTO.setFileName(fileProperties.getFileName());
        filePropertiesDTO.setDateUploaded(fileProperties.getDateUploaded());

        return filePropertiesDTO;
    }

    public List<FilePropertiesDTO> mapFilePropertiesToDTOS(List<FileProperties> filePropertiesList) {
        List<FilePropertiesDTO> filePropertiesDTOS = new ArrayList<>();
        for (FileProperties fileProperties : filePropertiesList) {
            filePropertiesDTOS.add(mapEntityToDTO(fileProperties));
        }
        return filePropertiesDTOS;
    }

}
