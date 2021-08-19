package org.jkdev;

import org.jkdev.entity.FilePropertiesEntity;
import org.jkdev.file.properties.api.FilePropertiesDTO;
import org.jkdev.util.FilePropertiesDataMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

@Tag("UnitTest")
public class FilePropertiesDataMapperTest {

    private static FilePropertiesDataMapper filePropertiesDataMapper;

    @BeforeAll
    static void setUp() {
        filePropertiesDataMapper = new FilePropertiesDataMapper();
    }

    @Test
    public void shouldProperlyMapEntityToDTO() {
        FilePropertiesEntity filePropertiesEntity = new FilePropertiesEntity();
        filePropertiesEntity.setFileIdentifier(UUID.randomUUID().toString());
        filePropertiesEntity.setFileOwner("someFileOwner");
        filePropertiesEntity.setDateUploaded("2020-01-01");
        filePropertiesEntity.setFileName("someFileName");

        FilePropertiesDTO filePropertiesDTO = filePropertiesDataMapper.mapEntityToDTO(filePropertiesEntity);

        Assertions.assertEquals(filePropertiesEntity.getFileIdentifier(), filePropertiesDTO.getFileIdentifier());
        Assertions.assertEquals(filePropertiesEntity.getFileOwner(), filePropertiesDTO.getFileOwner());
        Assertions.assertEquals(filePropertiesEntity.getDateUploaded(), filePropertiesDTO.getDateUploaded());
        Assertions.assertEquals(filePropertiesEntity.getFileName(), filePropertiesDTO.getFileName());
    }

    @Test
    public void shouldProperlyMapDTOtoEntity() {
        FilePropertiesDTO filePropertiesDTO = new FilePropertiesDTO();
        filePropertiesDTO.setFileIdentifier(UUID.randomUUID().toString());
        filePropertiesDTO.setFileOwner("someFileOwner");
        filePropertiesDTO.setDateUploaded("2020-01-01");
        filePropertiesDTO.setFileName("someFileName");

        FilePropertiesEntity filePropertiesEntity = filePropertiesDataMapper.mapDTOtoEntity(filePropertiesDTO);

        Assertions.assertEquals(filePropertiesDTO.getFileIdentifier(), filePropertiesEntity.getFileIdentifier());
        Assertions.assertEquals(filePropertiesDTO.getFileOwner(), filePropertiesEntity.getFileOwner());
        Assertions.assertEquals(filePropertiesDTO.getDateUploaded(), filePropertiesEntity.getDateUploaded());
        Assertions.assertEquals(filePropertiesDTO.getFileName(), filePropertiesEntity.getFileName());
    }

    @Test
    public void shouldProperlyMapListOfEntitiesToListOfDTOS() {
        String firstIdentifier = UUID.randomUUID().toString();
        String secondIdentifier = UUID.randomUUID().toString();

        List<FilePropertiesEntity> filePropertiesEntities = List.of(
                buildFilePropertiesEntity(firstIdentifier, "someFileOwner", "2020-01-01", "file.txt"),
                buildFilePropertiesEntity(secondIdentifier, "someOtherFileOwner", "2020-01-02", "file1.txt")
        );

        List<FilePropertiesDTO> expectedFilePropertiesDTOS = List.of(
                buildFilePropertiesDTO(firstIdentifier, "someFileOwner", "2020-01-01", "file.txt"),
                buildFilePropertiesDTO(secondIdentifier, "someOtherFileOwner", "2020-01-02", "file1.txt")
        );

        List<FilePropertiesDTO> actualFilePropertiesDTOS = filePropertiesDataMapper.mapFilePropertiesToDTOS(filePropertiesEntities);

        Assertions.assertEquals(expectedFilePropertiesDTOS, actualFilePropertiesDTOS);
    }

    private FilePropertiesDTO buildFilePropertiesDTO(String fileIdentifier, String fileOwner, String dateUploaded, String fileName) {
        FilePropertiesDTO filePropertiesDTO = new FilePropertiesDTO();
        filePropertiesDTO.setFileIdentifier(fileIdentifier);
        filePropertiesDTO.setFileOwner(fileOwner);
        filePropertiesDTO.setDateUploaded(dateUploaded);
        filePropertiesDTO.setFileName(fileName);

        return filePropertiesDTO;
    }

    private FilePropertiesEntity buildFilePropertiesEntity(String fileIdentifier, String fileOwner, String dateUploaded, String fileName) {
        FilePropertiesEntity filePropertiesEntity = new FilePropertiesEntity();
        filePropertiesEntity.setFileIdentifier(fileIdentifier);
        filePropertiesEntity.setFileOwner(fileOwner);
        filePropertiesEntity.setDateUploaded(dateUploaded);
        filePropertiesEntity.setFileName(fileName);

        return filePropertiesEntity;
    }

}
