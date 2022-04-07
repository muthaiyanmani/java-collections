
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

class Appointment {
    private String doctorNo;
    private String startTime;
    private String endTime;
    private String status = "ALLOWED";

    public Appointment(String doctorNo, String startTime, String endTime) {
        this.doctorNo = doctorNo;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getDoctorNo() {
        return doctorNo;
    }

    public void setDoctorNo(String doctorNo) {
        this.doctorNo = doctorNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}

public class Hospital {

    private int doctors;
    private int patients;

    ArrayList<Appointment> al = new ArrayList<>();

    Scanner sc = new Scanner(System.in);

    public Hospital() {
    }

    public Hospital(int doctors, int patients) {
        this.doctors = doctors;
        this.patients = patients;
    }
    
    String validateInput(String regex, String message) {
        while (true) {
            String str = sc.nextLine();
            if (str.matches(regex)) {
                return str;
            } else {
                System.out.print("Invalid Input..\n" + message);
            }
        }
    }

    void getInput() {
        System.out.print("No of doctors : ");
        doctors = Integer.parseInt(validateInput("[0-9]+", "Enter a Integer value : "));
        System.out.print("No of patients : ");
        patients = Integer.parseInt(validateInput("[0-9]+", "Enter a Integer value : "));

        for (int i = 0; i < patients; i++) {
            System.out.print("Doctor Number: ");
            String doctorNo = validateInput("[a-zA-Z0-9]+", "Enter a Proper value : ");
           
            System.out.print("Start Time: ");
            String startTime = validateInput("[0-1][0-9]\\s[0-5][0-9]\\s[AP][M]", "Enter a start time format value ex.[11 50 PM]: ");
            System.out.print("End Time: ");
            String endTime = validateInput("[0-1][0-9]\\s[0-5][0-9]\\s[AP][M]", "Enter a end time format value ex.[11 50 PM]: ");
            if (calculateTime(startTime, endTime) >= 1) {
                al.add(new Appointment(doctorNo, startTime, endTime));
            } else if (calculateTime(startTime, endTime) == 0) {
                System.out.println("Appointment should at least one minute long..");
                i--;
            } else {
                System.out.println("The time will be from 00 00 AM to 11 59 PM..");
                i--;
            }
        }
    }

    void checkAppointment() {
        for (int i = 0; i < al.size(); i++) {
            for (int j = i + 1; j < al.size(); j++) {
                Appointment a = al.get(i);
                Appointment b = al.get(j);

                if (a.getDoctorNo().equals(b.getDoctorNo())) {
                    if (calculateTime(a.getEndTime(), b.getStartTime()) < 0) {
                        b.setStatus("NOT ALLOWED");
                    }
                }

            }
        }
    }

    void display() {
        for (Appointment x : al) {
            System.out.println("FOR PATIENT : DOCTOR " + x.getDoctorNo());
            System.out.println("\t" + x.getStatus() + " : from " + x.getStartTime() + " to " + x.getEndTime());
        }
    }

    long calculateTime(String startTime, String endTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("hh mm aa");
        try {
            Date d1 = sdf.parse(startTime);
            Date d2 = sdf.parse(endTime);
            long elapsed = (d2.getTime() - d1.getTime()) / (60 * 1000);
            return elapsed;

        } catch (ParseException e) {
            System.out.println(e);
        }
        return -1;
    }

    public static void main(String args[]) {
        Hospital h = new Hospital();
        h.getInput();
        h.checkAppointment();
        h.display();
    }

}
