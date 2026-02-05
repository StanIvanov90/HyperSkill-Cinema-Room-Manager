package cinema;

import java.util.Objects;
import java.util.Scanner;

public class Cinema {
    public static final int OFFSET = 1;
    public static final int SMALL_CINEMA_SIZE = 60;
    public static final int FRONT_BACK_DIVISOR = 2;
    public static final int SHOW_SEATS = 1;
    public static final int BUY_TICKET = 2;
    public static final int SHOW_STATISTICS = 3;
    public static final int SMALL_TICKET_PRICE = 8;
    public static final int BIG_TICKET_PRICE = 10;
    public static final int HUNDRED_PERCENT = 100;
    public static final int INCORRECT_INPUT = -1;
    public static int numberOfPurchasedTickets = 0;
    public static int currentIncome = 0;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // Prompt input and read the rows and seats of the cinema
        System.out.println("Enter the number of rows:");
        int rows = getValidInteger(scanner);
        System.out.println("Enter the number of seats in each row:");
        int seats = getValidInteger(scanner);

        // Initialize cinema 2D array. Adding + 1 for rows and cols in for printing purposes
        String[][] cinema = new String[rows + OFFSET][seats + OFFSET];

        //Populate cinema 2D array
        createCinemaLayout(cinema);

        //Asking for input
        while (true) {
            showMenu();
            int menuSelection = getValidInteger(scanner);
            if (menuSelection == SHOW_SEATS) {
                // Print cinema 2D array
                printCinema(cinema);
            } else if (menuSelection == BUY_TICKET) {
                boolean isPurchaseSuccessful = false;
                while (!isPurchaseSuccessful) {
                    System.out.println("Enter a row number:");
                    int selectedRow = getValidInteger(scanner);
                    System.out.println("Enter a seat number in that row:");
                    int selectedSeat = getValidInteger(scanner);
                    int price = buyTicket(cinema, selectedRow, selectedSeat);
                    if (price != INCORRECT_INPUT) {
                        System.out.printf("Ticket price: $%d", price);
                        isPurchaseSuccessful = true;

                    }
                }
            } else if (menuSelection == SHOW_STATISTICS) {
                double occupancyPercentage = calculateOccupancyPercentage(numberOfPurchasedTickets, rows, seats);
                int totalIncome = calculateTotalIncome(isCinemaBig(rows, seats), rows, seats);
                String output = "Number of purchased tickets: %d%nPercentage: %.2f%%%nCurrent income: $%d%nTotal income: $%d".formatted(numberOfPurchasedTickets, occupancyPercentage, currentIncome, totalIncome);
                System.out.println(output);

            } else {
                return;
            }

        }

    }

    /**
     * Populates a 2D array with values that would represent cinema rows and seats
     *
     * @param cinema String 2D array
     */
    public static void createCinemaLayout(String[][] cinema) {
        for (int i = 0; i < cinema.length; i++) {
            for (int j = 0; j < cinema[0].length; j++) {
                if ((i == 0) && (j == 0)) {
                    cinema[i][j] = " ";
                } else if (i == 0) {
                    cinema[i][j] = Integer.toString(j);
                } else if (j == 0) {
                    cinema[i][j] = Integer.toString(i);
                } else {
                    cinema[i][j] = "S";
                }
            }
        }
    }

    /**
     * Print cinema seats
     *
     * @param cinema 2D array that represents a cinema
     */
    public static void printCinema(String[][] cinema) {
        System.out.println("Cinema:");
        int cinemaSeats = cinema[0].length;
        for (String[] strings : cinema) {
            for (int j = 0; j < cinemaSeats; j++) {
                System.out.print(strings[j] + " ");
            }
            System.out.println();
        }
    }

    /**
     * Checks if the Cinema has more than 60 seats
     *
     * @param cinemaRows  total number of rows available for booking
     * @param cinemaSeats total number of seats per row available for booking
     * @return boolean value representing if the Cinema is bigger than 60 seats
     */
    public static boolean isCinemaBig(int cinemaRows, int cinemaSeats) {
        return (cinemaRows * cinemaSeats) > SMALL_CINEMA_SIZE;
    }

    /**
     * Prints the ticket price per chosen seat
     *
     * @param isCinemaBig boolean value if cinema has more than 60 seats
     * @param cinemaRows  total number of rows available for booking
     * @param selectedRow number of the selected row
     * @return calculated ticket price
     */
    public static int calculateTicketPrice(boolean isCinemaBig, int cinemaRows, int selectedRow) {
        int cinemaHalf = (cinemaRows) / FRONT_BACK_DIVISOR;
        int price = BIG_TICKET_PRICE;
        if (isCinemaBig && (selectedRow > cinemaHalf)) {
            price = SMALL_TICKET_PRICE;
        }
        return price;
    }

    /**
     * Prints menu with input directions
     */
    public static void showMenu() {
        System.out.println();
        System.out.println("1. Show the seats");
        System.out.println("2. Buy a ticket");
        System.out.println("3. Statistics");
        System.out.println("0. Exit");
    }

    /**
     *  Simulates buying a ticket from a cinema.
     *  If the input is correct, the methods assigns "B" value to the specified 2D array index
     *
     * @param cinema 2D array that represents a cinema
     * @param row int that represents the selected row
     * @param seat int that represents selected seat
     * @return the ticket price, which is being used as a flag in a while loop
     */
    public static int buyTicket(String[][] cinema, int row, int seat) {
        if (seat < 1 || row < 1 || row > cinema.length - OFFSET || seat > cinema[row].length - OFFSET) {
            System.out.println("Wrong input");
            return INCORRECT_INPUT;
        }
        if (Objects.equals(cinema[row][seat], "B")) {
            System.out.println("That ticket has already been purchased!");
            return INCORRECT_INPUT;
        }
        cinema[row][seat] = "B";
        // Subtract the header row/column to get actual counts
        int actualRows = cinema.length - OFFSET;
        int actualSeats = cinema[0].length - OFFSET;
        boolean isCinemaBig = isCinemaBig(actualRows,actualSeats);
        int calculateTicketPrice = calculateTicketPrice(isCinemaBig,actualRows,row);
        numberOfPurchasedTickets++;
        currentIncome += calculateTicketPrice;

        return calculateTicketPrice;
    }

    /**
     *  Calculates the possible total income based on the rows and seats
     *
     * @param isCinemaBig boolean param to indicate if a more special calculation is required
     * @param rows total number of rows available for booking
     * @param seats total number of seats per row available for booking
     * @return  total possible income
     */
    public static int calculateTotalIncome(boolean isCinemaBig, int rows, int seats) {
        int possibleIncome = rows * seats * BIG_TICKET_PRICE;
        if (isCinemaBig) {
            int frontRows = rows / FRONT_BACK_DIVISOR;
            int backRows = rows - frontRows;
            possibleIncome = (frontRows * seats * BIG_TICKET_PRICE) + (backRows * seats * SMALL_TICKET_PRICE);
        }

        return possibleIncome;

    }

    /**
     * Calculates the number of purchased tickets represented as a percentage.
     * *
     * @param numberOfPurchasedTickets int that represents
     * @param rows total number of rows available for booking
     * @param seats total number of seats per row available for booking
     *
     * @return double value for purchased tickets represented as percentage
     */
    public static double calculateOccupancyPercentage(int numberOfPurchasedTickets, int rows, int seats) {
        double totalSeats = rows * seats;
        return (numberOfPurchasedTickets / totalSeats) * HUNDRED_PERCENT;
    }

    /**
     * Reads input prompted from a scanner and validates that it is a positive integer.
     * The method will prompt the user for an input until a String that contains only numbers is provided
     *
     * @param scanner the Scanner object used to read user input
     * @return the validated integer provided by the user
     */
    public static int getValidInteger(Scanner scanner) {
        while (true) {
            String input = scanner.next();
            if (input.matches("\\d+")) {
                return Integer.parseInt(input);
            } else {
                System.out.println("Error! Please enter a valid number (e.g., 5):");
            }
        }
    }
}