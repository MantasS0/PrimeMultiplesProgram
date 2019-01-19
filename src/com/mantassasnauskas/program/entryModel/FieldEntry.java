package com.mantassasnauskas.program.entryModel;

import java.time.LocalDateTime;

public class FieldEntry {
    private Integer firstNumber;
    private Integer lastNumber;
    private Integer intervalNumber;
    private LocalDateTime fieldEntryLocalDate;

    public FieldEntry(Integer firstNumber, Integer lastNumber, Integer intervalNumber,LocalDateTime fieldEntryLocalDate) {
        this.firstNumber = firstNumber;
        this.lastNumber = lastNumber;
        this.intervalNumber = intervalNumber;
        this.fieldEntryLocalDate = fieldEntryLocalDate;
    }

    public Integer getFirstNumber() {
        return firstNumber;
    }

    public void setFirstNumber(Integer firstNumber) {
        this.firstNumber = firstNumber;
    }

    public Integer getLastNumber() {
        return lastNumber;
    }

    public void setLastNumber(Integer lastNumber) {
        this.lastNumber = lastNumber;
    }

    public Integer getIntervalNumber() {
        return intervalNumber;
    }

    public void setIntervalNumber(Integer intervalNumber) {
        this.intervalNumber = intervalNumber;
    }

    public LocalDateTime getFieldEntryLocalDate() {
        return fieldEntryLocalDate;
    }

    public void setFieldEntryLocalDate(LocalDateTime fieldEntryLocalDate) {
        this.fieldEntryLocalDate = fieldEntryLocalDate;
    }
}
