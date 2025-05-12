package LLD;
import java.util.*;
/*
 Components: 
 1. vehicle creation 
 2. assign spots 
 3. calculate fee
 4. make payment 

 Class 
 1. Vehicle -> VehicleManager 
 2. ParkingLot 
 3. Parking Spot 
 4. Fee strategy
 5. payment srategy 
 */

 // main function
public class parkinglotRunner {
    
    public static void main(String[] args)
    {
        ParkingLot lot = new ParkingLot.ParkingLotBuilder()
                                .setBikes(0)
                                .setCars(4)
                                .build();
        Vehicle car1 = new Car("DEL9948");
        Vehicle car2 = new Car("CHD2234");

        lot.parkVehicle(car1);
        lot.parkVehicle(car2);
        lot.exitLot(car1, 1, DurationType.Days, PaymentType.Card);
        
    }
}

enum VehicleType
{
    Bike, 
    Car
}

abstract class Vehicle
{
    String licenseNumber;
    VehicleType type;

    public Vehicle(String license, VehicleType type)
    {
        this.licenseNumber = license;
        this.type = type;
    }
}

class Car extends Vehicle
{
    public Car(String license)
    {
        super(license, VehicleType.Car);
    }
}

// create car and bike 

abstract class ParkingSpot
{
    String spotNumber; //"A1"..
    boolean isAvailable;
    VehicleType slotType;
    Vehicle vehicle;
    IParkingFeeStrategy strategy;

    public ParkingSpot(String spotNumber, VehicleType slotType, IParkingFeeStrategy strategy)
    {
        this.spotNumber = spotNumber;
        this.slotType = slotType;
        this.isAvailable = true;
        this.strategy = strategy;
    }

    public void parkVehicle(Vehicle vehicle)
    {
        if(!isAvailable) throw new RuntimeException("Parking Spot not available!!");
        this.vehicle = vehicle;
        this.isAvailable = false;
    }

    public boolean vacateSpot(Vehicle vehicle)
    {
        this.vehicle = null;
        this.isAvailable = true;
        return true;
    }

}

class CarParkingSpot extends ParkingSpot
{
    public CarParkingSpot(String spotId, IParkingFeeStrategy parkingFeeStrategy)
    {
        super(spotId, VehicleType.Car, parkingFeeStrategy);
    }
}
// create parking spots 

enum DurationType
{
    Hours,
    Days
}

interface IParkingFeeStrategy
{
    public double calculateFee(VehicleType type, int duration, DurationType durationType);
}

class DefaultFeeStrategy implements IParkingFeeStrategy
{
    public double calculateFee(VehicleType type, int duration, DurationType durationType)
    {
        return 100.0;
    }
}
// implement diff strategies
// strategy manager 

enum PaymentType
{
    Cash,
    Card
}

interface IPaymentStrategy
{
    public boolean processPayment(double fee);
}

class CardPaymentStrategy implements IPaymentStrategy
{
    public boolean processPayment(double fee)
    {
        System.out.println("Processed payment: " + fee);
        return true;
    }
}

// implement diff strategies
// strategy manager 


class ParkingLot
{
    private static ParkingLot parkingLot;
    List<ParkingSpot> parkingSpots;
    CardPaymentStrategy cardPaymentStrategy;

    private ParkingLot(ParkingLotBuilder parkingLotBuilder)
    {
        parkingSpots = new ArrayList<>();
        for(int i=0;i<parkingLotBuilder.numCars;i++)
        {
            parkingSpots.add(new CarParkingSpot("i"+i,  new DefaultFeeStrategy()));
        }
        this.cardPaymentStrategy = new CardPaymentStrategy();
    }
    
    public static ParkingLot getInstance(ParkingLotBuilder parkingLotBuilder)
    {
        if(parkingLot == null) parkingLot = new ParkingLot(parkingLotBuilder);
        return parkingLot;
    }
        

    public static class ParkingLotBuilder
    {
        private int numCars; 
        private int numBikes;

        public ParkingLotBuilder setCars(int numCars)
        {
            this.numCars = numCars;
            return this;
        }

        public ParkingLotBuilder setBikes(int numBikes)
        {
            this.numBikes = numBikes;
            return this;
        }

        public ParkingLot build()
        {
            return ParkingLot.getInstance(this);
        }
    }

    public ParkingSpot parkVehicle(Vehicle vehicle)
    {
        ParkingSpot spot = findAvailableSpot(vehicle.type);
        spot.parkVehicle(vehicle);
        System.out.println("Vehicle Parked at: " + spot.spotNumber);
        return spot;
    }

    public void exitLot(Vehicle vehicle, int duration, DurationType durationType, PaymentType paymentType)
    {
        ParkingSpot currentSpot = findParkingSpotForVehicle(vehicle); 
        double price = currentSpot.strategy.calculateFee(vehicle.type, duration, durationType);
        cardPaymentStrategy.processPayment(price);
        currentSpot.vacateSpot(vehicle);
    }

    private ParkingSpot findParkingSpotForVehicle(Vehicle vehicle)
    {
        for(ParkingSpot spot: parkingSpots)
        {
            if(spot.vehicle == null) continue;
            if(spot.vehicle.licenseNumber == vehicle.licenseNumber) return spot;
        }
        throw new RuntimeException("Vehicle not parked in this lot!!");
    }

    public void addParkingSpot(ParkingSpot newSpot)
    {
        parkingSpots.add(newSpot);
    }

    private ParkingSpot findAvailableSpot(VehicleType vehicleType)
    {
        for(ParkingSpot spot: parkingSpots)
        {
            if(spot.slotType.equals(vehicleType) && spot.isAvailable)
            {
                return spot;
            }
        }
        throw new RuntimeException("No Parking Spot Available!! ");
    }
}

