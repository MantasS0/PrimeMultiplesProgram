package com.mantassasnauskas.program;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CalculationService extends Service<ObservableList<String>> {

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd\tHH:mm:ss:SSS");

    private IntegerProperty firstNumber = new SimpleIntegerProperty();
    private IntegerProperty lastNumber = new SimpleIntegerProperty();
    private IntegerProperty intervalNumber = new SimpleIntegerProperty();

    public final void setFirstNumber(Integer value) {
        firstNumber.set(value);
    }

    public final Integer getFirstNumber() {
        return firstNumber.get();
    }

    public final IntegerProperty firstNumberProperty() {
        return firstNumber;
    }

    public final void setLastNumber(Integer value) {
        lastNumber.set(value);
    }

    public final Integer getLastNumber() {
        return lastNumber.get();
    }

    public final IntegerProperty lastNumberProperty() {
        return lastNumber;
    }

    public final void setIntevalNumber(Integer value) {
        intervalNumber.set(value);
    }

    public final Integer getIntevalNumber() {
        return intervalNumber.get();
    }

    public final IntegerProperty intervalNumberProperty() {
        return intervalNumber;
    }

    @Override
    protected Task<ObservableList<String>> createTask() {

        final Integer _firstNumber = getFirstNumber();
        final Integer _lastNumber = getLastNumber();
        final Integer _intervalNumber = getIntevalNumber();

        return new Task<ObservableList<String>>() {

            @Override
            protected ObservableList<String> call() throws Exception {

                int firstNumber = new Integer(_firstNumber);
                int lastNumber = new Integer(_lastNumber);
                int intervalNumber = new Integer(_intervalNumber);
                LocalDateTime currentDate = LocalDateTime.now();

                ObservableList<String> numbers = FXCollections.observableArrayList();
                boolean interupted = false;

                if (firstNumber == -1 || lastNumber == -1 || intervalNumber == -1 || firstNumber > lastNumber) {
                    System.out.println("Įvestas netinkasmas skaičius.");
                    numbers.clear();
                    return numbers;

                } else {

                    try {
                        String stringFirst = currentDate.format(formatter) + "\t\tSkaičiavimo pradžia. Naudojami skaičiai: " +
                                firstNumber + ", " + lastNumber + ", " + intervalNumber + ".";
                        writeFile(stringFirst);

                        List<Integer> allNumbers = new ArrayList<Integer>();
                        int numberToList = firstNumber;
                        while (numberToList <= lastNumber) {
                            allNumbers.add(numberToList);
                            numberToList += intervalNumber;
                        }

                        for (int i = 0; i < allNumbers.size(); i++) {
                            int currentNumberBeingProcessed = allNumbers.get(i);

                            numbers.add(Integer.toString(currentNumberBeingProcessed));
                            updateMessage("Calculating number: " + currentNumberBeingProcessed);

                            List<Integer> currentFactors = primeFactors(currentNumberBeingProcessed);
                            StringBuilder stringBuilder = new StringBuilder();
                            Iterator<Integer> iter = currentFactors.iterator();
                            while (iter.hasNext()) {
                                stringBuilder.append(iter.next());
                                if (iter.hasNext()) {
                                    stringBuilder.append("*");
                                }
                            }
                            String currentFactorsString = stringBuilder.toString();
                            String stringCurrentLine = LocalDateTime.now().format(formatter) + "\t\t" +
                                    currentNumberBeingProcessed + "=" + currentFactorsString;
                            writeFile(stringCurrentLine);

                            updateProgress(i + 1, allNumbers.size());
                            Thread.sleep(500);

                        }
                    } catch (Exception e) {
                        interupted = true;
                        System.out.println(e);

                    } finally {
                        if (interupted){
                            return numbers;
                        }
                        String stringDone = LocalDateTime.now().format(formatter) + "\t\tSkaičiavimo pabaiga.";
                        writeFile(stringDone);
                    }
                    return numbers;

                }

            }
        };
    }

    private List<Integer> primeFactors(int numbers) {
        int n = numbers;
        List<Integer> factors = new ArrayList<Integer>();
        if (numbers == 1){
            factors.add(1);
            return factors;
        }
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

    private void writeFile(String s) throws IOException {
        FileWriter fw = new FileWriter("rezultatai.txt", true);
        fw.write(s);
        fw.write("\n");
        fw.close();
    }


}
