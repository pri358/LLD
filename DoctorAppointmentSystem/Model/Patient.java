package Model;

import java.util.*;

public class Patient
{
    String id;
    String name; 
    List<Booking> bookings;

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

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }

    public Patient(String name)
    {
        this.id = UUID.randomUUID().toString(); 
        this.name = name;
        bookings = new ArrayList<>();
    }

    public void addBooking(Booking booking)
    {
        this.bookings.add(booking);
    }

    @Override
    public String toString()
    {
        StringBuilder patient = new StringBuilder(); 
        patient.append("Patient id: " + id); patient.append('\n');
        patient.append("Patient name: " + name); patient.append('\n');
        patient.append("Patient bookings: " + bookings); 
        return patient.toString();
    }

}