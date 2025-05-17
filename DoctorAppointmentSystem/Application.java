import java.util.*;

import Model.*; 
import Services.*;
import enums.*;
import Strategy.*;

public class Application
{
    public static void main(String[] args)
    {
        DoctorService doctorService = DoctorService.getInstance();
        PatientService patientService = PatientService.getInstance();
        BookingService bookingService = BookingService.getInstance();
        IRankingAvailabilityStrategy rankingAvailabilityStrategy = new RankByStartTimeAvailabilityStrategy();

        doctorService.registerDoc("Curious", Speciality.CARDIOLOGIST);
        doctorService.registerDoc("Dreadful", Speciality.DERMATOLOGIST);
        doctorService.registerDoc("Daring", Speciality.DERMATOLOGIST);

        doctorService.markDoctorAvailability("Curious", Arrays.asList(new Slot("09:30","10:00"),new Slot("12:30","13:00"),new Slot("16:00","16:30")));

        doctorService.markDoctorAvailability("Dreadful", Arrays.asList(new Slot("09:30","10:00"),new Slot("12:30","13:00"),new Slot("16:00","16:30")));

        doctorService.markDoctorAvailability("Daring", Arrays.asList(new Slot("11:30","12:00"),new Slot("14:00","14:30")));

        
        patientService.registerPatient("patient1");

        doctorService.showAvailabilityBySpeciality(Speciality.CARDIOLOGIST, rankingAvailabilityStrategy);

        String bookingId= bookingService.bookAppointment("patient1", "Curious", new Slot("09:30","10:00"));
        System.out.println(bookingId);

    }
}