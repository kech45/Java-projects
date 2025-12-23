package bg.sofia.uni.fmi.mjt.vehiclerent.vehicle;

import bg.sofia.uni.fmi.mjt.vehiclerent.exception.InvalidRentingPeriodException;

import java.time.Duration;
import java.time.LocalDateTime;

public final class Car extends Vehicle
{
    private final FuelType fuelType;
    private final int numberOfSeats;
    private double pricePerWeek;

    public Car(String id, String model, FuelType fuelType, int numberOfSeats, double pricePerWeek, double pricePerDay, double pricePerHour)
    {
        super(id, model, pricePerDay, pricePerHour);
        this.fuelType = fuelType;
        this.numberOfSeats = numberOfSeats;
        this.pricePerWeek = pricePerWeek;
    }

    public FuelType getFuelType()
    {
        return fuelType;
    }

    public int getNumberOfSeats()
    {
        return numberOfSeats;
    }

    public double getPricePerWeek()
    {
        return pricePerWeek;
    }

    public void setPricePerWeek(double pricePerWeek)
    {
        this.pricePerWeek = pricePerWeek;
    }

    @Override
    public double calculateRentalPrice(LocalDateTime startOfRent, LocalDateTime endOfRent) throws InvalidRentingPeriodException
    {
        if (startOfRent.isAfter(endOfRent))
        {
            throw new InvalidRentingPeriodException("Invalid period!");
        }

        long totalDays = getDays(startOfRent, endOfRent);

        long days = totalDays % 7;
        long weeks = totalDays / 7;
        long hours = getHours(startOfRent, endOfRent);
        long minutes = getMinutes(startOfRent, endOfRent);


        if(minutes > 0)
        {
            hours +=1;
        }

        if (hours == 24)
        {
            days += 1;
            hours = 0;
        }

        if (days == 7)
        {
            weeks +=1;
            days = 0;
        }

        double price = weeks* getPricePerWeek() + days*getPricePerDay() + hours*getPricePerHour() +
        fuelType.getDailyTax() * totalDays + numberOfSeats * 5 + this.getCurrentDriver().ageGroup().getTax();


        return price;
    }
}

