# Cinema Room Manager (Java)

Console-based ticket booking system developed as part of the HyperSkill Java Developer track. This project simulates a cinema's backend, handling seating arrangements, ticket pricing, and sales statistics.

## Features

- **Dynamic Seating Map:** Generates a visual representation of the cinema based on user-defined dimensions.
- **Smart Pricing:** Automatically calculates ticket prices ($10 for front rows, $8 for back rows) based on cinema size and seat location.
- **Statistics Dashboard:** Tracks the number of purchased tickets, occupancy percentage, and both current and potential total income.
- **Robust Input Validation:** - Custom Regex-based validator to prevent crashes from non-integer inputs.
  - Guard clauses to handle out-of-bounds coordinates and double-booking attempts.

## Technologies Used

- **Java 21+**
- **Regex** for input sanitization.
- **Guard Clauses** for clean, "fail-fast" logic flow.
- **JavaDoc** for method documentation.

## How to Run

1. **Clone the repository:**
   ```bash
   git clone [https://github.com/StanIvanov90/HyperSkill-Cinema-Room-Manager.git](https://github.com/StanIvanov90/HyperSkill-Cinema-Room-Manager.git)


## Example Usage

Enter the number of rows:
> 9
Enter the number of seats in each row:
> 9

1. Show the seats
2. Buy a ticket
3. Statistics
0. Exit
> 2

Enter a row number:
> 5
Enter a seat number in that row:
> 5
Ticket price: $8