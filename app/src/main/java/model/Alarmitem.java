package model;

/**
 * Created by a on 07/10/2017.
 */

public class Alarmitem {
    String name, desc, time, reach, id;

    public Alarmitem(String id, String name, String onvan, String time, String reach) {
        this.name = name;
        desc = onvan;
        this.time = time;
        this.reach = reach;
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public String getDesc() {
        return this.desc;
    }

    public String getTime() {
        return this.time;
    }

    public String getReach() {
        return this.reach;
    }

    public String getID() {
        return this.id;
    }
}
