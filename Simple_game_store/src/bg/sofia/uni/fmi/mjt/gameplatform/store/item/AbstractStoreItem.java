package bg.sofia.uni.fmi.mjt.gameplatform.store.item;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public abstract class AbstractStoreItem implements StoreItem
{
    private String title;
    private BigDecimal price;
    private double rating;
    private LocalDateTime releaseDate;

    @Override
    public String getTitle()
    {
        return title;
    }

    @Override
    public BigDecimal getPrice()
    {
        return price;
    }

    @Override
    public double getRating()
    {
        return rating;
    }

    @Override
    public LocalDateTime getReleaseDate()
    {
        return releaseDate;
    }

    @Override
    public void setTitle(String title)
    {
        this.title = title;
    }

    @Override
    public void setPrice(BigDecimal price)
    {
        this.price = price;
    }

    @Override
    public void setReleaseDate(LocalDateTime releaseDate)
    {
        this.releaseDate = releaseDate;
    }

    @Override
    public void rate(double rating)
    {
        this.rating = rating;
    }
}
