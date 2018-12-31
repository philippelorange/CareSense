package ca.ecaconcordia.enggames.caresense.common;

public enum Room {
    LIVING_ROOM("Living Room"),
    KITCHEN("Kitchen"),
    BATHROOM("Bathroom"),
    BEDROOM("Bedroom");

    private String room;

    Room(String room) {
        this.room = room;
    }

    public String getRoom() {
        return room;
    }
}
