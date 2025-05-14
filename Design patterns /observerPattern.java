import java.util.*;

public class observerPattern {
    
}

interface Observer 
{
    public void execute();
}

class SomeObserver
{
    public void execute()
    {

    }
// can have different functionalities (if observing multiple things)
}

class ToBeObserved
{
    List<Observer> observers; 

    // add observer 
    // notify observer -> observer.execute()
}