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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Ticket ticket = (Ticket) o;

        if (id != ticket.id) return false;
        if (slot != ticket.slot) return false;
        return car.equals(ticket.car);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + car.hashCode();
        result = 31 * result + slot;
        return result;
    }
}
