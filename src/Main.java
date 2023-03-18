import java.util.Scanner;

public class Main {

    static char[] operators = {'+', '-', '/', '*'};
    static char operator;
    static int operand1;
    static int operand2;
    static String[] romanNumbers = {"I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X"};
    static String[] romanOnes = {"", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX"};
    static String[] romanTens = {"", "X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC"};
    static String[] romanHundreds = {"", "C"};
    static int romanNumbersCount = 0;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println(calc(sc.nextLine()));
            romanNumbersCount = 0;

        }
    }

    public static String calc(String input) {
        operator = getOperator(input);
        calculateOperands(input);

        switch (operator) {
            case '+':
                if (romanNumbersCount == 2) {
                    return convertArabicToRoman(operand1 + operand2);
                }
                return String.valueOf(operand1 + operand2);
            case '-':
                if (romanNumbersCount == 2 && (operand1 - operand2) < 0) {
                    throw new RuntimeException("В римской системе нет отрицательных чисел");
                }
                return String.valueOf(operand1 - operand2);
            case '*':
                if (romanNumbersCount == 2) {
                    return convertArabicToRoman(operand1 * operand2);
                }
                return String.valueOf(operand1 * operand2);
            case '/':
                if (romanNumbersCount == 2) {
                    int result = operand1 / operand2;
                    if (result == 0) {
                        throw new RuntimeException("Целочисленный результат деления в римской системе меньше 1");
                    }
                    return convertArabicToRoman(result);
                }
                return String.valueOf(operand1 / operand2);
            default:
                return "";
        }
    }

    static char getOperator(String input) {
        int operatorsCount = 0;
        for (char op : operators) {
            for (int i = 0; i < input.length(); i++) {
                if (input.charAt(i) == op) {
                    operator = op;
                    operatorsCount++;
                }
            }
        }
        if (operatorsCount != 1) {
            throw new RuntimeException("Формат математической операции не удовлетворяет заданию" +
                    " - 2 операнда и 1 оператор");
        }
        return operator;
    }

    static void calculateOperands(String input) {
        String[] operands = input.replaceAll("\\s+", "").toUpperCase().split("[+-/*]");

                for (int i = 0; i < romanNumbers.length; i++) {
            if (operands[0].equals(romanNumbers[i])) {
                operand1 = i + 1;
                romanNumbersCount++;
            }
            if (operands[1].equals(romanNumbers[i])) {
                operand2 = i + 1;
                romanNumbersCount++;
            }
        }
        if (romanNumbersCount == 1) {
            throw new RuntimeException("Используются одновременно разные системы счисления");
        }

        if (romanNumbersCount == 0) {
            try {
                operand1 = Integer.parseInt(operands[0]);
                operand2 = Integer.parseInt(operands[1]);
                if (operand1 > 10 || operand1 < 1 || operand2 > 10 || operand2 < 1) {
                    throw new RuntimeException();
                }

            } catch (Exception ex) {
                throw new RuntimeException("Формат математической операции не удовлетворяет заданию" +
                        " - операнд должен быть в диапозоне от 1 до 10");
            }
        }
    }

    static String convertArabicToRoman(int value) {
        int ones = value % 10;
        int tens = (value / 10) % 10;
        int hundreds = value / 100;
        return romanHundreds[hundreds] + romanTens[tens] + romanOnes[ones];
    }
}
