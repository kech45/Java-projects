package bg.sofia.uni.fmi.mjt.vehiclerent.vehicle;

import bg.sofia.uni.fmi.mjt.vehiclerent.exception.InvalidRentingPeriodException;

import java.time.Duration;
import java.time.LocalDateTime;

public final class Bicycle extends Vehicle
{
    public Bicycle(String id, String model, double pricePerDay, double pricePerHour)
    {
        super(id, model, pricePerDay, pricePerHour);
    }

    @Override
    public double calculateRentalPrice(LocalDateTime startOfRent, LocalDateTime endOfRent) throws InvalidRentingPeriodException
    {
        if (startOfRent.isAfter(endOfRent))
        {
            throw new InvalidRentingPeriodException("Invalid period!");
        }

        if(Duration.between(startOfRent, endOfRent).compareTo(Duration.ofDays(7)) >= 0)
        {
            throw new InvalidRentingPeriodException("Duration is longer than 1 week!");
        }

        long hours = getHours(startOfRent, endOfRent);
        long days = getDays(startOfRent, endOfRent);
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

        return days*getPricePerDay() + hours*getPricePerHour();
    }
}
