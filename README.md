# Hotel Booking System

## Overview

This project is a Hotel Booking System designed to manage hotel reservations, guest check-ins and check-outs, room cleaning, and booking cancellations. It is implemented in Java and uses Apache Derby as its embedded database.

## Features

- Book rooms for guests
- Cancel bookings
- Check-in and check-out guests
- Clean rooms
- Manage hotel room availability
- Command-based architecture for extensibility

## Project Structure

- `src/main/java/com/mycompany/`: Main Java source code
- `src/test/java/com/mycompany/`: Unit tests
- `lib/`: Contains required libraries (`derby.jar`, `derbyclient.jar`)
- `HotelDB/`: Embedded Derby database files (do not modify manually)
- `target/`: Compiled classes and JAR files
- `nbproject/`: NetBeans project configuration files

## Database

The system uses Apache Derby for data persistence. All database files are located in the `HotelDB/` directory. Do not edit or delete these files directly.

## Building and Running

### Prerequisites

- Java 8 or higher
- Maven

### Build

To build the project, run:

```sh
mvn clean install
```

### Run

To run the application:

```sh
java -jar target/HotelBookingSystem-1.0-SNAPSHOT.jar
```

## Testing

Unit tests are located in `src/test/java/com/mycompany/`. To run tests:

```sh
mvn test
```

## Notes

- Do not modify files in the `HotelDB/` directory unless instructed.
- The system uses a command pattern for handling user actions, making it easy to extend functionality.

## License

This project is for educational purposes.
# 603_ProjectTWO
