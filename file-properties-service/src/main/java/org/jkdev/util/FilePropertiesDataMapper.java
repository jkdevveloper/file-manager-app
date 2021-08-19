package org.jkdev.util;

import org.jkdev.file.properties.api.FilePropertiesDTO;
import org.jkdev.entity.FilePropertiesEntity;

import javax.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class FilePropertiesDataMapper {

    public FilePropertiesEntity mapDTOtoEntity(FilePropertiesDTO filePropertiesDTO) {
        FilePropertiesEntity filePropertiesEntity = new FilePropertiesEntity();

        filePropertiesEntity.setFileOwner(filePropertiesDTO.getFileOwner());
        filePropertiesEntity.setFileIdentifier(filePropertiesDTO.getFileIdentifier());
        filePropertiesEntity.setFileName(filePropertiesDTO.getFileName());
        filePropertiesEntity.setDateUploaded(filePropertiesDTO.getDateUploaded());

        return filePropertiesEntity;
    }

    public FilePropertiesDTO mapEntityToDTO(FilePropertiesEntity filePropertiesEntity) {
        FilePropertiesDTO filePropertiesDTO = new FilePropertiesDTO();

        filePropertiesDTO.setId(filePropertiesEntity.getId());
        filePropertiesDTO.setFileOwner(filePropertiesEntity.getFileOwner());
        filePropertiesDTO.setFileIdentifier(filePropertiesEntity.getFileIdentifier());
        filePropertiesDTO.setFileName(filePropertiesEntity.getFileName());
        filePropertiesDTO.setDateUploaded(filePropertiesEntity.getDateUploaded());

        return filePropertiesDTO;
    }

    public List<FilePropertiesDTO> mapFilePropertiesToDTOS(List<FilePropertiesEntity> filePropertiesEntityList) {
        List<FilePropertiesDTO> filePropertiesDTOS = new ArrayList<>();
        for (FilePropertiesEntity filePropertiesEntity : filePropertiesEntityList) {
            filePropertiesDTOS.add(mapEntityToDTO(filePropertiesEntity));
        }
        return filePropertiesDTOS;
    }

}
