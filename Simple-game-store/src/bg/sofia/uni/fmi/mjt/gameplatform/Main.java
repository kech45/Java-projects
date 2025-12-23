package bg.sofia.uni.fmi.mjt.gameplatform;
import bg.sofia.uni.fmi.mjt.gameplatform.store.GameStore;
import bg.sofia.uni.fmi.mjt.gameplatform.store.item.StoreItem;
import bg.sofia.uni.fmi.mjt.gameplatform.store.item.filter.*;
import bg.sofia.uni.fmi.mjt.gameplatform.store.item.category.Game;
import bg.sofia.uni.fmi.mjt.gameplatform.store.item.category.GameBundle;
import bg.sofia.uni.fmi.mjt.gameplatform.store.item.category.DLC;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args)
    {
        StoreItem game1 = new Game("Elden Ring", BigDecimal.valueOf(59.99), LocalDateTime.of(2022, 2, 25, 0, 0), "horror");
        StoreItem game2 = new Game("Mario Kart", BigDecimal.valueOf(39.99), LocalDateTime.of(2017, 4, 28, 0, 0), "action");
        StoreItem game3 = new Game("Hollow Knight", BigDecimal.valueOf(14.99), LocalDateTime.of(2017, 2, 24, 0, 0), "interactive");

        StoreItem game4 = new Game("GTA San Andreas", BigDecimal.valueOf(19.99), LocalDateTime.of(2004, 10, 26, 0, 0), "action");
        StoreItem game5 = new Game("GTA Vice City", BigDecimal.valueOf(12.99), LocalDateTime.of(2002, 10, 27, 0, 0), "action");
        StoreItem game6 = new Game("GTA III", BigDecimal.valueOf(8.99), LocalDateTime.of(2001, 10, 22, 0, 0), "action");

        StoreItem bundle = new GameBundle("GTA Trilogy", BigDecimal.valueOf(25.00), LocalDateTime.of(2007, 10, 23, 0, 0), new Game[]{(Game) game4, (Game) game5, (Game) game6});

        StoreItem dlc1 = new DLC("San Andreas Stories", BigDecimal.valueOf(6.50), LocalDateTime.of(2005, 10, 19, 0, 0), (Game) game4);

        StoreItem[] items = { game1, game2, game3, game4, game5, game6, bundle, dlc1};

        ItemFilter priceFilter = new PriceItemFilter(BigDecimal.valueOf(10), BigDecimal.valueOf(40));
        ItemFilter titleFilter = new TitleItemFilter("Knight", false);
        ItemFilter[] filters = { priceFilter, titleFilter };

        GameStore store = new GameStore(items);
        StoreItem[] results = store.findItemByFilters(filters);

        System.out.println("Filtered results:");
        for (StoreItem item : results) {
            if (item != null) {
                System.out.println(item.getTitle() + " - " + item.getPrice());
            }
        }

        // Discount tests
        store.applyDiscount("VAN40");
        System.out.println("\nAfter applying VAN40 discount:");
        for (StoreItem item : store.getAvailableItems()) {
            System.out.println(item.getTitle() + " - " + item.getPrice());
        }

        store.applyDiscount("stoyo");
        System.out.println("\nAfter applying stoyo discount:");
        for (StoreItem item : store.getAvailableItems()) {
            System.out.println(item.getTitle() + " - " + item.getPrice());
        }

        store.applyDiscount("100YO");
        System.out.println("\nAfter applying 100YO discount:");
        for (StoreItem item : store.getAvailableItems()) {
            System.out.println(item.getTitle() + " - " + item.getPrice());
        }

        // Test rating
        store.rateItem(game3, 5);
        System.out.println("\nAfter rating Hollow Knight: " + game3.getRating());
    }
}

