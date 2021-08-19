package org.jkdev.service;

import org.jkdev.file.storage.api.FileStorageDTO;

public interface FileStorageService {

    void saveFile(FileStorageDTO fileStorageDTO);

    FileStorageDTO getFile(String fileIdentifier, String fileOwner);

    void deleteFile(String fileIdentifier, String fileOwner);



}
