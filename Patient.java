import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
public class Patient extends User {
    private final int patientID;
    private final int age;
    private final String gender;
    private final String phoneNumber;
    private Doctor assignedDoctor;
    private static int countPatients = 0;
    public ArrayList<Appointment> appointments = new ArrayList<>();
    public static ArrayList<Patient> patients = new ArrayList<>();
    Scanner input = new Scanner(System.in);
    
    public Patient(String name, String username, String password,String phoneNumber, int age, String gender, Doctor assignedDoctor) {
        super(name, username, password);
        this.phoneNumber = phoneNumber;
        this.age = age;
        this.gender = gender;
        this.assignedDoctor = assignedDoctor;
        countPatients++;
        this.patientID = countPatients;
    }
    
    public static void addPatient(String name, String username, String password,String phoneNumber, int age, String gender, Doctor assignedDoctor) {
        if(name.isEmpty()||username.isEmpty()||password.isEmpty()||phoneNumber.isEmpty()||age == 0||gender.isEmpty()) {
            System.out.println("Invalid input");
            return;
        }
        for(Patient existing : patients) {
            if(existing.getUsername().equals(username)) {
                System.out.println("Username already exists.");
                return;
            }
        }
        Patient p = new Patient(name, username, password, phoneNumber, age, gender, assignedDoctor);
        patients.add(p);
    }
    @Override
    public void displayInfo() {
        String id = User.formatPatientID(this);
        System.out.println("ID: " + id + ", Name: " + name + ", Age: " + age + ", Gender: " + gender + ", Phone number: " + phoneNumber);
    }
    public void viewAssignedDoctor() {
        if(assignedDoctor == null) {
            System.out.println("No doctor assigned yet.");
            return;
        }
        System.out.println("Assigned doctor: " + assignedDoctor.name + ", phoneNumber: " + assignedDoctor.getPhoneNumber() + ", Department: " + assignedDoctor.getDepartment() + ", Specialization: " + assignedDoctor.getSpecialization());
    }
    public int getPatientID() {
        return patientID;
    }
    public int getAge() {
        return age;
    }
    public String getGender() {
        return gender;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public Doctor getAssignedDoctor() {
        return assignedDoctor;
    }
    public void setAssignedDoctor(Doctor assignedDoctor) {
        this.assignedDoctor = assignedDoctor;
    }   
    public static int getCountPatients() {
        return countPatients;
    }
    public void bookAppointments() {
        if(assignedDoctor == null) {
                System.out.println("No doctor assigned yet.");
                return;
            }
        System.out.println("Book an Appointments");
        System.out.print("Enter the date: ");
        String date = input.next().trim();
        System.out.print("Enter the time: ");
        String time = input.next().trim();
        boolean validation = Appointment.checkTimeAndDate(date, time) && Appointment.isValidateTime(date, time) && Appointment.checkDateFormat(date) && Appointment.checkTimeFormat(time);
        if(validation) {
            Appointment newAppointment = new Appointment(this.patientID,this.assignedDoctor.getDoctorID(),date,time,Status.CONFIRMED);
            Appointment.appointments.add(newAppointment);
            appointments.add(newAppointment);
            assignedDoctor.myAppointments.add(newAppointment);
        }
        else
            System.out.println("Date or time is invalid");
    }
    public void viewMyAppointments() {
        for(Appointment a: appointments) {
            String ap = User.formatAppointmentID(a);
            String pa = User.formatPatientID(a);
            String dc = User.formatDoctorID(a);
            System.out.println("Appointments: " + ap  + ", PatientID: " + pa + ", DoctorID: " + dc  + ", Date: " + a.getDate() + ", Time: " + a.getTime() + ", Status: " + a.getStatus());
        }
    }
    public void cancelAppointment() {
        System.out.print("Enter the Appointment ID: ");
        int ap = input.nextInt();
        for(Appointment a: appointments) {
            if(a.getAppointmentID()==ap) {
                if(null == a.getStatus()) {
                    a.setStatus(Status.CANCELLED);
                    System.out.println("Appointment cancelled successfully.");
                } else switch (a.getStatus()) {
                    case COMPLETED -> System.out.println("Error: You cannot cancel an already complete appointment.");
                    case CANCELLED -> System.out.println("Appointment is already cancelled.");
                    default -> {
                        a.setStatus(Status.CANCELLED);
                        System.out.println("Appointment cancelled successfully.");
                    }
                }
                
            }
                
        }
    }
    @Override
    public void showMenu() {
        while(true) {
            System.out.println("1. View My Profile");
            System.out.println("2. View Assigned Doctor");
            System.out.println("3. View My Appointments");
            System.out.println("4. Book Appointment");
            System.out.println("5. Cancel Appointment");
            System.out.println("6. Logout");
            try {
            int op = input.nextInt();
            switch(op) {
                case 1 -> this.displayInfo();
                case 2 -> this.viewAssignedDoctor();
                case 3 -> this.viewMyAppointments();
                case 4 -> this.bookAppointments();
                case 5 -> this.cancelAppointment();
                case 6 -> {
                    return;
                }
                default -> System.out.println("Invalid option");
            }

            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a valid number from the menu.");
                input.nextLine();
            }
            
        }
    }
    
}