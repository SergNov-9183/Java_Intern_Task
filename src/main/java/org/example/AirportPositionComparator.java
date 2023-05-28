package org.example;

import java.util.Comparator;

public class AirportPositionComparator implements Comparator<AirportPosition> {

    // override the compare() method
    public int compare(AirportPosition a1, AirportPosition a2) {
        return a1.name.compareTo(a2.name);
    }
}