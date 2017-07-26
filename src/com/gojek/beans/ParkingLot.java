package com.gojek.beans;


import java.util.*;

/**
 * Created by a1dmiuxe(gopesh.tulsyan) on 25/07/17.
 */

public class ParkingLot {

    private NavigableSet<Integer> availableSlots;
    private Map<String, Color> colorMap;
    private Map<String, Ticket> parkedCars;
    private Map<Integer, Ticket> bookedSlots;
    private int parkingSize;

    public ParkingLot(){
        availableSlots = new TreeSet<>();
        colorMap = new HashMap<>();
        parkedCars = new HashMap<>();
        bookedSlots = new HashMap<>();

    }

    public void initializeSlots(int slots){
        for (int i=1;i<=slots;i++){
            availableSlots.add(i);
        }
        parkingSize = slots;
    }


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

        colorMap.putIfAbsent(colorName, new Color(colorName));
        Color color = colorMap.get(colorName);

        car.setColor(color);
        car.setRegistrationNumber(registrationNumber);
        int slot = availableSlots.pollFirst();
        Ticket ticket = new Ticket();
        ticket.setCar(car);
        ticket.setId(UUID.randomUUID().hashCode());
        ticket.setSlot(slot);
        car.setTicket(ticket);
        color.getCars().add(car);
        parkedCars.put(car.getRegistrationNumber(), ticket);
        bookedSlots.put(slot, ticket);
        return ticket;
    }


    public void exit(int slot){
        if (slot>parkingSize || slot<0)
            throw new IllegalArgumentException("Invalid slot");
        if (bookedSlots.get(slot) == null)
            throw new IllegalArgumentException("Slot not booked yet!");
        Ticket ticket = bookedSlots.get(slot);
        availableSlots.add(slot);
        parkedCars.remove(ticket.getCar().getRegistrationNumber());
        bookedSlots.remove(slot);
        colorMap.get(ticket.getCar().getColor().getColorName()).getCars().remove(ticket.getCar());
    }


    public List<Integer> getSlotNumbersByColor(String colorName){
        validateColor(colorName);
        Color color = colorMap.get(colorName);
        if (null == color)
            throw new IllegalArgumentException("Invalid Color");
        List<Integer> slotNumbers = new ArrayList<>();
        for (Car car: color.getCars())
            slotNumbers.add(car.getTicket().getSlot());
        return slotNumbers;
    }


    public List<String> getRegistrationNumbersByColor(String colorName){
        validateColor(colorName);
        Color color = colorMap.get(colorName);
        if (null == color)
            throw new IllegalArgumentException("Invalid Color");
        List<String> registrationNumbers = new ArrayList<>();
        for (Car car: color.getCars())
            registrationNumbers.add(car.getRegistrationNumber());
        return registrationNumbers;
    }

    public List<String> getStatus(){
        List<String> rows = new ArrayList<>();
        List<String> headers = Arrays.asList();
        rows.add("Slot No.," + "Registration Number,"+"Color"+"\t");

        parkedCars.values().forEach(ticket ->
            rows.add(Integer.toString(ticket.getSlot())+"\t"+ticket.getCar().getRegistrationNumber()+
                    "\t"+ticket.getCar().getColor().getColorName())
        );
        return rows;
    }

    public Ticket getSlotByRegistrationNumber(String registrationNumber){
        if (null == registrationNumber || registrationNumber.equals(""))
            throw new IllegalArgumentException("Empty registrationNumber");
        return parkedCars.get(registrationNumber);
    }








}
