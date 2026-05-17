# Hospital Appointment Management System

A robust, console-based **Hospital Appointment Management System** built in Java using Object-Oriented Programming (OOP) principles. The system provides a structured workflow for handling interactions between **Admins**, **Doctors**, and **Patients**, featuring data persistence and type-safe state management.

## 🚀 Core Features & Roles

### 1. Admin Capabilities
- **Staff & Patient Management:** Register new patients and onboard doctors into the system.
- **Doctor Assignment:** Formally assign patients to specialized doctors.
- **Appointment Scheduling:** Create and log official appointments between patients and their assigned doctors.
- **Reporting Analytics:** Generate real-time system metrics, including breakdown of appointment statuses and showcasing the top 3 doctors by volume.

### 2. Doctor Capabilities
- **Patient Rosters:** View lists of all naturally assigned patients.
- **Appointment Logs:** Manage personal scheduling and check upcoming appointments.
- **Status Progression:** Update appointment states utilizing rigorous business logic rules.

### 3. Patient Capabilities
- **Profile Self-Service:** View personal information and check assigned medical staff.
- **Booking Engine:** Book or cancel appointments seamlessly while validating against scheduling conflicts.

## 🛠️ Technical Highlights & Architecture

- **Domain-Driven Design (OOP):** Leverages a clean inheritance hierarchy stemming from an abstract `User` class to decouple specialized logic for `Admin`, `Doctor`, and `Patient`.
- **Type-Safe State Management (`enum`):** Implements a dedicated `Status` Enum (`CONFIRMED`, `COMPLETED`, `CANCELLED`) for appointments. This guarantees type safety, eliminates string-typo vulnerabilities, and centralizes validation transitions.
- **File I/O Data Persistence:** Handles custom parsing, loading, and serializing data blocks across multiple text entities (`doctors.txt`, `patients.txt`, `appointments.txt`, `users.txt`) dynamically without dependency on external databases.
- **Defensive Programming:** Packed with conditional checks (e.g., stopping non-assigned patients from booking, or preventing `CANCELLED` appointments from transitioning to `COMPLETED`) to prevent runtime crashes and `NullPointerExceptions`.

## 📂 Project Structure
- `Main.java`: The core application entry point coordinating the main loop and shared `Scanner` context.
- `HospitalSystem.java`: Act as a central orchestrator facilitating credential indexing and global mutations.
- `User.java`: Abstract base model encapsulating core credentials and ID formatting procedures.
- `Admin.java` / `Doctor.java` / `Patient.java`: Role-specific models containing custom menus and entity behavior.
- `Appointment.java`: Manages schedule boundaries, double-booking checks, and state transitions.
- `Status.java`: The Type-safe enum configuration for scheduling validation.
- `FileManager.java`: Low-level stream handler managing robust disk serialization.

## 👥 Contributors & Development Team
This project was designed and engineered collaboratively by a team of 5 developers:

* **Omar Sherif** - *FileManager class - Appointment class* - [GitHub Profile](https://github.com/OmarSherif-Eng)
* **Abdallah Fathy** - *User base class - HospitalSystem class- Main class* - [GitHub Profile](https://github.com/abdallah-fathy07)
* **Abdallah Emara** - *Patient class* - [GitHub Profile](https://github.com/Abdullah2-0-0-7)
* **Ahmed Alaa** - *Admin class* - [GitHub Profile] -> not provided.
* **Abdelrahman Hesham** - *Doctor class* - [GitHub Profile] -> not provided.
