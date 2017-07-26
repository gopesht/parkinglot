package com.gojek.beans;

/**
 * Created by a1dmiuxe(gopesh.tulsyan) on 25/07/17.
 */

public class Car {
    private String registrationNumber;
    private Color color;
    private Ticket ticket;

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Car car = (Car) o;

        if (!registrationNumber.equals(car.registrationNumber)) return false;
        if (!color.equals(car.color)) return false;
        return ticket.equals(car.ticket);
    }

    @Override
    public int hashCode() {
        int result = registrationNumber.hashCode();
        result = 31 * result + color.hashCode();
        return result;
    }
}
