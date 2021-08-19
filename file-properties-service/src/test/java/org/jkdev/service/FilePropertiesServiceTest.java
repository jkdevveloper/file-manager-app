package org.jkdev.service;

import org.jkdev.dao.FilePropertiesDAO;
import org.jkdev.entity.FilePropertiesEntity;
import org.jkdev.file.properties.api.FilePropertiesDTO;
import org.jkdev.util.FilePropertiesDataMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.UUID;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Tag("UnitTest")
public class FilePropertiesServiceTest {

    @InjectMocks
    FilePropertiesServiceImpl filePropertiesService;

    @Mock
    FilePropertiesDAO filePropertiesDAO;

    @Mock
    FilePropertiesDataMapper filePropertiesDataMapper;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSaveFile(){
        FilePropertiesDTO samplePropertiesDTO = new FilePropertiesDTO();
        samplePropertiesDTO.setFileName("someFileName.txt");
        samplePropertiesDTO.setFileOwner("someOwner");
        samplePropertiesDTO.setFileIdentifier("someIdentifier");

        filePropertiesService.saveFileProperties(samplePropertiesDTO);
        verify(filePropertiesDAO, times(1)).saveFileProperties(any());
        verify(filePropertiesDataMapper, times(1)).mapDTOtoEntity(any());
    }

    @Test
    public void testGetFile() {
        String fileIdentifier = UUID.randomUUID().toString();
        String fileOwner = "fileOwner";
        FilePropertiesEntity fileStorageEntity = new FilePropertiesEntity("someFileName.txt", fileOwner, fileIdentifier, "2020-01-01");
        FilePropertiesDTO expectedFileStorageDTO = new FilePropertiesDTO();
        expectedFileStorageDTO.setFileName("someFileName.txt");
        expectedFileStorageDTO.setFileOwner(fileOwner);
        expectedFileStorageDTO.setFileIdentifier(fileIdentifier);
        expectedFileStorageDTO.setDateUploaded("2020-01-01");
        when(filePropertiesDAO.getFilePropertiesByOwner(fileOwner)).thenReturn(List.of(fileStorageEntity));
        when(filePropertiesDataMapper.mapFilePropertiesToDTOS(List.of(fileStorageEntity))).thenReturn(List.of(expectedFileStorageDTO));

        FilePropertiesDTO fileStorageDTO = filePropertiesService.getFilePropertiesByOwner(fileOwner).get(0);

        Assertions.assertEquals(fileIdentifier, fileStorageDTO.getFileIdentifier());
        Assertions.assertEquals(fileOwner, fileStorageDTO.getFileOwner());
        Assertions.assertEquals("someFileName.txt", fileStorageDTO.getFileName());

        verify(filePropertiesDAO, times(1)).getFilePropertiesByOwner(any());
        verify(filePropertiesDataMapper, times(1)).mapFilePropertiesToDTOS(any());
    }

}
