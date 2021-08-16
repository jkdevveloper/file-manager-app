package org.jkdev.dao;

import org.jkdev.entity.FileStorageEntity;

public interface FileStorageDAO {

    void saveFile(FileStorageEntity fileStorageEntity);

    void deleteFile(String fileIdentifier, String fileOwner);

    FileStorageEntity getFile(String fileIdentifier, String fileOwner);

}
