package org.jkdev.dao;

import org.jkdev.entity.FilePropertiesEntity;
import org.jkdev.util.HQLQueryBuilder;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;

@ApplicationScoped
class FilePropertiesDAOImpl implements FilePropertiesDAO {

    @Inject
    EntityManager em;

    @Override
    @Transactional
    public void saveFileProperties(FilePropertiesEntity filePropertiesEntity) {
        em.persist(filePropertiesEntity);
    }

    @Override
    @Transactional
    public void deleteFileProperties(String fileIdentifier, String fileOwner) {
        em.createQuery("delete from FilePropertiesEntity where fileIdentifier = :fileIdentifier and fileOwner = :fileOwner").setParameter("fileIdentifier", fileIdentifier).setParameter("fileOwner", fileOwner).executeUpdate();
    }

    @Override
    @Transactional
    public List<FilePropertiesEntity> getFilePropertiesById(String id) {
        return Collections.emptyList();
    }

    @Transactional
    public List<FilePropertiesEntity> getFileProperties() {
        return em.createQuery("from FileProperties", FilePropertiesEntity.class).getResultList();
    }

    @Override
    @Transactional
    public List<FilePropertiesEntity> getFilePropertiesByFileName(String fileName) {
        return Collections.emptyList();
    }

    @Override
    @Transactional
    public List<FilePropertiesEntity> getFilePropertiesByFilters(String fileName, String fileOwner, String dateUploaded, String fileIdentifier) {
        String query = HQLQueryBuilder.buildFilePropertiesHQLFilteringQuery(fileName, fileOwner, dateUploaded, fileIdentifier);

        TypedQuery<FilePropertiesEntity> typedQuery = em.createQuery(query, FilePropertiesEntity.class);

        HQLQueryBuilder.setParametersOnHQLFilteringQuery(typedQuery, fileName, fileOwner, dateUploaded, fileIdentifier);

        return typedQuery.getResultList();
    }

    @Override
    @Transactional
    public List<FilePropertiesEntity> getFilePropertiesByOwner(String fileOwner) {
        return em.createQuery("from FileProperties F where F.fileOwner = :file_owner", FilePropertiesEntity.class).setParameter("file_owner", fileOwner).getResultList();
    }

    @Override
    @Transactional
    public void updateFileProperties(FilePropertiesEntity filePropertiesEntity) {
        em.merge(filePropertiesEntity);
    }
}
