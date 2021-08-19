package org.jkdev.service;

import org.jkdev.dao.FileStorageDAO;
import org.jkdev.entity.FileStorageEntity;
import org.jkdev.file.storage.api.FileStorageDTO;
import org.jkdev.mapper.FileStorageDataMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
class FileStorageServiceImpl implements FileStorageService {

    @Inject
    FileStorageDAO fileStorageDAO;

    @Inject
    FileStorageDataMapper fileStorageDataMapper;

    Logger logger = LoggerFactory.getLogger(FileStorageServiceImpl.class);

    @Override
    public void saveFile(FileStorageDTO fileStorageDTO) {
        FileStorageEntity fileStorageEntity = fileStorageDataMapper.mapDTOtoEntity(fileStorageDTO);

        fileStorageDAO.saveFile(fileStorageEntity);

        logger.info("SAVED ENTITY SUCCESFULLY");
    }

    @Override
    public FileStorageDTO getFile(String fileIdentifier, String fileOwner) {
        FileStorageEntity fileStorageEntity = fileStorageDAO.getFile(fileIdentifier, fileOwner);

        return fileStorageDataMapper.mapEntityToDTO(fileStorageEntity);
    }

    @Override
    public void deleteFile(String fileIdentifier, String fileOwner) {
        fileStorageDAO.deleteFile(fileIdentifier, fileOwner);
    }
}
