import java.util.ArrayList;



public class Appointment {
    private final int patientID;
    private final int doctorID;
    private final int appointmentID;
    private String date;
    private String time;
    private Status appointmentStatus;
    private static int countAppointment;
    public static ArrayList<Appointment> appointments = new ArrayList<>();

    public Appointment(int patientID , int doctorID, String date,String time ,Status appointmentStatus) {
        countAppointment++;
        this.appointmentID = countAppointment;
        this.date = date;
        this.doctorID = doctorID;
        this.patientID = patientID;
        this.appointmentStatus = appointmentStatus;
        this.time = time;
    }
    public Appointment(int appointmentID,int patientID , int doctorID, String date,String time ,String appointmentStatus) {
        countAppointment++;
        this.appointmentID = appointmentID;
        this.date = date;
        this.doctorID = doctorID;
        this.patientID = patientID;
        this.appointmentStatus = Status.valueOf(appointmentStatus);
        this.time = time;
    }
    public void displayInfo() {
        String appId = User.formatAppointmentID(this);
        String docId = User.formatDoctorID(this);
        System.out.print("Appointment Id: " + appId +", ");
        System.out.print("Doctor ID: "+docId +", ");
        System.out.print("Date: "+date+", ");
        System.out.print("time : "+time+", ");
        System.out.println("Status : "+appointmentStatus);
    }
    public int getPatientID() {
        return patientID;
    }

    public int getDoctorID() {
        return doctorID;
    }

    public int getAppointmentID() {
        return appointmentID;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Status getStatus() {
        return appointmentStatus;
    }

    public void setStatus(Status appointmentStatus) {
        this.appointmentStatus = appointmentStatus;
    }
    public static boolean checkDate_TimeFormat(String date,String time)
    {
        boolean validDate = false;
        boolean validTime = false;
        int num_ = 0;
        for(int i = 1;i < date.length();i++) {
            if(date.charAt(i)=='-') {
                num_++;
            }
        }
        for(int i = 1;i < time.length();i++) {
            if(time.charAt(i)==':') {
                validTime = true;
                break;
            }
        }
        
        if(num_==2)
            validDate = true;
        return validDate&&validTime;
    }
    
    public static boolean checkTimeAndDate(String date,String time)
    {
        for(Appointment ap: appointments)
        {
            if((ap.date).equals(date)&&(ap.time).equals(time))
                return false;
        }
        return true;
    }
    
    public static boolean isDoubleBooked(ArrayList<Appointment> doctorAppointments, String newdate, String newTime) {
        for (Appointment doctorAppointment : doctorAppointments) {
            if(doctorAppointment.getDate().equals(newdate) && doctorAppointment.getTime().equals(newTime)) {
                if(!(doctorAppointment.appointmentStatus==Status.CANCELLED)) {
                    System.out.println("Error: The doctor already has an appointment at this time.");
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isValidateTime(String date, String time) {
        if(date == null ||date.trim().isEmpty()) {
            System.out.println("Error: appointment date cannot be empty.");
            return false;
        }

        if(time == null || time.trim().isEmpty()) {
            System.out.println("Error: appointment time cannot be empty.");
            return false;
        }
        return true;
    }

    public boolean validateCancelledStatus(Status newStatus) {
        if(this.appointmentStatus==Status.CANCELLED && newStatus == Status.COMPLETED) {
            System.out.println("Error: A cancelled appointment cannot be completed.");
            return false;
        }
        
        return true;
    }

    
    
    
    
    
}