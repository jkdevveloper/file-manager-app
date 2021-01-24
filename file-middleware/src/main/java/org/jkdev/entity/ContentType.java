package org.jkdev.entity;

public enum ContentType {
    PDF, JPG, JPEG;

    ContentType(){}

    public static String getStringValueForEnum(){
        return PDF.toString();
    }
}
