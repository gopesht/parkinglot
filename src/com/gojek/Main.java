package com.gojek;

import com.gojek.beans.ParkingLot;
import com.gojek.beans.Ticket;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        if (args.length==0 || args[0] == "") {
            System.out.println("File name is empty!!");
            System.exit(1);
        }
        try(BufferedReader br = new BufferedReader(new FileReader(args[0]))) {
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
                            String result = "";
                            for (int i=0;i<registrationNumbers.size()-1;i++)
                                result += registrationNumbers.get(i) + ",";
                            System.out.println(result+registrationNumbers.get(registrationNumbers.size()-1));
                            break;
                        case "slot_numbers_for_cars_with_colour":
                            List<Integer> slots = parkingLot.getSlotNumbersByColor(entry[1]);
                            for (int i=0;i<slots.size()-1;i++)
                                System.out.print(slots.get(i)+",");
                            System.out.print(slots.get(slots.size()-1)+"\n");
                            break;
                        case "slot_number_for_registration_number":
                            Ticket ticket1 = parkingLot.getSlotByRegistrationNumber(entry[1]);
                            if (null == ticket1)
                                System.out.println("Not found");
                            else
                                System.out.println(ticket1.getSlot());
                            break;
                        default:
                            System.out.println("Invalid Command!!!!");
                    }
                }catch (RuntimeException e){
                    e.printStackTrace();
                    System.out.println(e.getMessage());
                }

            }
        }catch (IOException io){

        }
    }
}
