package ca.ecaconcordia.enggames.caresense.common;

public class Sensor {

    private Room roomOne;
    private Room roomTwo;

    public Sensor(Room roomOne, Room roomTwo) {
        this.roomOne = roomOne;
        this.roomTwo = roomTwo;
    }


    public Room getRoomOne() {
        return roomOne;
    }

    public Room getRoomTwo() {
        return roomTwo;
    }
}
