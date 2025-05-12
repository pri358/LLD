package LLD;
/*
 driver -> driver manager 
 rider -> rider manager 
 trip -> trip manager 
 tripMetaData -> trip manager 

 Pricing Stretegy 
 RiderMatchStrategy 
 
 */

import java.util.*;

public class uber {

    public static void main(String[] args)
    {
        try{
            RiderManager riderManager = RiderManager.getInstance(); 
            DriverManager driverManager = DriverManager.getInstance();
            TripManager tripManager = TripManager.getInstance();

            String rider1 = riderManager.createRider("Pri");
            String driver1 = driverManager.createDriver("driver1");
            String driver2 = driverManager.createDriver("driver2");

            tripManager.createTrip(rider1, new Location(10,10), new Location(20, 20));
            tripManager.createTrip("blah", new Location(10,10), new Location(20, 20));
        }

        catch(Exception ex)
        {
            System.out.println(ex.getMessage());
        }
    }
}

enum Rating
{
    FIVE_STAR,
    FOUR_STAR,
    THREE_STAR,
    TWO_STAR,
    ONE_STAR
}

class Rider
{
    String riderId;
    String riderName;
    Rating riderRating;

    public Rating getRiderRating() {
        return this.riderRating;
    }

    public void setRiderRating(Rating riderRating) {
        this.riderRating = riderRating;
    }

    public Rider(String name)
    {
        riderId = UUID.randomUUID().toString();
        this.riderName = name;
        riderRating = Rating.FIVE_STAR;
    }

}

class Driver 
{
    String driverId; 
    String driverName; 
    Rating driverRating;

	public Rating getDriverRating() {
		return this.driverRating;
	}

	public void setDriverRating(Rating driverRating) {
		this.driverRating = driverRating;
	}
 
    boolean isAvailable;

    public boolean isIsAvailable() {
        return this.isAvailable;
    }

    public void setIsAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    public Driver(String name)
    {
        driverId = UUID.randomUUID().toString();
        this.driverName = name; 
        this.driverRating = Rating.FIVE_STAR;
        this.isAvailable = true;
    }
}

class Trip
{
    String tripId; 
    Location source;
    Location destination;
    Rider rider;
    Driver driver; 
    double price; 
    IPricingStrategy pricingStrategy;
    IDriverMatchStrategy driverMatchStrategy;

    public void setPrice(double price) {
        this.price = price;
    }

    public void setPricingStrategy(IPricingStrategy pricingStrategy) {
        this.pricingStrategy = pricingStrategy;
    }

    public void setDriverMatchStrategy(IDriverMatchStrategy driverMatchStrategy) {
        this.driverMatchStrategy = driverMatchStrategy;
    }

    public Trip(Rider rider, Driver driver, double price, IPricingStrategy pricingStrategy, IDriverMatchStrategy driverMatchStrategy, Location source, Location destination)
    {
        this.tripId = UUID.randomUUID().toString();
        this.rider = rider; 
        this.driver = driver;
        this.price = price;
        this.pricingStrategy = pricingStrategy;
        this.driverMatchStrategy = driverMatchStrategy;
        this.source = source;
        this.destination = destination;
    }


    @Override
    public String toString()
    {
        StringBuilder tripStr = new StringBuilder(); 
        tripStr.append("Source: " + source); tripStr.append('\n');
        tripStr.append("Destination: " + destination); tripStr.append('\n');
        tripStr.append("Rider: " + rider.riderName); tripStr.append('\n');
        tripStr.append("Driver: " + driver.driverName); tripStr.append('\n');
        tripStr.append("Price: " + price); tripStr.append('\n');
        return tripStr.toString();
    }
    
}

class TripMetaData
{
    String id;
    Location source;
    Location destination;
    Rider rider;
    Driver driver;


    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public TripMetaData(Rider rider, Location source, Location destination)
    {
        this.id = UUID.randomUUID().toString();
        this.source = source;
        this.rider = rider; 
        this.destination = destination;
    }
}

class Location
{
    int longitude;
    int latitude;

    public Location(int longitude, int latitude)
    {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public String toString()
    {
        return longitude + " " + latitude;
    }
}


class StrategyManager
{
    private static StrategyManager strategyManager;
    private StrategyManager() {}

    public static StrategyManager getInstance()
    {
        if(strategyManager == null) strategyManager = new StrategyManager();
        return strategyManager;
    }

    public IPricingStrategy getPricingStrategy(TripMetaData tripMetaData)
    {
        return new DefaultPricingStrategy();
    }

    public IDriverMatchStrategy getDriverMatchStrategy(TripMetaData tripMetaData)
    {
        return new DefaultDriverMatchStrategy();
    }

}

interface IPricingStrategy 
{
    public double calculatePrice();
}

interface IDriverMatchStrategy
{
    public Driver matchDriver();
}

class DefaultPricingStrategy implements IPricingStrategy
{
    public double calculatePrice()
    {
        return 100.0;
    }
}

class DefaultDriverMatchStrategy implements IDriverMatchStrategy
{
    public Driver matchDriver()
    {
        Map<String, Driver> drivers = DriverManager.getInstance().drivers;
        if(drivers.size() == 0) throw new RuntimeException("Drivers not found");
        return drivers.get(drivers.keySet().iterator().next());
    }
}

class RiderManager
{
    private static RiderManager riderManager; 
    Map<String, Rider> riders;

    private RiderManager()
    {
        riders = new HashMap<>();
    }

    public static RiderManager getInstance()
    {
        if(riderManager == null)
        {
            riderManager = new RiderManager();
        }
        return riderManager;
    }

    private void registerRider(Rider rider)
    {
        riders.put(rider.riderId, rider);
    }

    public String createRider(String riderName)
    {
        Rider rider = new Rider(riderName);
        registerRider(rider);
        return rider.riderId;
    }

    public Rider getRider(String riderId)
    {
        if(!riders.containsKey(riderId))
        {
            throw new RuntimeException("Rider doesn't exist!! Please register first");
        }
        return riders.get(riderId);
    }
}

class DriverManager
{
    private static DriverManager driverManager;
    Map<String, Driver> drivers; 

    private DriverManager()
    {
        drivers = new HashMap<>();
    }

    public static DriverManager getInstance()
    {
        if(driverManager == null) driverManager = new DriverManager();
        return driverManager;
    }

    private void registerDriver(Driver driver)
    {
        drivers.put(driver.driverId, driver);
    }

    public String createDriver(String driverName)
    {
        Driver driver = new Driver(driverName); 
        registerDriver(driver);
        return driver.driverId;
    }

}

class TripManager
{
    private static TripManager tripManager;
    DriverManager driverManager; 
    RiderManager riderManager; 
    Map<String, Trip> trips;
    Map<String, TripMetaData> tripsMetadata;

    private TripManager()
    {
        riderManager = RiderManager.getInstance();
        trips = new HashMap<>();
        tripsMetadata = new HashMap<>();
    }

    public static TripManager getInstance()
    {
        if(tripManager == null) tripManager = new TripManager();
        return tripManager;
    }

    public void createTrip(String riderId, Location source, Location destination)
    {
        Rider curRider = riderManager.getRider(riderId);
        TripMetaData tripMetaData = new TripMetaData(curRider, source, destination);

        StrategyManager strategyManager = StrategyManager.getInstance();
        IPricingStrategy pricingStrategy = strategyManager.getPricingStrategy(tripMetaData);
        IDriverMatchStrategy driverMatchStrategy = strategyManager.getDriverMatchStrategy(tripMetaData); 

        Driver driver = driverMatchStrategy.matchDriver();
        double price = pricingStrategy.calculatePrice();

        Trip trip = new Trip(curRider, driver,price, pricingStrategy, driverMatchStrategy, source, destination); 
        trips.put(trip.tripId, trip);
        tripsMetadata.put(tripMetaData.id, tripMetaData);

        System.out.println(trip);

    }

}