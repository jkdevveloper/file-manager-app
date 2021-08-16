package org.jkdev.service;

import org.jkdev.dao.FilePropertiesDAO;
import org.jkdev.entity.FileProperties;
import org.jkdev.file.properties.api.FilePropertiesDTO;
import org.jkdev.util.EntityToDTOMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.util.List;

@ApplicationScoped
class FilePropertiesServiceImpl implements FilePropertiesService {

    @Inject
    FilePropertiesDAO filePropertiesDAO;

    @Inject
    EntityToDTOMapper entityToDTOMapper;

    private static final Logger logger = LoggerFactory.getLogger(FilePropertiesServiceImpl.class);

    // handles requests and uses DAO as bridge to make database queries

    @Override
    public void saveFileProperties(FilePropertiesDTO filePropertiesDTO) {
        try {
            filePropertiesDAO.saveFileProperties(entityToDTOMapper.mapDTOtoEntity(filePropertiesDTO));
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
        List<FileProperties> filePropertiesList = filePropertiesDAO.getFilePropertiesByFilters(fileName, fileOwner, dateUploaded, fileIdentifier);

        if (filePropertiesList.size() > 0) {
            logger.info(String.valueOf(filePropertiesList));
            return entityToDTOMapper.mapFilePropertiesToDTOS(filePropertiesList);

        } else {
            logger.error("Could not fetch file properties for provided filter parameters" +
                    " fileName: {}, fileOwner: {}, dateUploaded: {}, fileIdentifier: {}", fileName, fileOwner, dateUploaded, fileIdentifier);
            return null;
            //throw new WebApplicationException("Could not fetch file properties for provided parameters", Response.Status.NOT_FOUND);
        }
    }

    @Override
    public List<FilePropertiesDTO> getFilePropertiesList() {
        return entityToDTOMapper.mapFilePropertiesToDTOS(filePropertiesDAO.getFileProperties());
    }

    @Override
    public List<FilePropertiesDTO> getFilePropertiesByOwner(String fileOwner) {
        return entityToDTOMapper.mapFilePropertiesToDTOS(filePropertiesDAO.getFilePropertiesByOwner(fileOwner));
    }


    @Override
    public void updateFileProperties(FilePropertiesDTO filePropertiesDTO) {
        try {
            filePropertiesDAO.updateFileProperties(entityToDTOMapper.mapDTOtoEntity(filePropertiesDTO));
        } catch (Exception e){
            //throw new WebApplicationException("Could not update provided entity", Response.Status.NOT_ACCEPTABLE);
        }
    }
}
