package bg.sofia.uni.fmi.mjt.gameplatform.store;

import bg.sofia.uni.fmi.mjt.gameplatform.store.item.StoreItem;
import bg.sofia.uni.fmi.mjt.gameplatform.store.item.filter.ItemFilter;

import java.math.BigDecimal;

public class GameStore implements StoreAPI
{
    private StoreItem[] availableItems;

    public StoreItem[] getAvailableItems()
    {
        return availableItems;
    }

    public void setAvailableItems(StoreItem[] availableItems)
    {
        this.availableItems = availableItems;
    }
    public GameStore(StoreItem[] availableItems)
    {
        setAvailableItems(availableItems);
    }

    @Override
    public StoreItem[] findItemByFilters(ItemFilter[] itemFilters)
    {
        StoreItem[] res = new StoreItem[availableItems.length];
        int count = 0;
        for (StoreItem item : availableItems)
        {
            boolean matchesAll = true;
            for (ItemFilter filter : itemFilters)
            {
                if(!filter.matches(item))
                {
                    matchesAll = false;
                    break;
                }
            }
            if (matchesAll)
            {
                res[count] = item;
                count++;
            }
        }
        return res;
    }

    @Override
    public void applyDiscount(String promoCode)
    {
        if(promoCode.equals("VAN40"))
        {
            for(StoreItem item : availableItems)
            {
                BigDecimal newPrice = item.getPrice().multiply(BigDecimal.valueOf(0.6));
                item.setPrice(newPrice);
            }
        }
        else if(promoCode.equals("100YO"))
        {
            for (StoreItem item : availableItems)
            {
                item.setPrice(BigDecimal.valueOf(0));
            }
        }
    }

    @Override
    public boolean rateItem(StoreItem item, int rating)
    {
        for(StoreItem product : availableItems)
        {
            if(product.equals(item) && (rating >= 1 && rating <= 5))
            {
                item.rate(rating);
                return true;
            }
        }
        return false;
    }
}
