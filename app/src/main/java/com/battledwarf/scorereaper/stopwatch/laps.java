package com.battledwarf.scorereaper.stopwatch;

//String car, String location, String user, String lap, int status
public class laps {
    int id;
    String car;
    String location;
    String user;
    Long lapTime;
    int status;


    public laps() {

    }

    public laps(String car, String location, String user, Long lapTime, int status) {
        this.car = car;
        this.location = location;
        this.user = user;
        this.lapTime = lapTime;
        this.status = status;
    }

    public laps(int id, String car, String location, String user, Long lapTime, int status) {
        this.id = id;
        this.car = car;
        this.location = location;
        this.user = user;
        this.lapTime = lapTime;
        this.status = status;
    }

    public laps(String car, Long lapTime, int status) {
        this.car = car;
        this.lapTime = lapTime;
        this.status = status;
    }

    public laps(String car, Long lapTime) {
        this.car = car;
        this.lapTime = lapTime;
    }


    public int getId() {
        return id;
    }

    public String getCar() {
        return car;
    }

    public String getUser() {
        return user;
    }

    public String getLocation() {
        return location;
    }

    public Long getlapTime() {
        return lapTime;
    }

    public int getStatus() {
        return status;
    }


}