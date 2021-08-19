package org.jkdev;

import org.jkdev.entity.FileStorageEntity;
import org.jkdev.file.storage.api.FileStorageDTO;
import org.jkdev.mapper.FileStorageDataMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.UUID;

@Tag("UnitTest")
public class FileStorageDataMapperTest {

    private static FileStorageDataMapper fileStorageDataMapper;

    @BeforeAll
    static void setUp() {
        fileStorageDataMapper = new FileStorageDataMapper();
    }

    @Test
    public void shouldProperlyMapEntityToDTO() {
        FileStorageEntity fileStorageEntity = new FileStorageEntity();

        String fileIdentifier = UUID.randomUUID().toString();

        fileStorageEntity.setFileContent("someFileContent");
        fileStorageEntity.setFileOwner("someFileOwner");
        fileStorageEntity.setFileIdentifier(fileIdentifier);


        FileStorageDTO fileStorageDTO = fileStorageDataMapper.mapEntityToDTO(fileStorageEntity);

        Assertions.assertEquals(fileStorageDTO.getFileContent(), fileStorageEntity.getFileContent());
        Assertions.assertEquals(fileStorageDTO.getFileOwner(), fileStorageEntity.getFileOwner());
        Assertions.assertEquals(fileStorageDTO.getFileIdentifier(), fileStorageEntity.getFileIdentifier());
    }

    @Test
    public void shouldProperlyMapDTOtoEntity() {
        FileStorageDTO fileStorageDTO = new FileStorageDTO();

        String fileIdentifier = UUID.randomUUID().toString();

        fileStorageDTO.setFileContent("someFileContent");
        fileStorageDTO.setFileOwner("someFileOwner");
        fileStorageDTO.setFileIdentifier(fileIdentifier);


        FileStorageEntity fileStorageEntity = fileStorageDataMapper.mapDTOtoEntity(fileStorageDTO);

        Assertions.assertEquals(fileStorageDTO.getFileContent(), fileStorageEntity.getFileContent());
        Assertions.assertEquals(fileStorageDTO.getFileOwner(), fileStorageEntity.getFileOwner());
        Assertions.assertEquals(fileStorageDTO.getFileIdentifier(), fileStorageEntity.getFileIdentifier());
    }

}
