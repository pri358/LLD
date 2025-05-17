package Model;

import java.util.*;
import enums.*;

public class Booking {
    String id;
    Doctor doctor; 
    Patient patient; 
    Slot slot;
    Date bookingTimestamp;
    BookingType bookingType;

    public String getId() {
        return id;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public Patient getPatient() {
        return patient;
    }

    public Slot getSlot() {
        return slot;
    }

    public Date getBookingTimestamp() {
        return bookingTimestamp;
    }

    public BookingType getBookingType() {
        return bookingType;
    }

    public Booking(Doctor doctor, Patient patient, Slot slot, Date bookingTimestamp, BookingType bookingType)
    {
        this.id = UUID.randomUUID().toString(); 
    }
}
