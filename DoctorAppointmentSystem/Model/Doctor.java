package Model;

import java.util.*;
import enums.*;

public class Doctor {
    String id;
    String name; 
    Speciality speciality;
    List<Slot> availableSlots; 
    List<Booking> bookings;

    public Doctor(String name, Speciality speciality)
    {
        this.id = UUID.randomUUID().toString();
        this.name = name; 
        this.speciality = speciality;
        availableSlots = new ArrayList<>();
        bookings = new ArrayList<>();
    }

    public void addAvailableSlot(Slot slot)
    {
        availableSlots.add(slot);
    }

    public void addBookings(Booking booking)
    {
        bookings.add(booking);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Speciality getSpeciality() {
        return speciality;
    }

    public void setSpeciality(Speciality speciality) {
        this.speciality = speciality;
    }

    public List<Slot> getAvailableSlots() {
        return availableSlots;
    }

    public void setAvailableSlots(List<Slot> availableSlots) {
        this.availableSlots = availableSlots;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }
    
}
