# Car Pooling Application

## Overview

The Car Pooling Application connects car owners who have available seats in their vehicles with customers who need a ride. Car owners can create journeys, and customers can join these journeys based on their starting and ending points. The system handles proximity-based pickup and drop-off, payment calculations, and notifications.

## Actors

### Car Owner

- Can create journeys.
- Can update the status of their journey.
- Can update available seats.
- Can see and accept ride requests.

### Customer

- Can search for available journeys based on their starting and ending points.
- Can request to join a journey.
- Can receive notifications about nearby journeys.
- Can make payments based on distance traveled.

## Functional Requirements

### 1. User Management

#### Registration

- Both car owners and customers can register.
- Required information includes name, email, password, and role (car owner or customer).

#### Login

- Users can log in using their email and password.
- JWT-based authentication is preferred for secure access.

#### Profile Management

- Users can update their profile details.
- Car owners can update car-related details (e.g., car model, license plate).

### 2. Journey Management

#### Create Journey

- Car owners can create journeys specifying:
   - Starting point
   - Ending point
   - Number of available seats
   - Journey status (not started, in progress, completed)

#### Update Journey

- Car owners can update:
   - Journey status
   - Available seats

#### View Journey

- Users can view details of a journey.

### 3. Ride Management

#### Request Ride

- Customers can request to join a journey specifying:
   - Starting point
   - Ending point
   - Desired travel time

#### Join Journey

- Customers can join a journey if:
   - There are available seats.
   - The journey’s route is within 5 km of the customer’s starting and ending points.

#### Update Ride Status

- Car owners can accept or reject ride requests.
- Customers can update their ride requests.

### 4. Proximity Handling

#### Pickup and Drop-off

- The system determines if the customer’s starting and ending points are within 5 km of the journey’s route.
- Car owners will be notified of such requests to facilitate pickup or drop-off.

### 5. Notification System

#### Journey Notifications

- Customers receive notifications if a journey starts within 5 km of their location and matches their request.

### 6. Payment Management

#### Fare Calculation

- Calculate fare based on distance traveled (per kilometer).
- Car owners can set a rate per kilometer.

#### Payment Processing

- Customers will pay the calculated fare at the end of the journey.

## Non-Functional Requirements

### 1. Performance

- The system should handle concurrent journey and ride requests efficiently.
- Notifications should be sent in real-time.

### 2. Security

- User data must be securely stored and protected.
- Authentication and authorization should be handled securely using JWT or similar mechanisms.

### 3. Usability

- The system should have a user-friendly interface for both car owners and customers.
- The system should provide clear instructions and feedback to users.

## Technologies

- **Backend:** Java, Spring Boot
- **Frontend:** HTML/CSS, JavaScript
- **Database:** PostgreSQL (development and production), H2 (unit tests)
- **Authentication:** JWT
- **Notifications:** Real-time notification system
- **Payment:** Fare calculation and processing

## Setup and Installation

1. **Clone the Repository:**

    ```bash
    git clone https://github.com/your-repository/car-pooling-application.git
    ```

2. **Navigate to the Project Directory:**

    ```bash
    cd car-pooling-application
    ```

3. **Build the Project:**

    ```bash
    mvn clean install
    ```

4. **Run the Application:**

    ```bash
    mvn spring-boot:run
    ```

5. **Access the Application:**

   - Open your browser and go to `http://localhost:8080`

## Contributing

1. **Fork the Repository**
2. **Create a New Branch:**

    ```bash
    git checkout -b feature/your-feature
    ```

3. **Commit Your Changes:**

    ```bash
    git commit -m "Add a descriptive message about your changes"
    ```

4. **Push to the Branch:**

    ```bash
    git push origin feature/your-feature
    ```

5. **Open a Pull Request**

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Contact

For any questions or issues, please contact [your-email@example.com](mailto:your-email@example.com).
