

public class HospitalSystem {
    
    
    
    private static final Admin admin = new Admin("Admin", "admin", "admin123");
    public void loadAll() {
        System.out.println("Loading data from files...");
        FileManager.loadAll(Doctor.doctors, Patient.patients, Appointment.appointments);
    }
    public Doctor findDoctorByID(int id) {
        for (Doctor d : Doctor.doctors) {
            if (d.getDoctorID() == id)
                return d;
        }
        return null;
    }
    public Patient findPatientByID(int id) {
        for (Patient p : Patient.patients) {
            if (p.getPatientID() == id)
                return p;
        }
        return null;
    }
    
    public Appointment findAppointmentByID(int id){
        if(Appointment.appointments.isEmpty()) {
            System.out.println("There is no appointments");
            return null;
        }
        
        for(Appointment a : Appointment.appointments) {
            if(a.getAppointmentID() == id) {
                return a;
            }
        }
        return null;
    }
    
    public User findUserByCredentials(String username,String password){
        if(admin!=null&&admin.getUsername().equals(username)&&admin.getPassword().equals(password))
            return admin;
        for (Doctor d:Doctor.doctors){
            if(d.getUsername().equals(username)&&d.getPassword().equals(password))
            return d;
        }
        for (Patient p:Patient.patients){
            if(p.getUsername().equals(username)&&p.getPassword().equals(password))
            return p;
        }   
        return null;
        }
        public void saveAll(){
            FileManager.saveDoctors(Doctor.doctors);
            FileManager.savePatients(Patient.patients);
            FileManager.saveAppointments(Appointment.appointments);
        }
        public void addDoctor(Doctor d){
            Doctor.doctors.add(d);
        }
        public void addPatient(Patient p){
            Patient.patients.add(p);
        }
        public static Admin getAdmin() {
            return admin;
        }
        
}