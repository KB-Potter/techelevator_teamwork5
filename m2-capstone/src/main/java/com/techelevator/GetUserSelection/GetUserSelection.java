//package com.techelevator.GetUserSelection;
//
//import com.techelevator.model.Campground;
//import com.techelevator.model.Park;
//import com.techelevator.model.Site;
//
//import java.io.PrintWriter;
//import java.time.LocalDate;
//import java.time.format.DateTimeFormatter;
//import java.util.Scanner;
//
//
//public class GetUserSelection {
//
//
//    public Park getParkSelectionFromUser(Park[] parkArray) {
//        Park parkChoice = null;
//        String userInput = in.nextLine();
//        try {
//            int selectedNumber = Integer.valueOf(userInput);
//            if(selectedNumber <= parkArray.length) {
//                parkChoice = parkArray[selectedNumber - 1];
//            } else if(selectedNumber == parkArray.length + 1) {
//                System.exit(0);
//            }
//        } catch(NumberFormatException e) {
//
//        }
//        if(parkChoice == null) {
//            out.println("\n*** " + userInput + " is not a valid park ***\n");
//        }
//        return parkChoice;
//    }
//
//    public Campground getCampgroundSelectionFromUser(Campground[] campgroundArray) {
//        Campground campgroundChoice = null;
//        String userInput = in.nextLine();
//        try {
//            int selectedNumber = Integer.valueOf(userInput);
//            if(selectedNumber > 0 && selectedNumber <= campgroundArray.length) {
//                campgroundChoice = campgroundArray[selectedNumber -1];
//            } else if (selectedNumber == 0) {
//                return null;
//            }
//        } catch(NumberFormatException e) {
//
//        }
//        if(campgroundChoice == null) {
//            out.println("\n*** " + userInput + " is not a valid campground ***\n");
//        }
//        return campgroundChoice;
//    }
//
//    public Site getSiteSelectionFromUser(Site[] siteArray) {
//        Site siteChoice = null;
//        String userInput = in.nextLine();
//        try {
//            int selectedNumber = Integer.valueOf(userInput);
//            if(selectedNumber == 0) {
//                return null;
//            }
//            for(Site site : siteArray) {
//                if(selectedNumber == site.getSiteNumber()) { //Need check here for invalid site selection
//                    siteChoice = site;
//                }
//            }
//        } catch(NumberFormatException e) {
//        }
//        if(siteChoice == null) {  //Check is coming too late
////            out.println("\n*** " + userInput + " is not a valid site ***\n");
//        }
//        return siteChoice;
//    }
//
//
//    public String getReservationName(){
//        return in.nextLine();
//    }
//
//    public LocalDate getDateFromUser(){
//        String userInput = in.nextLine();
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy"); //Grabs a readable (US) input from user and format it for the computer
//        LocalDate date;
//        try {
//            date = LocalDate.parse(userInput, formatter);  //Passes it out in sql format
//        } catch (Exception e) {
//            System.out.println("\n***Please enter a valid date***\n");
//            return null;
//        }
//        return date;
//    }
//}
