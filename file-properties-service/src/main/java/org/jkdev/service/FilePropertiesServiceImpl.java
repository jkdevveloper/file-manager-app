package org.jkdev.service;

import org.jkdev.dao.FilePropertiesDAO;
import org.jkdev.entity.FilePropertiesEntity;
import org.jkdev.file.properties.api.FilePropertiesDTO;
import org.jkdev.util.FilePropertiesDataMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;

@ApplicationScoped
class FilePropertiesServiceImpl implements FilePropertiesService {

    @Inject
    FilePropertiesDAO filePropertiesDAO;

    @Inject
    FilePropertiesDataMapper filePropertiesDataMapper;

    private static final Logger logger = LoggerFactory.getLogger(FilePropertiesServiceImpl.class);

    // handles requests and uses DAO as bridge to make database queries

    @Override
    public void saveFileProperties(FilePropertiesDTO filePropertiesDTO) {
        try {
            filePropertiesDAO.saveFileProperties(filePropertiesDataMapper.mapDTOtoEntity(filePropertiesDTO));
        } catch (Exception e) {
            logger.error("Failed to save provided file properties entity: {}", filePropertiesDTO);
            //throw new WebApplicationException("Failed to save provided file properties entity", Response.Status.NOT_ACCEPTABLE);
        }
    }

    @Override
    public void deleteFileProperties(String fileIdentifier, String fileOwner) {
        filePropertiesDAO.deleteFileProperties(fileIdentifier, fileOwner);
    }

    @Override
    public List<FilePropertiesDTO> getFileProperties(String fileName, String fileOwner, String dateUploaded, String fileIdentifier) {
        logger.info("Received request to get properties with parameters: {}, {}, {}, {}", fileName, fileOwner, dateUploaded, fileIdentifier);
        List<FilePropertiesEntity> filePropertiesEntityList = filePropertiesDAO.getFilePropertiesByFilters(fileName, fileOwner, dateUploaded, fileIdentifier);

        if (filePropertiesEntityList.size() > 0) {
            logger.info(String.valueOf(filePropertiesEntityList));
            return filePropertiesDataMapper.mapFilePropertiesToDTOS(filePropertiesEntityList);

        } else {
            logger.error("Could not fetch file properties for provided filter parameters" +
                    " fileName: {}, fileOwner: {}, dateUploaded: {}, fileIdentifier: {}", fileName, fileOwner, dateUploaded, fileIdentifier);
            return null;
            //throw new WebApplicationException("Could not fetch file properties for provided parameters", Response.Status.NOT_FOUND);
        }
    }

    @Override
    public List<FilePropertiesDTO> getFilePropertiesList() {
        return filePropertiesDataMapper.mapFilePropertiesToDTOS(filePropertiesDAO.getFileProperties());
    }

    @Override
    public List<FilePropertiesDTO> getFilePropertiesByOwner(String fileOwner) {
        return filePropertiesDataMapper.mapFilePropertiesToDTOS(filePropertiesDAO.getFilePropertiesByOwner(fileOwner));
    }


    @Override
    public void updateFileProperties(FilePropertiesDTO filePropertiesDTO) {
        try {
            filePropertiesDAO.updateFileProperties(filePropertiesDataMapper.mapDTOtoEntity(filePropertiesDTO));
        } catch (Exception e){
            //throw new WebApplicationException("Could not update provided entity", Response.Status.NOT_ACCEPTABLE);
        }
    }
}
