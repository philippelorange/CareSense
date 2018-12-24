package ca.ecaconcordia.enggames.caresense.common;

public class ActiveRoomInformation {
    private String current_room;
    private String timestamp;

    public ActiveRoomInformation(String current_room, String timestamp) {
        this.current_room = current_room;
        this.timestamp = timestamp;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getRoom() {
        return current_room;
    }
}
