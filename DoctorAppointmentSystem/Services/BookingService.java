package Services;


import java.util.*;
import Model.*; 
import enums.*;

public class BookingService {
    // singleton 
    private static BookingService bookingService;
    Map<String, Booking> bookings;

    private BookingService()
    {
        bookings = new HashMap<>();
    }

    public synchronized static BookingService getInstance()
    {
        if(bookingService == null) bookingService = new BookingService();
        return bookingService;
    }

    // book appointment 

    public String bookAppointment(String patientName, String doctorName, Slot slot)
    {
        Doctor curDoctor = DoctorService.getInstance().getDoctor(doctorName);
        Patient patient = PatientService.getInstance().getPatient(patientName); 

        if(!validatePatientsBooking(patient, slot))
            return "Patient already has a booking at this time";
        
        List<Slot> slots = curDoctor.getAvailableSlots(); 
        Slot foundAvailableSlot = null;
        for(Slot availableSlot: slots)
        {
            if(availableSlot.getStartTime().equals(slot.getStartTime()))
            {
                foundAvailableSlot = availableSlot;
            }
        }
        if(foundAvailableSlot == null) return "No available slots found"; 
        if(foundAvailableSlot.isBooked())
        {
            Booking booking = new Booking(curDoctor, patient, foundAvailableSlot, new Date(), BookingType.WAITLIST);
            System.out.println("slot waitlisted!!"); 
            return booking.getId();
        }
        Booking booking = new Booking(curDoctor, patient, foundAvailableSlot, new Date(), BookingType.CONFIRMED);
        System.out.println("slot confirmed!!"); 
        bookings.put(booking.getId(), booking);
        curDoctor.addBookings(booking);
        patient.addBooking(booking);
        return booking.getId();
    }

    private boolean validatePatientsBooking(Patient patient, Slot slot)
    {
        return true;
    }

    // cnacel appointment 

    public void cancelBooking(String bookingId)
    {
        Booking curBooking = bookings.get(bookingId); 
        curBooking.getDoctor().getBookings().remove(curBooking);
        curBooking.getPatient().getBookings().remove(curBooking);

        // mark the slot as available 
        // try booking any waitlisted 

    }

    // book waiting appointment 
}
