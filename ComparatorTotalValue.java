package unl.cse;

import java.util.Comparator;

public class ComparatorTotalValue implements Comparator<Portfolio>{
	@Override
    public int compare(Portfolio item1, Portfolio item2) {
        if (item1 == null || item2 == null) {
            throw new NullPointerException("Failed attempt to compare " + item1 + " to " + item2);
        }
        else if (!item1.getClass().equals(item2.getClass())) {
            throw new ClassCastException("Possible ClassLoader issue. Failed attempt to compare " + item1 + " to " + item2);
        }
        return (int)(item1.getTotalValue() - item2.getTotalValue());
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof ComparatorTotalValue;
    }
}
