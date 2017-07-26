package com.gojek.beans;

import com.sun.deploy.util.StringUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Created by a1dmiuxe(gopesh.tulsyan) on 25/07/17.
 */

public class ParkingLot {

    private NavigableSet<Integer> availableSlots;
    private Map<String, Set<Ticket>> colorTicketSet;
    private Map<Integer, Ticket> issuedTickets;
    private int parkingSize;

    public ParkingLot(){
        availableSlots = new TreeSet<>();
        colorTicketSet = new HashMap<>();
        issuedTickets = new HashMap<>();

    }

    public void initializeSlots(int slots){
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

    public List<String> getStatus(){
        List<String> rows = new ArrayList<>();
        List<String> headers = Arrays.asList("Slot No.", "Registration Number", "Color");
        rows.add(StringUtils.join(headers,"\t"));
        Collection<Ticket> tickets = issuedTickets.values();
        tickets.forEach(ticket ->
            rows.add(Integer.toString(ticket.getSlot())+"\t"+ticket.getCar().getRegistrationNumber()+
                    "\t"+ticket.getCar().getColor().getColorName())
        );
        return rows;
    }


    public static void main(String[] args) {
        try(BufferedReader br = new BufferedReader(new FileReader("/Users/a1dmiuxe/parkinglot.txt"))) {
            String line;
            ParkingLot parkingLot = new ParkingLot();
            while ((line = br.readLine())!=null){
                String[] entry = line.split(" ");
                try {
                    switch (entry[0]){
                        case "create_parking_lot":
                            parkingLot.initializeSlots(Integer.parseInt(entry[1]));
                            System.out.println("Created a parking lot with "+entry[1]+" slots");
                            break;
                        case "park":
                            Ticket ticket = parkingLot.issueTicket(entry[1], entry[2]);
                            System.out.println("Allocated slot number: "+ticket.getSlot());
                            break;
                        case "leave":
                            parkingLot.exit(Integer.parseInt(entry[1]));
                            System.out.println("Slot number "+entry[1]+" is free");
                            break;
                        case "status":
                            List<String> table = parkingLot.getStatus();
                            for (String row : table)
                                System.out.println(row);
                            break;
                        case "registration_numbers_for_cars_with_colour":
                            List<String> registrationNumbers = parkingLot.getRegistrationNumbersByColor(entry[1]);
                            String result = StringUtils.join(registrationNumbers, ",");
                            System.out.println(result);
                            break;
                        case "slot_numbers_for_cars_with_colour":
                            List<Integer> slots = parkingLot.getSlotNumbersByColor(entry[1]);
                            for (int i=0;i<slots.size()-1;i++)
                                System.out.print(slots.get(i)+",");
                            System.out.print(slots.get(slots.size()-1)+"\n");
                            break;
                        default:
                            System.out.println("Invalid Command!!!!");
                    }
                }catch (RuntimeException e){
                    System.out.println(e.getMessage());
                }

            }
        }catch (IOException io){

        }
    }





}
