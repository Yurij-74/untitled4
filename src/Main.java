import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите арифметическое выражение:");
        String input = scanner.nextLine();
        try {
            String result = calc(input);
            System.out.println("Результат: " + result);
        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    public static String calc(String input) throws Exception {
        String[] parts = input.split(" ");
        if (parts.length != 3) {
            throw new Exception("Неверный формат ввода");
        }

        String num1 = parts[0];
        String operator = parts[1];
        String num2 = parts[2];

        boolean isArabic = isArabicNumber(num1) && isArabicNumber(num2);
        boolean isRoman = isRomanNumber(num1) && isRomanNumber(num2);

        if (!isArabic && !isRoman) {
            throw new Exception("Числа должны быть либо арабскими, либо римскими");
        }

        int number1 = isArabic ? Integer.parseInt(num1) : romanToArabic(num1);
        int number2 = isArabic ? Integer.parseInt(num2) : romanToArabic(num2);

        if (number1 < 1 || number1 > 10 || number2 < 1 || number2 > 10) {
            throw new Exception("Числа должны быть в диапазоне от 1 до 10");
        }

        int result;
        switch (operator) {
            case "+":
                result = number1 + number2;
                break;
            case "-":
                result = number1 - number2;
                break;
            case "*":
                result = number1 * number2;
                break;
            case "/":
                if (number2 == 0) {
                    throw new Exception("Деление на ноль невозможно");
                }
                result = number1 / number2;
                break;
            default:
                throw new Exception("Неверный оператор");
        }

        return isArabic ? String.valueOf(result) : arabicToRoman(result);
    }

    private static boolean isArabicNumber(String number) {
        try {
            Integer.parseInt(number);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static boolean isRomanNumber(String number) {
        return number.matches("^(M{0,3})(CM|CD|D?C{0,3})(XC|XL|L?X{0,3})(IX|IV|V?I{0,3})$");
    }

    private static int romanToArabic(String number) {
        int result = 0;
        for (int i = 0; i < number.length(); i++) {
            char ch = number.charAt(i);
            if (i > 0 && romanToArabic(number.charAt(i - 1)) < romanToArabic(ch)) {
                result += romanToArabic(ch) - 2 * romanToArabic(number.charAt(i - 1));
            } else {
                result += romanToArabic(ch);
            }
        }
        return result;
    }

    private static int romanToArabic(char ch) {
        switch (ch) {
            case 'I': return 1;
            case 'V': return 5;
            case 'X': return 10;
            case 'L': return 50;
            case 'C': return 100;
            case 'D': return 500;
            case 'M': return 1000;
            default: return -1;
        }
    }
    private static String arabicToRoman(int number) {
        if (number < 1) {
            throw new IllegalArgumentException("Римские числа не могут быть меньше единицы");
        }

        StringBuilder result = new StringBuilder();
        int[] values = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
        String[] symbols = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};

        for (int i = 0; i < values.length; i++) {
            while (number >= values[i]) {
                number -= values[i];
                result.append(symbols[i]);
            }
        }
        return result.toString();
    }
}