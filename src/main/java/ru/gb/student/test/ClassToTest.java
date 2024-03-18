package ru.gb.student.test;

import ru.gb.student.test.annotations.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class ClassToTest {
    private FileOutputStream fos;
    private final Asserter asserter = new Asserter();
    private final Calc calc = new Calc();
    private final String num_a;
    private final String num_b;
    private final String operator;
    private final double result;
    private final StringBuilder message = new StringBuilder();
    private boolean testResult = false;
    private boolean testResultGeneral = false;
    private final String FILEINPUTERROR = "Ошибка записи в файл: ";

    public ClassToTest() {
        num_a = "2";
        num_b = "5";
        operator = "*";
        result = 10.0;

    }
    public ClassToTest(String num_a, String num_b, String operator, double result) {
        this.num_a = num_a;
        this.num_b = num_b;
        this.operator = operator;
        this.result = result;
    }
    @BeforeAll
    void beforeAll() {
        try {
            fos = new FileOutputStream("log.txt");
            fos.write("Before All\n".getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            message.append(FILEINPUTERROR).append(e.getMessage());
        }
        System.out.println("Before All\n");
    }
    @BeforeEach
    void beforeEach(){
        message.setLength(0);
        testResult = false;
        try {
            fos.write("Before Each\n".getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            message.append(FILEINPUTERROR).append(e.getMessage());
        }

        System.out.println("Before Each");
    }
    @AfterEach
    void afterEach() {
        if(testResult){
            message.append("Тест успешно пройден\n");
        } else {
            message.append("Тест не пройден\n");
        }
        try {
            fos.write(("After Each\n" + message).getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            message.append(FILEINPUTERROR).append(e.getMessage());
        }
        System.out.println("After Each\n" + message);
    }

    @AfterAll
    void afterAll() {
        message.setLength(0);
        if(testResultGeneral){
            message.append("Результат тестирования положительный\n");
        } else {
            message.append("Результат тестирования отрицательный\n");
        }
        try {
            fos.write(("After All\n" + message).getBytes(StandardCharsets.UTF_8));
            fos.close();
        } catch (IOException e) {
            message.append(FILEINPUTERROR).append(e.getMessage());
        }
        System.out.println("After All\n" + message);
    }

    @Test(order = 1)
    private void test1() {
        try {
            asserter.assertEqualsBoolean(true, calc.setNumA(num_a));
            testResult = true;
            testResultGeneral = true;
        } catch (RuntimeException e) {
            message.append("\n").append(e.getMessage()).append("\n");
            testResult = false;
            testResultGeneral = false;
        }
        try {
            fos.write(("Test 1 Private Check calc.setNumA\n").getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            message.append(FILEINPUTERROR).append(e.getMessage());
        }
        System.out.println("Test 1 Private Check calc.setNumA\n");
    }
    @Test(order = 4)
    protected void test4() {
        try {
            asserter.assertEqualsDouble(result, calc.getResult());
            testResult = true;
            testResultGeneral = true;
        } catch (RuntimeException e) {
            message.append("\n").append(e.getMessage()).append("\n");
            testResult = false;
            testResultGeneral = false;
        }

        try {
            fos.write(("Test 4 Protected Check calc.getResult\n").getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            message.append(FILEINPUTERROR).append(e.getMessage());
        }
        System.out.println("Test 4 Protected Check calc.getResult\n");
    }

    @Test(order = 3)
    public void test3() {
        try {
            asserter.assertEqualsBoolean(true, calc.setNumB(num_b));
            testResult = true;
            testResultGeneral = true;
        } catch (RuntimeException e) {
            message.append("\n").append(e.getMessage()).append("\n");
            testResult = false;
            testResultGeneral = false;
        }
        try {
            fos.write(("Test 3 Public Check calc.setNumB\n").getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            message.append(FILEINPUTERROR).append(e.getMessage());
        }
        System.out.println("Test 3 Public Check calc.setNumB\n");
    }

    @Test(order = 2)
    void test2() {
        try {
            asserter.assertEqualsBoolean(true, calc.setSign(operator));
            testResult = true;
            testResultGeneral = true;
        } catch (RuntimeException e) {
            message.append("\n").append(e.getMessage()).append("\n");
            testResult = false;
            testResultGeneral = false;
        }
        try {
            fos.write(("Test 2 Default Check calc.setSign\n").getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            message.append(FILEINPUTERROR).append(e.getMessage());
        }
        System.out.println("Test 2 Default Check calc.setSign\n");
    }
}
