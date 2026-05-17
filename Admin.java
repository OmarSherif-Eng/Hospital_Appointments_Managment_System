import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Admin extends User {
    Scanner input = new Scanner(System.in);
    
    
    public Admin(String name, String username, String password) {
        super(name , username , password);
    }
    
    @Override
    public void displayInfo() {
        System.out.println("===== Admin Info =====");
        System.out.println("Name : " + name);
        System.out.println("Username : " + username);       
    }
    
    @Override
    public void showMenu() {
        boolean running = true;
        while(running) {
            System.out.println("1. Add Doctor");
            System.out.println("2. Register Patient");
            System.out.println("3. Assign Patient to Doctor");
            System.out.println("4. Create Appointment");
            System.out.println("5. View All Doctors");
            System.out.println("6. View All Patients");
            System.out.println("7. View All Appointments");
            System.out.println("8. Search Patient by ID");
            System.out.println("9. Search Doctor by ID");
            System.out.println("10. Generate Reports");
            System.out.println("11. Save Data");
            System.out.println("12. Logout");
            try {
                int choice = input.nextInt();
            
            switch(choice) {
                case 1 -> addDoctor();
                
                case 2 -> registerPatient();
                    
                case 3 -> assignPatientToDoctor();
                    
                case 4 -> createAppointment();
                    
                case 5 -> {
                    System.out.println("All Doctors");
                    viewAllDoctors();
                }
                    
                case 6 -> {
                    System.out.println("All Patients");
                    viewAllPatients();
                }
                    
                case 7 -> {
                    System.out.println("All Appointments");
                    viewAllAppointments();
                }
                    
                case 8 -> {
                    System.out.println("Enter the patient id : ");
                    searchPatientById();
                }
                    
                case 9 -> {    
                    System.out.println("Enter the doctor id : ");
                    searchDoctorById();
                }
                    
                case 10 -> {
                    System.out.println("Hospital Report");
                    System.out.println("Total Doctors  : " + Doctor.doctors.size());
                    System.out.println("Total Patients : " + Patient.patients.size());
                    int confirmed = 0, completed = 0, cancelled = 0;
                    for (Appointment a : Appointment.appointments) {
                        if (null!= a.getStatus())
                            switch (a.getStatus()) {
                            case CONFIRMED -> confirmed++;
                            case COMPLETED -> completed++;
                            case CANCELLED -> cancelled++;
                            default -> {
                            }
                        }
                    }
                    System.out.println("Total Appointments : " + Appointment.appointments.size());
                    System.out.println("  Confirmed  : " + confirmed);
                    System.out.println("  Completed  : " + completed);
                    System.out.println("  Cancelled  : " + cancelled);
                    System.out.println("Top 3 Doctors");
                    ArrayList<Doctor> sortedDoctors = new ArrayList<>(Doctor.doctors);
                    sortedDoctors.sort((a, b) -> b.myAppointments.size() - a.myAppointments.size());
                    int rank = 1;
                    for (int i = 0; i < Math.min(3, sortedDoctors.size()); i++) {
                        Doctor topDoc = sortedDoctors.get(i);
                        System.out.println(rank + ". " + topDoc.getName() +
                                " | Appointments: " + topDoc.myAppointments.size());
                        rank++;
                    }
                }
                    
                case 11 -> {
                    FileManager.saveDoctors(Doctor.doctors);
                    FileManager.savePatients(Patient.patients);
                    FileManager.saveAppointments(Appointment.appointments);
                    FileManager.saveUsers(Doctor.doctors, Patient.patients);
                    System.out.println("Data Saved.");
                }
                    
                case 12 -> { 
                    running = false;
                    System.out.println("Logging out.");
                }
                    
                default -> System.out.println("Invalid choice.");
            
            }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a valid number from the menu.");
                input.nextLine();
            }
                                   
        }
    }
    
        public void addDoctor() {
        input.nextLine();
        System.out.println("Add Doctor : ");
        System.out.println("Enter the doctor name : ");
        String docName = input.nextLine();
        System.out.println("Enter the doctor specialization : ");
        String specialization = input.nextLine();
        System.out.println("Enter the doctor department : ");
        String department = input.nextLine();
        System.out.println("Enter the phone number : ");
        String phoneNumber = input.next();
        System.out.println("Enter the doctor username : ");
        String userName = input.next();
        System.out.println("Enter the doctor password : ");
        String docPassword = input.next();
        
        for(Doctor d : Doctor.doctors) {
            if(d.getUsername().equals(userName)) {
                System.out.println("This doctor username already exists.");
                return;
            }
        }
        
        Doctor newDoctor = new Doctor(docName,userName,phoneNumber , docPassword,specialization,department);
        Doctor.doctors.add(newDoctor);        
        System.out.println("Doctor added successfully.");
    }
    
    public void registerPatient() {
        input.nextLine();
        System.out.println("Add Patient : ");
        System.out.println("Enter First Name : ");
        String patientName = input.next();
        input.nextLine();
        System.out.println("Enter Username : ");
        String userName = input.nextLine();
        System.out.println("Enter Password : ");
        String patientPassword = input.nextLine();
        System.out.println("Enter Phone Number : ");
        String phoneNumber = input.nextLine();
        int age = 0;
        while (true) { 
            System.out.println("Enter Age : ");
            try {
                age = input.nextInt();
                input.nextLine();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a valid number for age.");
                input.nextLine();
            }
            
        }
        
        
        System.out.println("Enter Gender : ");
        String gender = input.next();
        Patient.addPatient(patientName, userName, patientPassword, phoneNumber, age, gender, null);
        System.out.println("Patient registered.");
    }
    
    public void createAppointment() {
        input.nextLine();
        System.out.println("Create an appointment");
        System.out.println("Enter Patient id : ");
        int patientID = input.nextInt();
        int doctorID;
        try {
            if(Patient.patients.get(patientID-1).getAssignedDoctor()==null)
                throw new NullPointerException("Patient has not assigned doctor");
            doctorID = Patient.patients.get(patientID-1).getAssignedDoctor().getDoctorID();
        }
        catch(NullPointerException e) {
            System.out.println(e.getMessage());
            return;
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Invalid patient ID.");
            return;
        }
        input.nextLine();
        String date, time;
        while (true) { 
          try {
            System.out.println("Enter Date (Year-Month-Day)");
            date = input.nextLine().trim();
            if(!Appointment.checkDateFormat(date))
                throw new Exception("Invalid Date! Please enter date in format (Year-Month-Day) and use \'-\' not \\");
            else break;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }  
        }
        
        while (true) { 
            try {
                System.out.println("Enter Time (HH:MM): ");
                time = input.nextLine().trim(); 
                if(Appointment.checkTimeFormat(time))
                    break;
                else throw new Exception("Invalid Time! Please enter time in format (Hours:Minutes) without spaces.");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        if(!Appointment.isValidateTime(date , time)) {
            return;
        }
        if(Appointment.isDoubleBooked(Appointment.appointments, date, time)) {
            return;
        }
        Appointment newAppointment = new Appointment(patientID, doctorID, date,time, Status.CONFIRMED);
        Appointment.appointments.add(newAppointment);
        Patient.patients.get(patientID-1).appointments.add(newAppointment);
        Patient.patients.get(patientID-1).getAssignedDoctor().myAppointments.add(newAppointment);
        System.out.println("Appointment created.");
    }
        

      public void viewAllDoctors() {
        if(Doctor.doctors.isEmpty()) {
            System.out.println("there is no Doctors.");
        } else {
            for(Doctor d : Doctor.doctors) {
                d.displayInfo();
            }
        }
    }
    
    public void viewAllPatients(){
        if(Patient.patients.isEmpty()) {
            System.out.println("there is no Patients.");
        } else {
            for(Patient p : Patient.patients) {
                p.displayInfo();
            }
        }       
    }
    
    public void searchPatientById() {
        int searchID  = input.nextInt();
        input.nextLine();
        boolean found = false;
        for(Patient p : Patient.patients) {
            if(p.getPatientID() == searchID) {
                p.displayInfo();
                found = true;
                break;
            }
        }

        if(!found) {
            System.out.println("No patient found");
        }
    }
    
    public void searchDoctorById() {
        int searchId = input.nextInt();
        input.nextLine();
        boolean found = false;
        for(Doctor d : Doctor.doctors) {
            if(d.getDoctorID() == searchId) {
                d.displayInfo();
                found = true;
                break;
            }
        }

        if(!found) {
            System.out.println("No doctor found");
        }
    }
    
    public void viewAllAppointments() {
        if(Appointment.appointments.isEmpty())  {
            System.out.println("there is no Appointments.");
        } else {
            for(Appointment appointment : Appointment.appointments) {
                appointment.displayInfo();
            }
        } 
    }
    
    public void assignPatientToDoctor() {
        System.out.println("Assign Patient to Doctor.");
        System.out.println("Enter the patient id : ");
        int patientID = input.nextInt();
        Patient foundPatient = null;
        for (Patient p : Patient.patients) {
            if (p.getPatientID() == patientID) {
                foundPatient = p;
                break;
            }
        }
        if (foundPatient == null) {
            System.out.println("Patient not found.");
            return;
        }
        System.out.println("Enter the doctor id : ");
        int doctorID = input.nextInt();
        
        

        Doctor foundDoctor = null;
        for (Doctor d : Doctor.doctors) {
            if (d.getDoctorID() == doctorID) {
                foundDoctor = d;
                break;
            }
        }

        
        if (foundDoctor == null) {
            System.out.println("Doctor not found.");
            return;
        }
        
        foundPatient.setAssignedDoctor(foundDoctor);
        foundDoctor.assignedPatients.add(foundPatient);
        System.out.println("Patient " + foundPatient.getName() + " assigned to Dr. " + foundDoctor.getName());
    }  

        

        
    }
    
        
