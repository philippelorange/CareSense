package ca.ecaconcordia.enggames.caresense.common;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ActiveRoomInformation {
    private Room current_room;
    private Date timestamp;

    public ActiveRoomInformation(Room current_room, Date timestamp) {
        this.current_room = current_room;
        this.timestamp = timestamp;
    }

    public ActiveRoomInformation() {
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public Room getRoom() {
        return current_room;
    }
}
