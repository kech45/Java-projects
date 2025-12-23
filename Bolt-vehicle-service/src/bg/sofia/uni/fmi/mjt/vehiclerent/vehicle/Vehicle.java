package bg.sofia.uni.fmi.mjt.vehiclerent.vehicle;

import bg.sofia.uni.fmi.mjt.vehiclerent.driver.Driver;
import bg.sofia.uni.fmi.mjt.vehiclerent.exception.InvalidRentingPeriodException;
import bg.sofia.uni.fmi.mjt.vehiclerent.exception.VehicleAlreadyRentedException;
import bg.sofia.uni.fmi.mjt.vehiclerent.exception.VehicleNotRentedException;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

public sealed abstract class Vehicle permits Bicycle, Car, Caravan
{
    private final String id;
    private final String model;
    private double pricePerDay;
    private double pricePerHour;

    private Driver currentDriver;
    private LocalDateTime currentRentStart;


    public Vehicle(String id, String model, double pricePerDay, double pricePerHour) {
        this.id = id;
        this.model = model;
        setPricePerDay(pricePerDay);
        setPricePerHour(pricePerHour);
    }

    public String getId() {
        return id;
    }

    public String getModel() {
        return model;
    }

    public double getPricePerDay() {
        return pricePerDay;
    }

    public double getPricePerHour() {
        return pricePerHour;
    }

    public Driver getCurrentDriver() {
        return currentDriver;
    }

    public LocalDateTime getCurrentRentStart() {
        return currentRentStart;
    }

    public void setPricePerHour(double pricePerHour) {
        this.pricePerHour = pricePerHour;
    }

    public void setPricePerDay(double pricePerDay) {
        this.pricePerDay = pricePerDay;
    }

    public void setCurrentDriver(Driver currentDriver) {
        this.currentDriver = currentDriver;
    }

    public void setCurrentRentStart(LocalDateTime currentRentStart) {
        this.currentRentStart = currentRentStart;
    }

    public void rent(Driver driver, LocalDateTime currentRentStart) {
        if (driver == null || currentRentStart == null)
        {
            throw new IllegalArgumentException("Arguments cannot be null!");
        }

        if (this.getCurrentDriver() != null)
        {
            if(this.getCurrentDriver() == driver)
            {
                throw new VehicleAlreadyRentedException("Vehicle is rented by the same driver");
            }
            else
            {
                throw new VehicleAlreadyRentedException("Vehicle is already rented by another driver!");
            }
        }

        setCurrentDriver(driver);
        setCurrentRentStart(currentRentStart);
    }

    public double returnBack(LocalDateTime rentalEnd) throws InvalidRentingPeriodException
    {
        if (rentalEnd == null)
        {
            throw new IllegalArgumentException("Rental end time is null!");
        }

        if (currentDriver == null)
        {
            throw new VehicleNotRentedException("Vehicle is not rented!");
        }

        if (rentalEnd.isBefore(this.getCurrentRentStart()))
        {
            throw new InvalidRentingPeriodException("Rental end is before rental start!");
        }

        double rentalPrice = calculateRentalPrice(this.getCurrentRentStart(), rentalEnd);

        setCurrentDriver(null);
        setCurrentRentStart(null);

        return  rentalPrice;
    }

    public long getDays(LocalDateTime startOfRent, LocalDateTime endOfRent)
    {
        Duration duration = Duration.between(startOfRent, endOfRent); //in seconds and ms
        long minutes = duration.toMinutes();

        return minutes / (24*60);
    }

    public long getHours(LocalDateTime startOfRent, LocalDateTime endOfRent)
    {
        Duration duration = Duration.between(startOfRent, endOfRent); //in seconds and ms
        long minutes = duration.toMinutes();

        return (minutes/ 60) % 24;
    }

    public long getMinutes(LocalDateTime startOfRent, LocalDateTime endOfRent)
    {
        Duration duration = Duration.between(startOfRent, endOfRent); //in seconds and ms

        return duration.toMinutes() % 60;
    }


    public abstract double calculateRentalPrice(LocalDateTime startOfRent, LocalDateTime endOfRent) throws InvalidRentingPeriodException;
}
