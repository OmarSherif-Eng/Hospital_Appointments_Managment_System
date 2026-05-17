import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Doctor extends User {
    Scanner input = new Scanner(System.in);
    private final int doctorID;
    private final String specialization;
    private final String department;
    private static int countDoctors = 0;
    private final String phoneNumber;
    public ArrayList<Patient> assignedPatients = new ArrayList<>();
    public ArrayList<Appointment> myAppointments = new ArrayList<>();
    public static ArrayList<Doctor> doctors = new ArrayList<>();
    public Doctor(String name, String username, String phoneNumber,String password, String specialization, String department) {
        super(name , username ,password);
        this.specialization = specialization;
        this.department = department;
        this.phoneNumber = phoneNumber;
        countDoctors++;
        doctorID = countDoctors;
    }

    public int getDoctorID() {
        return doctorID;
    }

    public String getSpecialization() {
        return specialization;
    }

    public String getDepartment() {
        return department;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }

    @Override
    public void displayInfo() {
        String id = User.formatDoctorID(this);
        System.out.print("Doctor ID: "+ id +", ");
        System.out.print("Name: "+name+", ");
        System.out.print("Username: "+username+", ");
        System.out.print("Specialization: "+specialization+", ");
        System.out.print("Department: "+department+", ");
        System.out.println("Phone number: "+phoneNumber);
    }

    @Override
    public void showMenu(){
    while (true){
        System.out.println("1.View personal information");
        System.out.println("2.View assigned patients");
        System.out.println("3.View appointments");
        System.out.println("4.Update appointment status");
        System.out.println("5.Logout");
        try {
            int Doctor = input.nextInt();
            switch (Doctor) {
                case 1 -> displayInfo();

                case 2 -> {
                    if(assignedPatients.isEmpty()) {
                        System.out.println("There is no assigned patients for you");
                    } else {
                        for (Patient p : assignedPatients) {
                            String id = User.formatPatientID(p);
                            System.out.print("ID: " + id + ", ");
                            System.out.print("Name: " + p.getName()+", ");
                            System.out.println("Age: " + p.getAge());
                        }
                    }
            }

                case 3 -> {
                    if(myAppointments.isEmpty()) {
                        System.out.println("There is no appointments");
                    } else {
                        for (Appointment a : myAppointments) {
                            String appId = User.formatAppointmentID(a);
                            String docId = User.formatDoctorID(this);
                            String PatientId = User.formatPatientID(a);
                            System.out.print("Time: "+a.getTime()+", ");
                            System.out.print("Date: "+a.getDate()+", ");
                            System.out.print("Status: "+a.getStatus()+", ");
                            System.out.print("Appointment ID: "+ appId +", ");
                            System.out.print("Doctor ID: "+docId+", ");
                            System.out.println("Patient ID: "+ PatientId);
                        }
                    }
            }
                    
                case 4 -> {
                    System.out.print("Enter Appointment ID: ");
                    int id = input.nextInt();

                    System.out.println("Choose new status:");
                    Status newStatus = null;
                    while(newStatus == null) {
                    System.out.println("1. Confirmed");
                    System.out.println("2. Completed");
                    System.out.println("3. Cancelled");

                    int choice = input.nextInt();
                        switch (choice) {
                        case 1 -> newStatus = Status.CONFIRMED;
                        case 2 -> newStatus = Status.COMPLETED;
                        case 3 -> newStatus = Status.CANCELLED;
                        default -> System.out.println("Invalid choice, try again: ");
                    }
                }
                    

                    boolean found = false;

                    for (Appointment a : myAppointments) {
                        if (a.getAppointmentID()==id) {
                            found = true;
                            boolean isCancelledValid = a.validateCancelledStatus(newStatus);
                            if(isCancelledValid)
                                a.setStatus(newStatus);
                            break;
                        }
                    }
                    if (!found) {
                        System.out.println("Appointment not found!");
                        
                    }
                    
            }


                case 5 -> {
                    return;
            }
                default -> System.out.println("Invalid choice");
            }
        } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a valid number from the menu.");
                input.nextLine();
            }
        
        }
    
    }
    
    
        
        
    
}