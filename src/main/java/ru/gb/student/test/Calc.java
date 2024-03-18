package ru.gb.student.test;

public class Calc {
    private String operator;
    private double num_a;
    private double num_b;

    public Calc(){
    }

    public boolean setNumA(String s_num_a){
        try {
            this.num_a = Float.parseFloat(s_num_a);
            return true;
        } catch (Exception e){
            return false;
        }
    }

    public boolean setNumB(String s_num_b){
        try {
            this.num_b = Float.parseFloat(s_num_b);
            return !(operator.equals("/") && num_b == 0);
        } catch (Exception ignore){
            return false;
        }
    }

    public boolean setSign(String operator){
        if (operator.equals("+") || operator.equals("-")
                || operator.equals("*") || operator.equals("/")) {
            this.operator = operator;
            return true;
        } else return false;
    }


    public double getResult() {
        double num_c = 0;
        switch (operator) {
            case "+": num_c = num_a + num_b;
                break;
            case "-": num_c = num_a - num_b;
                break;
            case "*": num_c = num_a * num_b;
                break;
            case "/": num_c = num_a / num_b;
                break;
        }
        return num_c;
    }
}
