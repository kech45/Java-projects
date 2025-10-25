package bg.sofia.uni.fmi.mjt.gameplatform.store.item.category;

import bg.sofia.uni.fmi.mjt.gameplatform.store.item.AbstractStoreItem;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Game extends AbstractStoreItem
{
    private String genre;

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre)
    {
        this.genre = genre;
    }

    public Game(String title, BigDecimal price, LocalDateTime releaseDate, String genre)
    {
        this.setTitle(title);
        this.setPrice(price);
        this.setReleaseDate(releaseDate);
        this.setGenre(genre);
    }

}
