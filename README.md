# Cine2k: Cinema Management Application

## Overview

Cine2k is a Java-based desktop application designed to manage a cinema's operations, including films, screening rooms (salles), screenings (séances), and reservations. It's built using Swing for the graphical user interface and implements the Model-View-Controller (MVC) pattern along with the Observer pattern for effective data management and UI updates.

## Project Structure

The project is organized into the following key packages:

*   **`com.rmoss.model`:** Contains the data model and service classes.
    *   `Film.java`: Represents a film with attributes like title, director, genre, duration, release date, and classification.
    *   `Salle.java`: Represents a screening room with attributes like name, capacity, and room type.
    *   `Seance.java`: Represents a screening session with attributes like the film, room, date/time, version, projection type, and price.
    *   `Reservation.java`: Represents a reservation with details like the screening, client's name, number of seats, tariff type, and reservation date.
    *   `FilmService.java`: Manages the data and operations related to films.
    *   `SalleService.java`: Manages the data and operations related to screening rooms.
    *   `SeanceService.java`: Manages the data and operations related to screenings.
    *   `ReservationService.java`: Manages the data and operations related to reservations.
    * `FilmServiceObserver.java`: Interface to implement the Observer Pattern, notify the views about changes in `FilmService`
    * `SalleServiceObserver.java`: Interface to implement the Observer Pattern, notify the views about changes in `SalleService`
*   **`com.rmoss.view`:** Contains the graphical user interface (GUI) components.
    *   `MainFrame.java`: The main application window, responsible for managing the different screens and the menu.
    *   `EcranGestionFilms.java`: Manages the UI for the Film List and operations.
    *   `EcranGestionSalles.java`: Manages the UI for the Salle List and operations.
    *   `EcranGestionSeances.java`: Manages the UI for the Seance List and operations.
    *   `EcranReservations.java`: Manages the UI for the Reservations List and operations.
    * `FormulaireSeance`: Form component to handle `Seance` elements.
*   **`com.rmoss`:** The main package, containing the `MainFrame` class.
    *   `MainFrame.java`: Initializes the application, loads the data, sets up the CardLayout, and manages the main menu.

## Key Concepts and Notions

This project showcases several important software design concepts:

### 1. Model-View-Controller (MVC)

*   **Model:** The `com.rmoss.model` package contains the data and logic (services) of the application. It's independent of the user interface.
    *   **Entities (POJOs):** `Film`, `Salle`, `Seance`, `Reservation` represent the core data structures.
    *   **Services:** `FilmService`, `SalleService`, `SeanceService`, `ReservationService` provide the business logic for interacting with the entities (e.g., add, modify, delete, search).
*   **View:** The `com.rmoss.view` package contains the graphical user interface components. They display information from the model and allow user interaction.
    *   **Screens:** `EcranGestionFilms`, `EcranGestionSalles`, `EcranGestionSeances`, `EcranReservations` are the primary views.
    * **Forms** `FormulaireSeance`
*   **Controller:** The `MainFrame` and the various screen classes act as controllers. They handle user input from the view, interact with the services in the model, and update the view accordingly.

### 2. Observer Pattern

*   **Purpose:** To allow multiple parts of the application (the views) to be notified automatically when the model changes.
*   **Implementation:**
    *   **Observer Interfaces:** `FilmServiceObserver`, `SalleServiceObserver` define the `onFilmsChanged()` and `onSallesChanged()` methods.
    *   **Observable Services:** `FilmService` and `SalleService` maintain a list of observers, and call their notification methods (`notifyObservers`) when their data is changed.
    *   **Observing Views:** `FormulaireSeance` implement `FilmServiceObserver`, `SalleServiceObserver`. They register as observers with the services.
*   **Benefits:** Decoupling, automatic UI updates, and code maintainability.

### 3. Data Management (Services)

*   **Services:** `FilmService`, `SalleService`, `SeanceService`, and `ReservationService` handle data management and business logic.
*   **Data Storage:** Currently, the data is stored in memory using `ArrayList`s. In a real-world application, this would be replaced with a database (e.g., using JPA or JDBC).
*   **Unique Identifiers:** UUIDs (Universally Unique Identifiers) are used to generate unique identifiers for each entity (`filmId`, `salleId`, etc.).

### 4. Swing (GUI)

*   **`JFrame`:** The main application window (`MainFrame`).
*   **`JMenuBar`:** The menu bar at the top (`menuGestion`).
*   **`JMenuItem`:** Menu items (`menuFilms`, `menuSalles`, etc.).
*   **`JPanel`:** Used as containers for organizing UI elements.
*   **`CardLayout`:** A layout manager used in `MainFrame` to switch between different screens.
*   **`JComboBox`:** Drop-down lists to select films, rooms, etc.
*   **`JTextField`:** Text input fields.
* **`GridBagLayout`**: Layout Manager to organize the components in the `FormulaireSeance`

### 5. Java Concepts

*   **Object-Oriented Programming (OOP):** Encapsulation, inheritance, polymorphism are used throughout the project.
*   **Collections:** `ArrayList` is used for storing lists of films, rooms, screenings, etc.
*   **Date/Time:** `java.time.LocalDateTime` and `java.util.Date` are used for date and time management.
* **Interfaces**: `FilmServiceObserver` and `SalleServiceObserver` are interfaces that define the methods needed for views to react to changes.

## Definitions

*   **Film:** A movie that is shown in the cinema.
    *   **Attributes:** Title, director, genre, duration, release date, classification.
*   **Salle (Room):** A room in the cinema where films are screened.
    *   **Attributes:** Name, capacity, room type.
*   **Seance (Screening):** A specific showing of a film in a specific room at a specific date and time.
    *   **Attributes:** Film, room, date/time, version (VF/VOST), projection type, price.
*   **Reservation:** A booking for one or more seats for a specific screening.
    *   **Attributes:** Screening, client's name, number of seats, tariff type, reservation date.
* **Service**: A class that handles the operations, and business logic of the different element, like films, salle or seance.
* **Observer**: A class that reacts to changes in the data.
* **Observable**: A class that send a notification when a change happens.

## How to Run the Application

1.  **Prerequisites:**
    *   Java Development Kit (JDK) 8 or higher.
2.  **Compilation:**
    *   Navigate to the project's root directory (where the `src` folder is) in your terminal.
    *   Compile the code: `javac -d out src/main/java/com/rmoss/**/*.java`
3.  **Execution:**
    *   Run the `MainFrame` class: `java -cp out com.rmoss.MainFrame`

## Future Improvements

*   **Database Integration:** Replace the in-memory data storage with a persistent database (e.g., MySQL, PostgreSQL, or H2).
*   **Error Handling:** Add more robust error handling (e.g., exceptions).
*   **Input Validation:** Implement input validation to ensure data integrity.
*   **More Features:** Add features like seat selection, payment processing, user accounts, etc.
*   **More Views**: Add a view to manage `Reservations`, `Seances`, `Films` and `Salles`.
* **Unit and Integration Tests**: Create unit and integration tests to have a robust application.
* **Deployment**: Create a `.jar` or an executable to have a standalone application.

## Author

*   Radnoumane Mossabely