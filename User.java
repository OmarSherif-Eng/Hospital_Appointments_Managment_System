public abstract class User {
    protected String name;
    protected String username;
    protected String password;

    public User() {
    }
    
    public User(String name, String username, String password) {
        this.name = name;
        this.username = username;
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
    public abstract void displayInfo();
    public abstract void showMenu();

    public static String formatPatientID(Patient ID) {
        String patientID = (ID.getPatientID() > 99)? "P":(ID.getPatientID() > 9)? "P0":"P00";
        return patientID+ID.getPatientID();
    }
    
    public static String formatPatientID(Appointment ID) {
        String patientID = (ID.getPatientID() > 99)? "P": (ID.getPatientID() > 9)? "P0": "P00";
        return patientID + ID.getPatientID();
    }

    public static String formatDoctorID(Doctor ID) {
        String DoctorID = (ID.getDoctorID() > 99)? "D":(ID.getDoctorID() > 9)? "D0": "D00";
        return DoctorID+ID.getDoctorID();
    }

    public static String formatDoctorID(Appointment ID) {
        String doctorID = (ID.getDoctorID() > 99)? "D": (ID.getDoctorID() > 9)? "D0": "D00";
        return doctorID + ID.getDoctorID();
    }

    public static String formatDoctorID(Patient ID) {
        if(ID.getAssignedDoctor() == null)
            return "None";
        String patientID = (ID.getAssignedDoctor().getDoctorID() > 99)? "D": (ID.getAssignedDoctor().getDoctorID() > 9)? "D0": "D00";
        return patientID + ID.getAssignedDoctor().getDoctorID();
    }

    public static String formatAppointmentID(Appointment ID) {
        String AppointmentID = (ID.getAppointmentID() > 99)? "A": (ID.getAppointmentID() > 9)? "A0": "A00";
        return AppointmentID + ID.getAppointmentID();
    }
}