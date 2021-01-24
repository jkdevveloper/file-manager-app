package org.jkdev.util;

import javax.persistence.Query;
import java.util.Objects;

public class HQLQueryBuilder {

    public static String buildFilePropertiesHQLFilteringQuery(String fileName, String fileOwner, String dateUploaded, String fileIdentifier){
        StringBuilder queryBuilder = new StringBuilder("from FileProperties ");

        if (Objects.nonNull(fileName) || Objects.nonNull(fileOwner) || Objects.nonNull(dateUploaded) || Objects.nonNull(fileIdentifier)) {
            queryBuilder.append("F where ");
        }
        if (Objects.nonNull(fileName)) {
            queryBuilder.append("F.fileName = :file_name");
        }
        if (Objects.nonNull(fileOwner)) {
            if (!queryBuilder.toString().contains("AND") && Objects.nonNull(fileName)) {
                queryBuilder.append(" AND ");
            }
            queryBuilder.append("F.fileOwner = :file_owner");
        }
        if (Objects.nonNull(dateUploaded)) {
            if (Objects.nonNull(fileName) || Objects.nonNull(fileOwner)) {
                queryBuilder.append(" AND ");
            }
            queryBuilder.append("F.dateUploaded = :date_uploaded ");
        }if (Objects.nonNull(fileIdentifier)) {
            if (Objects.nonNull(fileName) || Objects.nonNull(fileOwner) || Objects.nonNull(dateUploaded)) {
                queryBuilder.append(" AND ");
            }
            queryBuilder.append("F.fileIdentifier = :file_identifier ");
        }

        return queryBuilder.toString();
    }

    public static void setParametersOnHQLFilteringQuery(Query query, String fileName, String fileOwner, String dateUploaded, String fileIdentifier){

        if (Objects.nonNull(fileName)) {
            query.setParameter("file_name", fileName);
        }
        if (Objects.nonNull(fileOwner)) {
            query.setParameter("file_owner", fileOwner);
        }
        if (Objects.nonNull(dateUploaded)) {
            query.setParameter("date_uploaded", dateUploaded);
        }
        if(Objects.nonNull(fileIdentifier)){
            query.setParameter("file_identifier", fileIdentifier);
        }
    }

}
