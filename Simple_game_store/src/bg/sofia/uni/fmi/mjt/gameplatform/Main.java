package bg.sofia.uni.fmi.mjt.gameplatform;
import bg.sofia.uni.fmi.mjt.gameplatform.store.GameStore;
import bg.sofia.uni.fmi.mjt.gameplatform.store.item.StoreItem;
import bg.sofia.uni.fmi.mjt.gameplatform.store.item.filter.*;
import bg.sofia.uni.fmi.mjt.gameplatform.store.item.category.Game; // or wherever your Game class is

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        // Create some test items
        StoreItem game1 = new Game("Elden Ring", BigDecimal.valueOf(59.99), LocalDateTime.of(2022, 2, 25, 0, 0), "horror");
        StoreItem game2 = new Game("Mario Kart", BigDecimal.valueOf(39.99), LocalDateTime.of(2017, 4, 28, 0, 0), "action");
        StoreItem game3 = new Game("Hollow Knight", BigDecimal.valueOf(14.99), LocalDateTime.of(2017, 2, 24, 0, 0), "interactive");

        // Add them to an array
        StoreItem[] items = { game1, game2, game3 };

        // Create filters
        ItemFilter priceFilter = new PriceItemFilter(BigDecimal.valueOf(10), BigDecimal.valueOf(40));
        ItemFilter titleFilter = new TitleItemFilter("Knight", false);
        ItemFilter[] filters = { priceFilter, titleFilter };

        // Create store
        GameStore store = new GameStore(items);

        // Test filtering
        StoreItem[] results = store.findItemByFilters(filters);

        System.out.println("Filtered results:");
        for (StoreItem item : results) {
            if (item != null) {
                System.out.println(item.getTitle() + " - " + item.getPrice());
            }
        }

        // Test discount
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

