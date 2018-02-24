package com.techelevator.view;

import com.techelevator.model.Campground;
import com.techelevator.model.Park;
import com.techelevator.model.Site;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Locale;
import java.util.Scanner;

public class Menu {

    private final PrintWriter out;
    private final Scanner in;

    public Menu(InputStream input, OutputStream output) {
        out = new PrintWriter(output);
        in = new Scanner(input);
    }

    public Object getChoiceFromOptions(Object[] options) {
        Object choice = null;
        while(choice == null) {
            displayMenuOptions(options);
            choice = getChoiceFromUserInput(options);
        }
        return choice;
    }

    private Object getChoiceFromUserInput(Object[] options) {
        Object choice = null;
        String userInput = in.nextLine();
        try {
            int selectedOption = Integer.valueOf(userInput);
            if(selectedOption > 0 && selectedOption <= options.length) {
                choice = options[selectedOption - 1];
            }
        } catch(NumberFormatException e) {
            // eat the exception, an error message will be displayed below since choice will be null
        }
        if(choice == null) {
            out.println("\n*** "+userInput+" is not a valid option ***\n");
        }
        return choice;
    }

    public Park getParkFromInput(Park[] parkArray) {
        Park choice = null;
        while(choice == null) {
            displayParkOptions(parkArray);
            choice = getParkSelectionFromUser(parkArray);
        }
        return choice;
    }

    public Park getParkSelectionFromUser(Park[] parkArray) {
        Park parkChoice = null;
        String userInput = in.nextLine();
        try {
            int selectedNumber = Integer.valueOf(userInput);
            if(selectedNumber <= parkArray.length) {
                parkChoice = parkArray[selectedNumber - 1];
            } else if(selectedNumber == parkArray.length + 1) {
                System.exit(0);
            }
        } catch(NumberFormatException e) {

        }
        if(parkChoice == null) {
            out.println("\n*** " + userInput + " is not a valid park ***\n");
        }
        return parkChoice;
    }

    public Campground getCampgroundSelectionFromUser(Campground[] campgroundArray) {
        Campground campgroundChoice = null;
        String userInput = in.nextLine();
        try {
            int selectedNumber = Integer.valueOf(userInput);
            if(selectedNumber > 0 && selectedNumber <= campgroundArray.length) {
                campgroundChoice = campgroundArray[selectedNumber -1];
            } else if (selectedNumber == 0) {
                return null;
            }
        } catch(NumberFormatException e) {

        }
        if(campgroundChoice == null) {
            out.println("\n*** " + userInput + " is not a valid campground ***\n");
        }
        return campgroundChoice;
    }

    public Site getSiteSelectionFromUser(Site[] siteArray) {
        Site siteChoice = null;
        String userInput = in.nextLine();
        try {
            int selectedNumber = Integer.valueOf(userInput);
            if(selectedNumber == 0) {
                return null;
            }
            for(Site site : siteArray) {
                if(selectedNumber == site.getSiteNumber()) { //Need check here for invalid site selection
                    siteChoice = site;
                }
            }
        } catch(NumberFormatException e) {
        }
        if(siteChoice == null) {  //Check is coming too late
//            out.println("\n*** " + userInput + " is not a valid site ***\n");
        }
        return siteChoice;
    }


    public String getReservationName(){
        return in.nextLine();
    }

    public LocalDate getDateFromUser(){
        String userInput = in.nextLine();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy"); //Grabs a readable (US) input from user and format it for the computer
        LocalDate date;
        try {
            date = LocalDate.parse(userInput, formatter);  //Passes it out in sql format
        } catch (Exception e) {
            System.out.println("\n***Please enter a valid date***\n");
            return null;
        }
        return date;
    }

    private void displayMenuOptions(Object[] options) {
        out.println();
        for(int i = 0; i < options.length; i++) {
            int optionNum = i+1;
            out.println(optionNum+") "+options[i]);
        }
        out.print("\nPlease choose an option >>> ");
        out.flush();
    }

    private void displayParkOptions(Park[] parkArray) {
        out.println();
        int number = 0;
        for(int i = 0; i < parkArray.length; i++) {
            number = i+1;
            out.println(number + ") " + parkArray[i].getName());
        }
        number++;
        out.println(number + ") Quit");
        out.flush();
    }

    public void displayParkInfo(Park selectedPark) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy"); //US input format

        String location = selectedPark.getLocation();
        String established = selectedPark.getEstablishedDate().format(dateFormatter);
        String area = NumberFormat.getNumberInstance(Locale.US).format(selectedPark.getArea()); //add comma
        String visitors = NumberFormat.getNumberInstance(Locale.US).format(selectedPark.getVisitors());

        StringBuilder info = new StringBuilder();
        info.append(String.format("%-18s %s", "Location: ", location  + "\n"));
        info.append(String.format("%-18s %s", "Established: ", established + "\n"));
        info.append(String.format("%-18s %s", "Area: ", area  + " sq km\n"));
        info.append(String.format("%-18s %s", "Annual Visitors: ", visitors  + "\n"));

        System.out.println();
        System.out.println(info);

//        System.out.println(selectedPark.getDescription());

        StringBuilder description = new StringBuilder(selectedPark.getDescription());
        int i = 0;
        while ((i = description.indexOf(" ", i + 60)) != -1) {
            description.replace(i, i + 1, "\n");
        }
        System.out.println(description.toString());
    }

    public void displayCampgrounds(Park park, Campground[] campgrounds) {
        System.out.println();
        System.out.println(park.getName() + " National Park Campgrounds");
        System.out.println();

        String campName;
        String dateNames[] = { "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December" };
        Integer openingDateInt;
        String openingDateString;
        Integer closingDateInt;
        String closingDateString;
        String dailyFee;

        StringBuilder campgroundsDisplayString = new StringBuilder();
        campgroundsDisplayString.append(String.format("%-5s %-25s %-15s %-13s %s", "", "Name", "Open", "Close", "Daily Fee" + "\n"));

        for(int i = 0; i < campgrounds.length; i++) {

            campName = campgrounds[i].getName();
            openingDateInt = Integer.parseInt(campgrounds[i].getOpeningDate());
            openingDateString = dateNames[openingDateInt - 1];
            closingDateInt = Integer.parseInt(campgrounds[i].getClosingDate());
            closingDateString = dateNames[closingDateInt - 1];
            dailyFee = NumberFormat.getCurrencyInstance().format(campgrounds[i].getDailyFee());


            campgroundsDisplayString.append(String.format("%-5s %-25s %-15s %-13s %s", "#" + (i+1), campName,
                    openingDateString, closingDateString, dailyFee + "\n"));
        }
        System.out.println(campgroundsDisplayString);
    }

    public void displaySites(Site[] siteArray, Campground campground, LocalDate arrivalDate, LocalDate departureDate) {
        System.out.println();
        System.out.println("Results Matching Your Search Criteria");

        String siteNumber;
        String occupancy;
        String accessible;
        String maxRVLength;
        String utility;
        String cost;

        StringBuilder siteString = new StringBuilder();
        Long daysBetween = ChronoUnit.DAYS.between(arrivalDate, departureDate);  //Get days between to pass to daily cost big decimal
        BigDecimal daysBetweenBD = new BigDecimal(daysBetween);

        siteString.append(String.format("%-9s %-13s %-13s %-13s %-13s %s", "Site No.", "Max Occup.", "Accessible?", "Max RV Length", "Utility" ,"Cost" +"\n"));

        for (Site aSiteArray : siteArray) {
            siteNumber = aSiteArray.getSiteNumber().toString();
            occupancy = aSiteArray.getMaxOccupancy().toString();
            accessible = aSiteArray.isAccessibleToString(aSiteArray.isAccessible());
            maxRVLength = aSiteArray.maxRVToString(aSiteArray.getMaxRvLength());
            utility = aSiteArray.isUtilitiesToString(aSiteArray.isUtilities());
            cost = NumberFormat.getCurrencyInstance().format((campground.getDailyFee().multiply(daysBetweenBD)));

            siteString.append(String.format("%-9s %-13s %-13s %-13s %-13s %s", siteNumber, occupancy,
                    accessible, maxRVLength,
                    utility, cost + "\n"));
        }
        System.out.println(siteString);
    }

}