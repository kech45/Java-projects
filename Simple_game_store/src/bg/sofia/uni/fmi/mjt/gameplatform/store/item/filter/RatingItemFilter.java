package bg.sofia.uni.fmi.mjt.gameplatform.store.item.filter;

import java.math.BigDecimal;
import bg.sofia.uni.fmi.mjt.gameplatform.store.item.StoreItem;

public class RatingItemFilter implements ItemFilter
{
    private double rating;

    public double getRating()
    {
        return rating;
    }

    public void setRating(double rating)
    {
        this.rating = rating;
    }

    public RatingItemFilter(double rating)
    {
        this.setRating(rating);
    }

    @Override
    public boolean matches(StoreItem item)
    {
        return item.getRating() >= this.getRating();
    }

}
