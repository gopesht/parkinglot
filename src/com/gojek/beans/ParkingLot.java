package com.gojek.beans;

import java.util.*;

/**
 * Created by a1dmiuxe(gopesh.tulsyan) on 25/07/17.
 */

public class ParkingLot {

    private NavigableSet<Integer> availableSlots = new TreeSet<>();
    private Map<String, Set<Ticket>> colorTicketSet = new HashMap<>();
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

    private void validateColor(String color){
        if (color == null || color.equals(""))
            throw new IllegalArgumentException("Empty color");
    }

    public Ticket issueTicket(String registrationNumber, String colorName) throws RuntimeException, IllegalArgumentException{
        if (availableSlots.size()==0)
            throw new RuntimeException("Sorry, parking lot is full");
        if (registrationNumber == null || registrationNumber.equals(""))
            throw new IllegalArgumentException("Empty registration number");
        validateColor(colorName);

        Car car = new Car();
        Color color = new Color();
        color.setColor(colorName);
        car.setColor(color);
        car.setRegistrationNumber(registrationNumber);
        Ticket ticket = new Ticket();
        ticket.setId(UUID.randomUUID().hashCode());
        ticket.setSlot(availableSlots.pollFirst());
        ticket.setCar(car);
        colorTicketSet.putIfAbsent(colorName, new HashSet<>());
        colorTicketSet.get(colorName).add(ticket);
        issuedTickets.put(ticket.getSlot(), ticket);
        return ticket;
    }


    public void exit(int slot){
        if (slot>parkingSize || slot<0)
            throw new IllegalArgumentException("Invalid slot");
        Ticket ticket = issuedTickets.remove(slot);
        availableSlots.add(slot);
        colorTicketSet.get(ticket.getCar().getColor().getColorName()).remove(ticket);
    }


    public List<Integer> getSlotNumbersByColor(String colorName){
        validateColor(colorName);
        Set<Ticket> tickets = colorTicketSet.get(colorName);
        List<Integer> slotNumbers = new ArrayList<>();
        for (Ticket ticket: tickets)
            slotNumbers.add(ticket.getSlot());
        return slotNumbers;
    }


    public List<String> getRegistrationNumbersByColor(String colorName){
        validateColor(colorName);
        Set<Ticket> tickets = colorTicketSet.get(colorName);
        List<String> slotNumbers = new ArrayList<>();
        for (Ticket ticket: tickets)
            slotNumbers.add(ticket.getCar().getRegistrationNumber());
        return slotNumbers;
    }

    






}
