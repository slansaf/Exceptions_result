import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;

public class PersonDataApp {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("Выберите действие:\n" +
                    "1. Ввести данные пользователя\n" +
                    "2. Выход");

            int choice = getIntInput();
            if (choice == 1) {
                processPersonData();
            } else if (choice == 2) {
                System.out.println("Завершение программы.");
                return;
            } else {
                System.out.println("Некорректный выбор. Попробуйте еще раз.");
            }
        }
    }

    private static void processPersonData() {
        System.out.println("Введите данные в формате: Фамилия Имя Отчество датарождения номертелефона пол");
        String input = scanner.nextLine();
        String[] data = input.split(" ");

        if (data.length != 6) {
            System.out.println("Ошибка: Вы ввели неверное количество данных.");
            return;
        }

        try {
            String lastName = data[0];
            String firstName = data[1];
            String middleName = data[2];
            String birthDate = data[3];
            long phoneNumber = getPhoneNumber(data[4]);
            char gender = getGender(data[5]);

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
            Date date = parseDate(birthDate);
            String fileName = lastName + ".txt";
            writeToFile(lastName, firstName, middleName, date, phoneNumber, gender);
            System.out.println("Данные успешно записаны в файл " + lastName);
        } catch (NumberFormatException e) {
            System.out.println("Ошибка: Неверный формат номера телефона.");
        } catch (ParseException e) {
            System.out.println("Ошибка: Неверный формат даты рождения.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println("Ошибка при работе с файлом: " + e.getMessage());
        }
    }

    private static int getIntInput() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Ошибка: Введено неверное значение. Попробуйте еще раз.");
            }
        }
    }

    private static long getPhoneNumber(String input) {
        try {
            return Long.parseLong(input);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Ошибка: Неверный формат номера телефона.");
        }
    }

    private static char getGender(String input) {
        char gender = input.charAt(0);
        if (gender != 'f' && gender != 'm') {
            throw new IllegalArgumentException("Ошибка: Неверный формат пола.");
        }
        return gender;
    }

    private static Date parseDate(String input) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        return dateFormat.parse(input);
    }

    private static void writeToFile(String lastName, String firstName, String middleName, Date birthDate, long phoneNumber, char gender) throws IOException {
        String fileName =  lastName + ".txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))){
            writer.write(lastName + " " + firstName + " " + middleName + " " + birthDate + " " + phoneNumber + " " + gender);
            writer.newLine();
        } catch (IOException e){
            System.out.println("Ошибка записи!");
        }
    }
}