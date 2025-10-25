package bg.sofia.uni.fmi.mjt.gameplatform.store.item.category;

import bg.sofia.uni.fmi.mjt.gameplatform.store.item.AbstractStoreItem;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class DLC extends AbstractStoreItem
{
    private Game game;

    public Game getGame() {
        return game;
    }

    public void setGame(Game game)
    {
        this.game = game;
    }

    public DLC(String title, BigDecimal price, LocalDateTime releaseDate, Game game)
    {
        this.setTitle(title);
        this.setPrice(price);
        this.setReleaseDate(releaseDate);
        this.setGame(game);
    }

}
