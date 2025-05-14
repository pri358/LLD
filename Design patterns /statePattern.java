public class statePattern {
    
}

interface someState
{
    void next(Context context); 
    public String getState();
}

class state1 implements someState
{
    public void next(Context context)
    {
        // move basis of context
    }
    public String getState()
    {
        return "";
    }
}

class Context
{
    // details
    // initial state

    // state transition here 
}