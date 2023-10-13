package com.baeldung.springbootreact.domain;

public enum RoomType {

    ROOM("room"), FLOOR("floor"), BUILDING("building");
    String value;

    RoomType(String value){
        this.value = value;
    }


    static RoomType to11(String value){
        return RoomType.valueOf(value.toUpperCase());
    }

}
