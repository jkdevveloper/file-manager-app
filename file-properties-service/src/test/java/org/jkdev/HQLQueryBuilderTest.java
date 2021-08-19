package org.jkdev;

import org.jkdev.util.HQLQueryBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.UUID;

@Tag("UnitTest")
public class HQLQueryBuilderTest {

    @Test
    public void shouldProperlyBuildHQLQueryForGivenParameters() {
        String fileIdentifier = UUID.randomUUID().toString();
        String buildedQuery = HQLQueryBuilder.buildFilePropertiesHQLFilteringQuery("file.txt", "fileOwner", "2020-01-01", fileIdentifier);

        Assertions.assertEquals("from FilePropertiesEntity F where F.fileName = :file_name AND F.fileOwner = :file_owner AND F.dateUploaded = :date_uploaded  AND F.fileIdentifier = :file_identifier ", buildedQuery);
    }


}
