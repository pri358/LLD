package Model;


public class DoctorToSlot {
    Doctor doctor;
    Slot slot; 

    public Doctor getDoctor() {
        return doctor;
    }

    public Slot getSlot() {
        return slot;
    }

    public DoctorToSlot(Doctor doctor, Slot slot)
    {
        this.doctor = doctor;
        this.slot = slot;
    }
}
