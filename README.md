# University Timetable Management System RESTful API

This RESTful API project is developed for managing the timetable system of a university. It facilitates the creation, modification, and querying of class schedules for students, faculty, and administrative staff. Also, this system provides functionality for students to enroll to courses and book resource based on their availability.

## Features
- **User Roles and Authentication:** Supports multiple user roles (Admin, Student) with secure login functionality and session management using JWT.
- **Course Management:** Allows CRUD operations on courses, including assigning faculty to courses.
- **Timetable Management:** Facilitates the creation and modification of timetables for different courses.
- **Location and Resource Booking:** Manages classrooms and resources availability, allowing booking without overlaps.
- **Student Enrollment:** Enables students to enroll in courses and view their timetables.
- **Notifications and Alerts:** Implements a system to notify users of timetable changes and important announcements.

## Technology Stack
- **SpringBoot:** Java framework for building the RESTful API.
- **MongoDB:** NoSQL database for storing and managing timetables, courses, users, and other related data.
- **JWT:** JSON Web Tokens for secure authentication.
- **Postman:** API documentation tool.

## Setup Instructions
1. Clone the GitHub repository.
2. Install dependencies: mvn clean install
3. Set up MongoDB and configure connection settings in application.properties.
4. Start the server: mvn spring-boot:run
5. The API will be available at http://localhost:8080.

## API Documentation
The main API endpoints, descriptions, and status codes are as follows.

| URL               | Description                 | Status            |
|-------------------|-----------------------------|-------------------|
| /api/course/      | Course Management           | 201 CREATED, 200 OK |
| /api/timetable/    | Timetable Management        | 201 CREATED, 200 OK |
| /api/auth/         | Authentication Management   | 201 CREATED, 200 OK |
| /api/enrollment/   | Student Course Enrollment   | 201 CREATED, 200 OK |
| /api/booking/     | Resource Booking            | 201 CREATED, 200 OK |

- API documentation is available via Postman. You can view and interact with the endpoints using `https://documenter.getpostman.com/view/26797450/2sA35Bbjbf`. 
- This documentation provides detailed information about the available endpoints, request parameters, request bodies (if applicable), and response formats. It allows you to easily test and explore the functionality of the API.

## Running Tests
### Unit Testing
1. Run unit tests: `npm test`
2. This will execute unit tests for individual components and functions.

### Integration Testing
1. Integration tests are located in the `tests/` directory.
2. Run integration tests.

### Security Testing
1. Spring Security is a framework that provides authentication, authorization, and other security features.
2. It helps to implement authentication providers, access control, and protection against common vulnerabilities like CSRF and XSS.

## Sample ER Diagram

![AF_ER_Diagram drawio](https://github.com/sliitcsse/assignment-01-YasiraBanuka/assets/111946114/a101236e-bbb7-4e68-99f8-23afeb0684ac)
