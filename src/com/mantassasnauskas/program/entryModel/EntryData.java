package com.mantassasnauskas.program.entryModel;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class EntryData {

    private static EntryData instance = new EntryData();
    private static String filename = "rezultatai.txt";

    private FieldEntry fieldEntryList;
    private DateTimeFormatter formatter;

    private volatile boolean stopper;


    public boolean isStopper() {
        return stopper;
    }

    public void setStopper(boolean stopper) {
        this.stopper = stopper;
    }

    public static EntryData getInstance() {
        return instance;
    }

    private EntryData() {
        formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd\tHH:mm:ss:SSS");
    }

    public FieldEntry getFieldEntryList() {
        return fieldEntryList;
    }

    public void addFieldEntry(FieldEntry entry) {
        fieldEntryList = entry;
    }


    private List<Integer> primeFactors(int numbers) {
        int n = numbers;
        List<Integer> factors = new ArrayList<Integer>();
        for (int i = 2; i <= n / i; i++) {
            while (n % i == 0) {
                factors.add(i);
                n /= i;
            }
        }
        if (n > 1) {
            factors.add(n);
        }
        return factors;
    }

    private static void writeFile(String s) throws IOException {
        FileWriter fw = new FileWriter(filename, true);
        fw.write(s);
        fw.write("\n");
        fw.close();
    }


    public void calculatePrimeIntegers(boolean startStop) throws InterruptedException {


        Runnable task = new Runnable() {
            @Override
            public void run() {
                try {
                    try {

                        String stringFirst = fieldEntryList.getFieldEntryLocalDate().format(formatter) +
                                "\t\tSkaičiavimo pradžia. Naudojami skaičiai: " +
                                fieldEntryList.getFirstNumber() + ", " +
                                fieldEntryList.getLastNumber() + ", " +
                                fieldEntryList.getIntervalNumber() + ".";
                        writeFile(stringFirst);
                    } catch (IOException ex) {
                        System.out.println("Error in closing the FileWriter " + ex);
                    }
                    int currentNumber = fieldEntryList.getFirstNumber();

                    while (currentNumber <= fieldEntryList.getLastNumber() && isStopper()) {
                        List<Integer> currentFactors = primeFactors(currentNumber);
                        StringBuilder stringBuilder = new StringBuilder();
                        Iterator<Integer> iter = currentFactors.iterator();
                        while (iter.hasNext()) {
                            stringBuilder.append(iter.next());
                            if (iter.hasNext()) {
                                stringBuilder.append("*");
                            }
                        }
                        String currentFactorsString = stringBuilder.toString();
                        try {
                            String stringCurrentLine = LocalDateTime.now().format(formatter) +
                                    "\t\t" +
                                    currentNumber + "=" + currentFactorsString;
                            writeFile(stringCurrentLine);
                        } catch (IOException ex) {
                            Thread.currentThread().interrupt();
                            System.out.println("Error in closing the FileWriter " + ex);
                        }
                        try {
                            Thread.sleep(500);

                        } catch (InterruptedException ie) {
                            ie.printStackTrace();
                            Thread.currentThread().interrupt();
                            return;
                        }
                        currentNumber += fieldEntryList.getIntervalNumber();
                    }

                } finally {
                    try {
                        if (isStopper()) {
                            String stringDone = LocalDateTime.now().format(formatter) + "\t\tSkaičiavimo pabaiga.";
                            writeFile(stringDone);
                        }
                    } catch (Exception ex) {
                        System.out.println("Error in closing the FileWriter " + ex);
                    }
                }
            }
        };

        if (startStop) {

            setStopper(false);
            TimeUnit.MILLISECONDS.sleep(1000);
            setStopper(true);
            new Thread(task).start();

        }
    }
}
