import java.io.*;
import java.util.ArrayList;

public class FileManager
{
    
    public static File patient = new File("patients.txt");
    public static File doctor = new File("doctors.txt");
    public static File appointment = new File("appointments.txt");
    public static File users = new File("users.txt");

    
    public static void savePatients(ArrayList<Patient> patients) {
        try (BufferedWriter patientSaveB = new BufferedWriter(new FileWriter(patient, false))){
            if (!patient.exists())
                throw new FileNotFoundException("patients.txt Not Found");
            for (Patient p : patients) {
                String data = User.formatPatientID(p) + "," + p.getName() + "," +
                              p.getAge() + "," + p.getGender() + "," +
                              p.getPhoneNumber() + "," + User.formatDoctorID(p) + "," + p.getUsername() + "," + p.getPassword();
                patientSaveB.write(data + "\n");
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public static void saveDoctors(ArrayList<Doctor> doctors) {
    try (BufferedWriter doctorSaveB = new BufferedWriter(new FileWriter(doctor, false))) {
        if (!doctor.exists())
            throw new FileNotFoundException("doctors.txt Not Found");
        
        for (Doctor d : doctors) {
            String data = User.formatDoctorID(d) + "," +
                    d.getName() + "," + d.getSpecialization() + "," + 
                    d.getDepartment() + "," + d.getPhoneNumber() + "," +
                    d.getUsername() + "," + d.getPassword();
            doctorSaveB.write(data + "\n");
        }
    } catch (FileNotFoundException e) {
        System.out.println(e.getMessage());
    } catch (IOException e) {
        System.out.println(e.getMessage());
    }
    }

    public static void saveAppointments(ArrayList<Appointment> appointments) {
        try {
            if (!appointment.exists())
                throw new FileNotFoundException("appointments.txt Not Found");
            FileWriter appF = new FileWriter(appointment, false);
            try (BufferedWriter appointmentFile = new BufferedWriter(appF)) {
                for (Appointment a : appointments) {
                    String data = User.formatAppointmentID(a) + "," + User.formatPatientID(a) + "," +
                            User.formatDoctorID(a) + "," + a.getDate() + "," +
                            a.getTime() + "," + a.getStatus();
                    appointmentFile.write(data + "\n");
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public static void loadAll(ArrayList<Doctor> doctors, ArrayList<Patient> patients, ArrayList<Appointment> appointments) {
        
            
            try (BufferedReader br = new BufferedReader(new FileReader(doctor))) {
                if (!doctor.exists())
                    throw new FileNotFoundException("doctors.txt Not Found");
                String line;
                while ((line = br.readLine()) != null) {
                    line = line.trim();
                    if (line.isEmpty()) continue;
                    String[] parts = line.split(",");
                    if (parts.length < 7) continue;
                    Doctor d = new Doctor(parts[1].trim(), parts[5].trim(), parts[4].trim(),
                            parts[6].trim(), parts[2].trim(), parts[3].trim());
                    doctors.add(d);
                }System.out.println("Doctors loaded successfully.");
            }catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        
        try {
            if (!patient.exists())
                throw new FileNotFoundException("patients.txt Not Found");
            try (BufferedReader br = new BufferedReader(new FileReader(patient))) {
                String line;
                while ((line = br.readLine()) != null) {
                    line = line.trim();
                    if (line.isEmpty()) continue;
                    String[] parts = line.split(",");
                    if (parts.length < 7) continue;
                    int age = Integer.parseInt(parts[2].trim());
                    Patient p;
                    if(parts[5].equals("None"))
                    {
                        p = new Patient(parts[1].trim(), parts[6].trim(), parts[7].trim(),
                                parts[4].trim(), age, parts[3].trim(), null);
                    }
                    else
                    {
                        String doctorID = parts[5].substring(1);
                        int docID = Integer.parseInt(doctorID);
                        p = new Patient(parts[1].trim(), parts[6].trim(), parts[7].trim(),
                                parts[4].trim(), age, parts[3].trim(), Doctor.doctors.get(docID-1));
                    }
                    Patient.patients.add(p);
                }
            }
            System.out.println("Patients loaded successfully.");
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        for(Doctor d: Doctor.doctors)
        {
            for(Patient p: Patient.patients)
            {
                try
                {
                    if(p==null||p.getAssignedDoctor()==null)
                        throw new NullPointerException();
                    if(p.getAssignedDoctor().getDoctorID()==d.getDoctorID())
                        d.assignedPatients.add(p);
                }
                catch(NullPointerException e)
                {

                }
            }
        }
        
        try {
            if (!appointment.exists())
                throw new FileNotFoundException("appointments.txt Not Found");
            try (BufferedReader br = new BufferedReader(new FileReader(appointment))) {
                String line;
                while ((line = br.readLine()) != null) {
                    line = line.trim();
                    if (line.isEmpty()) continue;
                    String[] parts = line.split(",");
                    if (parts.length < 6) continue;
                    int patientID = Integer.parseInt(parts[1].replaceAll("[^0-9]", "").trim());
                    int doctorID  = Integer.parseInt(parts[2].replaceAll("[^0-9]", "").trim());
                    String appID = parts[0].substring(1);
                    int appointmentID = Integer.parseInt(appID);
                    Appointment a = new Appointment(appointmentID,patientID, doctorID,
                            parts[3].trim(), parts[4].trim(), parts[5].trim());
                    appointments.add(a);
                }
            }
            System.out.println("Appointments loaded successfully.");
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        for(Patient p: Patient.patients)
        {
            for(Appointment a: Appointment.appointments)
            {
                if(a.getPatientID()==p.getPatientID())
                    p.appointments.add(a);
            }
        }
        for(Doctor d: Doctor.doctors)
        {
            for(Appointment a: Appointment.appointments)
            {
                if(a.getDoctorID()==d.getDoctorID())
                    d.myAppointments.add(a);
            }
        }
    }
    public static void saveUsers(ArrayList<Doctor> doctors, ArrayList<Patient> patients) {
        try {
            if (!users.exists())
                throw new FileNotFoundException("users.txt Not Found");

            FileWriter usersF = new FileWriter(users, false);
            try (BufferedWriter usersB = new BufferedWriter(usersF)) {
                usersB.write(HospitalSystem.getAdmin().name+","+HospitalSystem.getAdmin().username+","+HospitalSystem.getAdmin().password + "\n");
                
                for (Doctor d : doctors) {
                    String data = "doctor," + d.getUsername() + "," + d.getPassword();
                    usersB.write(data + "\n");
                }
                
                for (Patient p : patients) {
                    String data = "patient," + p.getUsername() + "," + p.getPassword();
                    usersB.write(data + "\n");
                }
            }
            System.out.println("Users saved successfully.");

        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    
}