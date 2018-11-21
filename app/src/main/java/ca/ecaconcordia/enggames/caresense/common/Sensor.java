package ca.ecaconcordia.enggames.caresense.common;

public class Sensor {
    private String roomOne;
    private String roomTwo;

    public Sensor(String roomOne, String roomTwo) {
        this.roomOne = roomOne;
        this.roomTwo = roomTwo;
    }

    public String getRoomOne() {
        return roomOne;
    }


    public String getRoomTwo() {
        return roomTwo;
    }

}
