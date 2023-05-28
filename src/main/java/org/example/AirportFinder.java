package org.example;

public class AirportFinder {

    private FileManager fm = null;
    private AirportFilter filter = null;

    public boolean isValid() {
        return fm != null && fm.isValid;
    }

    private boolean check(Airport airport) {
        return filter == null ? true : filter.check(airport);
    }
    public String airportInfo(AirportPosition airportPosition) {
        return "\"" + airportPosition.name + "\"[" + fm.readLine(airportPosition.position) + "]";
    }

    private int binarySearch(String airportName) {
        int left = 0;
        int right = fm.airports.size() - 1;
        while (left <= right) {
            int mid = (left + right) / 2;
            String name = fm.airports.get(mid).name.toLowerCase();
            if (name.startsWith(airportName)) {
                return mid;
            }
            if (name.compareTo(airportName) < 0) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return -1;
    }

    private void buildResult(AirportFinderResult result, String airportName, int index, int shift) {
        while (true) {
            AirportPosition ap = fm.airports.get(index);
            if (ap.name.toLowerCase().startsWith(airportName)) {
                Airport airoport = new Airport(fm.readLine(ap.position), true);
                if (check(airoport)) {
                    if (shift < 0) {
                        result.airports.add(0, ap);
                    }
                    else {
                        result.airports.add(ap);
                    }
                }
            }
            else {
                break;
            }
            index += shift;
        }
    }
    public AirportFinderResult find(String airportName) {
        AirportFinderResult result = new AirportFinderResult();
        String lowerCaseAirportName = airportName.toLowerCase();
        if (isValid()) {
            long startTime = System.nanoTime();
            int index = binarySearch(lowerCaseAirportName);
            if (index >= 0) {
                buildResult(result, lowerCaseAirportName, index, 1);
                buildResult(result, lowerCaseAirportName, index - 1, -1);
            }
            result.duration = (System.nanoTime() - startTime);
        }
        return  result;
    }


    AirportFinder(FileManager fm, AirportFilter filter) {
        this.fm = fm;
        this.filter = filter;
    }
}