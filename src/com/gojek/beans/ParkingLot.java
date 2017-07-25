package com.gojek.beans;

import java.util.*;

/**
 * Created by a1dmiuxe(gopesh.tulsyan) on 25/07/17.
 */

public class ParkingLot {

    private NavigableSet<Integer> availableSlots;
    private Map<Color, Set<Ticket>> colorListMap = new HashMap<>();
    private Map<Integer, Ticket> issuedTickets = new HashMap<>();
    private int parkingSize;

    public ParkingLot(int slots){
        for (int i=1;i<=slots;i++){
            availableSlots.add(i);
        }
        parkingSize = slots;
    }

//    private void validate(String registrationNumber, String name){
//        if (registrationNumber == null || registrationNumber.equals(""))
//            throw new IllegalArgumentException("Empty registration number");
//        else if (name == null || name.equals(""))
//            throw new IllegalArgumentException("Empty color");
//    }

    public Ticket issueTicket(String registrationNumber, String name) throws RuntimeException, IllegalArgumentException{
        if (availableSlots.size()==0)
            throw new RuntimeException("Parking Full!");
        if (registrationNumber == null || registrationNumber.equals(""))
            throw new IllegalArgumentException("Empty registration number");
        else if (name == null || name.equals(""))
            throw new IllegalArgumentException("Empty color");

        Car car = new Car();
        Color color = new Color();
        color.setColor(name);
        car.setColor(color);
        car.setRegistrationNumber(registrationNumber);
        Ticket ticket = new Ticket();
        ticket.setId(UUID.randomUUID().hashCode());
        ticket.setSlot(availableSlots.pollFirst());
        ticket.setCar(car);
        colorListMap.putIfAbsent(color, new HashSet<>());
        colorListMap.get(color).add(ticket);
        issuedTickets.put(ticket.getSlot(), ticket);
        return ticket;
    }


    public void exit(int slot){
        if (slot>parkingSize || slot<0)
            throw new IllegalArgumentException("Invalid slot");
        Ticket ticket = issuedTickets.remove(slot);
        availableSlots.add(slot);
        colorListMap.get(ticket.getCar().getColor()).remove(ticket);
    }







}
