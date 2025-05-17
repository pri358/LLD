package Strategy;

import Model.*; 
import java.util.*;

public class RankByStartTimeAvailabilityStrategy implements IRankingAvailabilityStrategy{

    public List<DoctorToSlot> rankAvailableSlots(List<DoctorToSlot> slots)
    {
        Collections.sort(slots, (a,b) -> a.getSlot().getStartTime().compareTo(b.getSlot().getStartTime()));
        return slots;
    }
}
