package ru.gb.student.test;

public class Asserter {
    public void assertEqualsDouble(double expected, double actual) throws RuntimeException {

        if (expected != actual) {
            throw new RuntimeException("Ожидалось: " + expected  + ". Результат: " + actual);
        }
    }

    public void assertEqualsBoolean(boolean expected, boolean actual) throws RuntimeException {

        if (expected != actual) {
            throw new RuntimeException("Ожидалось: " + expected  + ". Результат: " + actual);
        }
    }

}
