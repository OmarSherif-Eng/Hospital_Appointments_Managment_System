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
        this.appointmentStatus = Status.valueOf(appointmentStatus.trim().toUpperCase());
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
    public static boolean checkDateFormat(String date)
    {
        if(date.length() > 10 || date.length() < 8)
            return false;
        boolean validDate = false;
        boolean checkYear_Month_DayNumbers = true;
        boolean checkYearSize = (date.charAt(4)=='-');
        boolean checkMonthSize = (date.charAt(6) == '-' || date.charAt(7) == '-');
        int num_ = 0;
        for(int i = 0;i < date.length();i++) {
            if((date.charAt(i) < '0' || date.charAt(i) > '9') && date.charAt(i) != '-') {
                checkYear_Month_DayNumbers = false;
                break;
            }
            if(date.charAt(i)=='-') {
                num_++;
            }
        }
         
        
        if(num_==2)
            validDate = true;
        return validDate && checkYear_Month_DayNumbers && checkYearSize && checkMonthSize;
    }

    public static boolean checkTimeFormat(String time) {
        if(time.length() < 3 || time.length() > 5)
            return false;
        int colonNum = 0;
        boolean validTime = false;
        boolean checkHours_MinutesNumbers = true;
        boolean checkHourSize = (time.charAt(1) == ':' || time.charAt(2) == ':');
        for(int i = 0;i < time.length();i++) {
            if ((time.charAt(i) < '0' || time.charAt(i) > '9') && time.charAt(i) != ':') {
                checkHours_MinutesNumbers = false;
                break;
            }
            if(time.charAt(i)==':') {
                colonNum++;
            }
        }
        if(colonNum  == 1)
            validTime = true;
        return validTime && checkHours_MinutesNumbers && checkHourSize;
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