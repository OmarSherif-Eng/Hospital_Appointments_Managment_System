import java.util.InputMismatchException;
import java.util.Scanner;
public class Main {
    public static void main (String[]args){
        HospitalSystem system = new HospitalSystem();
        boolean running=true;
        system.loadAll();
        
            try (Scanner input = new Scanner(System.in)) {
            while(running){
                System.out.println("1. Login as Admin");
                System.out.println("2. Login as Doctor");
                System.out.println("3. Login as Patient");
                System.out.println("4. Exit");
                System.out.println("Enter your choice: ");
                try {
                    int choice=input.nextInt();
                switch(choice){
                    case 1 -> login(system, input, "Admin");
                    case 2 -> login(system, input, "Doctor");
                    case 3 -> login(system, input, "Patient");
                    case 4 -> //system.saveAll();
                        //System.out.println("Data Saved");
                        running=false;
                    default -> System.out.println("Invalid choice");
                }
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please enter a number.");
                    input.nextLine();
                }
                
            }
        } 
    }

    public static void login(HospitalSystem system,Scanner input,String role){
            System.out.println("Login as "+role);
            System.out.print("Username: ");
            String username = input.next();
            System.out.print("Password: ");
            String password = input.next();
            
            User user = system.findUserByCredentials(username, password);
            if (user ==null){
                System.out.println("Invalid Credential");
                return;
            }
            boolean ok=false;
            switch(role){
                case "Admin" -> ok =(user instanceof Admin);
                case "Doctor" -> ok =(user instanceof Doctor);
                case "Patient" -> ok =(user instanceof Patient);
            }
            if(!ok){
                System.out.println("Access denied wrong role for this login");
                return;
            }
            System.out.println("You have logged in successfully as "+role);
            user.showMenu();
    }
}