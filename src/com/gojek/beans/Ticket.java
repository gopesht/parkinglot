package com.gojek.beans;

/**
 * Created by a1dmiuxe(gopesh.tulsyan) on 25/07/17.
 */

public class Ticket {
    private int id;
    private Car car;
    private int slot;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public int getSlot() {
        return slot;
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }


}
