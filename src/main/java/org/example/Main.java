package org.example;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        String fileNaame = "/home/sergey/Documents/airports.csv";
        System.out.print("Введите фильтр: ");
        Scanner scanner = new Scanner(System.in);
        String filter = scanner.nextLine();
        AirportFinder airportFinder = new AirportFinder(new FileManager(fileNaame), new AirportFilter(filter));
        if (airportFinder.isValid()) {
            while (true) {
                System.out.print("Введите '!quit' для завершения программы или начало имени аэропорта: ");
                String input = scanner.nextLine();
                if (input.equalsIgnoreCase("!quit")) {
                    break;
                }
                AirportFinderResult airports = airportFinder.find(input);
                for (AirportPosition airport : airports.airports) {
                    System.out.println(airportFinder.airportInfo(airport));
                }
                System.out.println("Количество найденных строк: " + airports.airports.size() + " Время затраченное на поиск: " + airports.duration / 1000000 + " мс");
            }
        }
        System.out.println("Программа завершена.");
    }
}
