package org.jkdev.dao;

import org.jkdev.entity.FileProperties;
import org.jkdev.util.HQLQueryBuilder;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@ApplicationScoped
public class FilePropertiesDAOImpl implements FilePropertiesDAO {

    // all the database stuff

    @Inject
    private EntityManager em;

    @Override
    @Transactional
    public void saveFileProperties(FileProperties fileProperties) {
        em.persist(fileProperties);
    }

    @Override
    @Transactional
    public void deleteFilePropertiesById(Long id) {
        em.createQuery("delete FileProperties where id = :id").setParameter("id", id).executeUpdate();
    }

    @Override
    @Transactional
    public List<FileProperties> getFilePropertiesById(String id) {
        return Collections.emptyList();
    }

    @Transactional
    public List<FileProperties> getFileProperties() {
        return em.createQuery("from FileProperties", FileProperties.class).getResultList();
    }

    @Override
    @Transactional
    public List<FileProperties> getFilePropertiesByFileName(String fileName) {
        return Collections.emptyList();
    }

    @Override
    @Transactional
    public List<FileProperties> getFilePropertiesByFilters(String fileName, String fileOwner, String dateUploaded, String fileIdentifier) {
        String query = HQLQueryBuilder.buildFilePropertiesHQLFilteringQuery(fileName, fileOwner, dateUploaded, fileIdentifier);

        TypedQuery<FileProperties> typedQuery = em.createQuery(query, FileProperties.class);

        HQLQueryBuilder.setParametersOnHQLFilteringQuery(typedQuery, fileName, fileOwner, dateUploaded, fileIdentifier);

        return typedQuery.getResultList();
    }

    @Override
    @Transactional
    public List<FileProperties> getFilePropertiesByOwner(String fileOwner) {
        return em.createQuery("from FileProperties F where F.fileOwner = :file_owner", FileProperties.class).setParameter("file_owner", fileOwner).getResultList();
    }

    @Override
    @Transactional
    public void updateFileProperties(FileProperties fileProperties) {
        em.merge(fileProperties);
    }
}
