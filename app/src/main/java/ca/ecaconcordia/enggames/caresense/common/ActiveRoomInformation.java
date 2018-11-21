package ca.ecaconcordia.enggames.caresense.common;

import java.util.Date;

public class ActiveRoomInformation {
    private String room;
    private Date timestamp;

    public ActiveRoomInformation(String room, Date timestamp) {
        this.room = room;
        this.timestamp = timestamp;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public String getRoom() {
        return room;
    }

}
