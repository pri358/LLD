package Model;

public class Slot {
    String startTime; 
    String endTime;
    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    boolean isBooked;

    public Slot(String startTime, String endTime)
    {
        this.startTime = startTime; 
        this.endTime = endTime; 
        this.isBooked = false;
    }

    public boolean isBooked()
    {
        return isBooked;
    }

    @Override
    public String toString()
    {
        return startTime + " - " + endTime;
    }
}
