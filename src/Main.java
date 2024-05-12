import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNextLine()) {

            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                break;
            }

            String[] parts = input.split(" ");

            if(parts.length != 3) {
                System.out.println("Неверный формат ввода");
                continue;
            }

            int num1, num2;
            try {
                if (isRoman(parts[0])) {
                    num1 = romanToArabic(parts[0]);
                } else {
                    num1 = Integer.parseInt(parts[0]);
                }

                if (isRoman(parts[2])) {
                    num2 = romanToArabic(parts[2]);
                } else {
                    num2 = Integer.parseInt(parts[2]);
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Ошибка: " + e.getMessage());
                continue;
            }

            char operator = parts[1].charAt(0);
            if(operator != '+' && operator != '-' && operator != '*' && operator != '/') {
                System.out.println("Неверная арифметическая операция");
                continue;
            }

            boolean isRoman1 = isRoman(parts[0]);
            boolean isRoman2 = isRoman(parts[2]);

            if (isRoman1 != isRoman2) {
                System.out.println("Нельзя использовать одновременно разные системы счисления");
                continue;
            }

            if (isRoman1 && (num1 <= 0 || num2 <= 0)) {
                System.out.println("Римские числа могут быть только положительными");
                continue;
            }

            int result;
            switch (operator) {
                case '+':
                    result = num1 + num2;
                    break;
                case '-':
                    result = num1 - num2;
                    break;
                case '*':
                    result = num1 * num2;
                    break;
                case '/':
                    if(num2 == 0) {
                        System.out.println("Деление на ноль");
                        continue;
                    }
                    result = num1 / num2;
                    break;
                default:
                    System.out.println("Неверная арифметическая операция");
                    continue;
            }

            if (isRoman1) {
                System.out.println(toRoman(result));
            } else {
                System.out.println( result);
            }
        }
        System.out.println("Работа программы завершена.");
    }

    private static boolean isRoman(String input) {
        String romanNumeralPattern = "^M{0,3}(CM|CD|D?C{0,3})(XC|XL|L?X{0,3})(IX|IV|V?I{0,3})$";
        return input.matches(romanNumeralPattern);
    }

    private static int romanToArabic(String roman) {
        Map<Character, Integer> romanMap = new HashMap<>();
        romanMap.put('I', 1);
        romanMap.put('V', 5);
        romanMap.put('X', 10);
        romanMap.put('L', 50);
        romanMap.put('C', 100);

        int result = 0;
        int prevValue = 0;
        for (int i = roman.length() - 1; i >= 0; i--) {
            int value = romanMap.get(roman.charAt(i));
            if (value < prevValue) {
                result -= value;
            } else {
                result += value;
            }
            prevValue = value;
        }
        return result;
    }

    private static String toRoman(int number) {
        if (number < 1) {
            throw new IllegalArgumentException("Нельзя представить отрицательные числа в римской системе");
        }

        String[] romanNumerals = {"I", "IV", "V", "IX", "X", "XL", "L", "XC", "C"};
        int[] values = {1, 4, 5, 9, 10, 40, 50, 90, 100};

        StringBuilder roman = new StringBuilder();
        for (int i = values.length - 1; i >= 0; i--) {
            while (number >= values[i]) {
                number -= values[i];
                roman.append(romanNumerals[i]);
            }
        }
        return roman.toString();
    }
}
