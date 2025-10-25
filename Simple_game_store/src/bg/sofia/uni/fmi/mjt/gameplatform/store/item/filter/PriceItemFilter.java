package bg.sofia.uni.fmi.mjt.gameplatform.store.item.filter;

import java.math.BigDecimal;
import bg.sofia.uni.fmi.mjt.gameplatform.store.item.StoreItem;


public class PriceItemFilter implements ItemFilter
{
    private BigDecimal lowerBound;
    private BigDecimal upperBound;

    public BigDecimal getLowerBound()
    {
        return lowerBound;
    }

    public void setLowerBound(BigDecimal lowerBound)
    {
        this.lowerBound = lowerBound;
    }

    public BigDecimal getUpperBound()
    {
        return upperBound;
    }

    public void setUpperBound(BigDecimal upperBound)
    {
        this.upperBound = upperBound;
    }

    public PriceItemFilter(BigDecimal lowerBound, BigDecimal upperBound)
    {
        this.setLowerBound(lowerBound);
        this.setUpperBound(upperBound);
    }

    @Override
    public boolean matches(StoreItem item)
    {
        return (item.getPrice().compareTo(lowerBound) >= 0 && item.getPrice().compareTo(upperBound) <= 0);
    }

}
