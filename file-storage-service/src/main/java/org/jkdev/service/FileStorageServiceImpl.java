package org.jkdev.service;

import org.jkdev.dao.FileStorageDAO;
import org.jkdev.entity.FileStorageEntity;
import org.jkdev.file.storage.api.FileStorageDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
class FileStorageServiceImpl implements FileStorageService {

    @Inject
    FileStorageDAO fileStorageDAO;

    Logger logger = LoggerFactory.getLogger(FileStorageServiceImpl.class);

    @Override
    public void saveFile(FileStorageDTO fileStorageDTO) {
        FileStorageEntity fileStorageEntity = new FileStorageEntity();
        fileStorageEntity.setFileOwner(fileStorageDTO.getFileOwner());
        fileStorageEntity.setFileContent(fileStorageDTO.getFileContent());
        fileStorageEntity.setFileIdentifier(fileStorageDTO.getFileIdentifier());

        fileStorageDAO.saveFile(fileStorageEntity);

        logger.info("SAVED ENTITY SUCCESFULLY");
    }

    @Override
    public FileStorageDTO getFile(String fileIdentifier, String fileOwner) {
        FileStorageEntity fileStorageEntity = fileStorageDAO.getFile(fileIdentifier, fileOwner);

        FileStorageDTO fileStorageDTO = new FileStorageDTO().fileContent(fileStorageEntity.getFileContent())
                .fileIdentifier(fileStorageEntity.getFileIdentifier())
                .fileOwner(fileStorageEntity.getFileOwner());

        return fileStorageDTO;
    }

    @Override
    public void deleteFile(String fileIdentifier, String fileOwner) {
        fileStorageDAO.deleteFile(fileIdentifier, fileOwner);
    }
}
