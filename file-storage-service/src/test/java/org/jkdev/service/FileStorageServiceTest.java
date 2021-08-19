package org.jkdev.service;

import org.jkdev.dao.FileStorageDAO;
import org.jkdev.entity.FileStorageEntity;
import org.jkdev.file.storage.api.FileStorageDTO;
import org.jkdev.mapper.FileStorageDataMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.UUID;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Tag("UnitTest")
public class FileStorageServiceTest {

    @InjectMocks
    FileStorageServiceImpl fileStorageService;

    @Mock
    FileStorageDAO fileStorageDAO;

    @Mock
    FileStorageDataMapper fileStorageDataMapper;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSaveFile(){
        FileStorageDTO sampleStorageDTO = new FileStorageDTO();
        sampleStorageDTO.setFileContent("someContent");
        sampleStorageDTO.setFileOwner("someOwner");
        sampleStorageDTO.setFileIdentifier("someIdentifier");

        fileStorageService.saveFile(sampleStorageDTO);
        verify(fileStorageDAO, times(1)).saveFile(any());
        verify(fileStorageDataMapper, times(1)).mapDTOtoEntity(any());
    }

    @Test
    public void testGetFile() {
        String fileIdentifier = UUID.randomUUID().toString();
        String fileOwner = "fileOwner";
        FileStorageEntity fileStorageEntity = new FileStorageEntity("someContent", fileOwner, fileIdentifier);
        FileStorageDTO expectedFileStorageDTO = new FileStorageDTO();
        expectedFileStorageDTO.setFileContent("someContent");
        expectedFileStorageDTO.setFileOwner(fileOwner);
        expectedFileStorageDTO.setFileIdentifier(fileIdentifier);
        when(fileStorageDAO.getFile(fileIdentifier, fileOwner)).thenReturn(fileStorageEntity);
        when(fileStorageDataMapper.mapEntityToDTO(fileStorageEntity)).thenReturn(expectedFileStorageDTO);

        FileStorageDTO fileStorageDTO = fileStorageService.getFile(fileIdentifier, fileOwner);

        Assertions.assertEquals(fileIdentifier, fileStorageDTO.getFileIdentifier());
        Assertions.assertEquals(fileOwner, fileStorageDTO.getFileOwner());
        Assertions.assertEquals("someContent", fileStorageDTO.getFileContent());

        verify(fileStorageDAO, times(1)).getFile(any(), any());
        verify(fileStorageDataMapper, times(1)).mapEntityToDTO(any());
    }

}
