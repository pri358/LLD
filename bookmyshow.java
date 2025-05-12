package LLD;
import java.util.*;

// Models 

class Seat
{
    int seatId; 
    char row; 
    int seat;
}

class Movie
{
    int movieId;
    String movieName; 
    // other metadata
}

class Screen
{
    int screenId; 
    String screenName; 
    Theatre theatre;
    List<Seat> seats;
}

class Theatre
{
    int theatreId;
    List<Screen> screens;
}

class Show 
{
    int showId;
    Movie movie;
    Screen screen;
    int startTime; 
    int durationInMinutes;
}