package Services;

import java.util.*;


import Model.*; 
import enums.*;
import Strategy.*;

public class DoctorService {
    // singleton 
    private static DoctorService doctorService; 
    Map<String, Doctor> doctors; 

    private DoctorService()
    {
        doctors = new HashMap<>();
    }

    public synchronized static DoctorService getInstance()
    {
        if (doctorService == null) {
            doctorService = new DoctorService();
        }
        return doctorService;
    }

    public void registerDoc(String name, Speciality speciality)
    {
        // validation on name
        Doctor doctor = new Doctor(name, speciality);
        doctors.put(name, doctor);
        System.out.println("Welcome " + doctor.getName() + " !!");
    }

    public void markDoctorAvailability(String name, List<Slot> slots)
    {
        if(!doctors.containsKey(name))
        {
            System.out.println("Doctor not found!! Please register first");
            return;
        }
        Doctor curDoctor = doctors.get(name);
        for(Slot slot: slots)
        {
            if(!isValidSlot(slot))
            {
                System.out.println("Slot" + slot +  "not valid, try again!");
            }
            else
            {
                curDoctor.addAvailableSlot(slot);
                System.out.println("Slot" + slot +  " added");
            }
        }
        System.out.println("Done Doc! ");
    }

    private boolean isValidSlot(Slot slot)
    {
        return true;
    }

    public void showAvailabilityBySpeciality(Speciality speciality, IRankingAvailabilityStrategy rankingAvailabilityStrategy)
    {
        List<DoctorToSlot> availableSlots = new ArrayList<>(); 

        for(String doctorName: doctors.keySet())
        {
            Doctor doctor = doctors.get(doctorName); 
            if(! doctor.getSpeciality().equals(speciality)) continue;

            for(Slot slot: doctor.getAvailableSlots())
            {
                if(slot.isBooked()) continue; 
                availableSlots.add(new DoctorToSlot(doctor, slot));
            }
        }

        List<DoctorToSlot> rankedSlots = rankingAvailabilityStrategy.rankAvailableSlots(availableSlots); 
        showAvailableSlots(rankedSlots);
    }

    private void showAvailableSlots(List<DoctorToSlot> rankedSlots)
    {
        if(rankedSlots == null || rankedSlots.size() == 0)
        {
            System.out.println("No doctor/slots available!! ");
            return;
        }

        for(DoctorToSlot current: rankedSlots)
        {
            System.out.println(current.getDoctor().getName() + " " + current.getSlot());
        }
    }

    public Doctor getDoctor(String name)
    {
        if(!doctors.containsKey(name))
        {
            System.out.println("Doctor not found!! Please register first");
            return null;
        }
        Doctor curDoctor = doctors.get(name);
        return curDoctor;
    }

    public void getTrendingDoctor()
    {
        // choose doctor with max bookings 
    }

    
}
