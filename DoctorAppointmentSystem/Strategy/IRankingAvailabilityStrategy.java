package Strategy;

import Model.*; 
import java.util.*;

// rank given slots 
public interface IRankingAvailabilityStrategy {
    public List<DoctorToSlot> rankAvailableSlots(List<DoctorToSlot> slots);
}
