package bg.sofia.uni.fmi.mjt.gameplatform.store.item.category;


import bg.sofia.uni.fmi.mjt.gameplatform.store.item.AbstractStoreItem;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class GameBundle extends AbstractStoreItem
{
    private Game[] games;

    public Game[] getGames()
    {
        return games;
    }

    public void setGames(Game[] games)
    {
        this.games = games;
    }

    public GameBundle(String title, BigDecimal price, LocalDateTime releaseDate, Game[] games)
    {
        this.setTitle(title);
        this.setPrice(price);
        this.setReleaseDate(releaseDate);
        this.setGames(games);

    }

}
