package com.battledwarf.scorereaper.points;

public class points {
    private final String name;
    private final String time;
    private final String points;
    private final int status;

    public points(String name, String time, String points, int status) {
        this.name = name;
        this.time = time;
        this.points = points;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public String getTime() {
        return time;
    }

    public String getPoints() {
        return points;
    }

    public int getStatus() {
        return status;
    }
}