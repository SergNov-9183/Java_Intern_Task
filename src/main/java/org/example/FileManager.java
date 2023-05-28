package org.example;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Collections;

public class FileManager {
    public boolean isValid = true;
    public ArrayList<AirportPosition> airports = new ArrayList<>();

    private RandomAccessFile raf = null;

    public String readLine(long posittion) {
        if (raf != null) {
            try {
                raf.seek(posittion);
                return raf.readLine();
            }
            catch (IOException e) {
                isValid = false;
                raf = null;
                e.printStackTrace();
            }
        }
        return null;
    }
    FileManager(String fileName) {
        try {
            raf = new RandomAccessFile(fileName, "r");
            String line;
            int progress = 0;
            System.out.print("Инициализация ");
            while (true) {
                long position = raf.getFilePointer();
                line = raf.readLine();
                if (line == null) {
                    break;
                }
                Airport airport = new Airport(line, false);
                if (airport.isValid) {
                    airports.add(new AirportPosition(airport.column2, position));
                }
                if (++progress > 100) {
                    progress = 0;
                    System.out.print('.');
                }
            }
            Collections.sort(airports, new AirportPositionComparator());
            System.out.println(".");
        }
        catch (IOException e) {
            isValid = false;
            raf = null;
            e.printStackTrace();
        }
    }
}