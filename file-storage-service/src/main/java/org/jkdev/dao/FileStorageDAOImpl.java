package org.jkdev.dao;

import org.jkdev.entity.FileStorageEntity;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

@ApplicationScoped
class FileStorageDAOImpl implements FileStorageDAO {

    @Inject
    EntityManager em;

    @Override
    @Transactional
    public void saveFile(FileStorageEntity fileStorageEntity) {
        em.persist(fileStorageEntity);
    }

    @Override
    @Transactional
    public void deleteFile(String fileIdentifier, String fileOwner) {
        em.createQuery("DELETE FROM FileStorageEntity WHERE fileOwner = :fileOwner AND fileIdentifier =: fileIdentifier").setParameter("fileOwner", fileOwner).setParameter("fileIdentifier", fileIdentifier).executeUpdate();
    }

    @Override
    @Transactional
    public FileStorageEntity getFile(String fileIdentifier, String fileOwner) {
        TypedQuery<FileStorageEntity> typedQuery = em.createQuery("FROM FileStorageEntity WHERE fileOwner = :fileOwner AND fileIdentifier =: fileIdentifier", FileStorageEntity.class).setParameter("fileOwner", fileOwner).setParameter("fileIdentifier", fileIdentifier);

        return typedQuery.getSingleResult();
    }
}
