package com.jacksplwxy.start;

public class Scenery {
    private Number id;
    private String name;
    private String address;

    public Scenery(Number id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }

    public Number getId() {
        return id;
    }

    public void setId(Number id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}
