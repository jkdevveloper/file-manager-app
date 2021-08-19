package org.jkdev.mapper;

import org.jkdev.entity.FileStorageEntity;
import org.jkdev.file.storage.api.FileStorageDTO;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class FileStorageDataMapper {

    public FileStorageEntity mapDTOtoEntity(FileStorageDTO fileStorageDTO) {
        FileStorageEntity fileStorageEntity = new FileStorageEntity();

        fileStorageEntity.setFileOwner(fileStorageDTO.getFileOwner());
        fileStorageEntity.setFileContent(fileStorageDTO.getFileContent());
        fileStorageEntity.setFileIdentifier(fileStorageDTO.getFileIdentifier());

        return fileStorageEntity;
    }

    public FileStorageDTO mapEntityToDTO(FileStorageEntity fileStorageEntity) {
        FileStorageDTO fileStorageDTO = new FileStorageDTO();

        fileStorageDTO.setFileOwner(fileStorageEntity.getFileOwner());
        fileStorageDTO.setFileContent(fileStorageEntity.getFileContent());
        fileStorageDTO.setFileIdentifier(fileStorageEntity.getFileIdentifier());

        return fileStorageDTO;
    }

}
