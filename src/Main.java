import model.User;
import repositories.Cache;
import repositories.UserRepositoryImpl;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        UserRepositoryImpl userRepository = new UserRepositoryImpl();
        Cache.users.addAll(userRepository.findAll());

        Scanner sc = new Scanner(System.in);
        System.out.println("Загружен список из файла:");
        int counter = 0;
        for (User u : Cache.users){
            counter++;
            System.out.println(counter + ". " + u.toString());
        }

        boolean isOn = true;

        while (isOn){
            System.out.println("\n\n");
            System.out.println("Выберите действие: ");
            System.out.println("1. Создать нового пользователя\n" +
                    "2. Обновить пользователя (по ID)\n" +
                    "3. Удалить пользователя по id\n" +
                    "4. Удалить всех пользователей\n" +
                    "5. Завершить программу");

            String action = sc.nextLine();

            switch (action){
                case "1" -> {
                    System.out.println("Давайте создадим пользователя!");
                    userRepository.create(new User());
                }
                case "2" -> {
                    System.out.println("Введите ID пользователя, которого нужно обновить");
                    userRepository.update(userRepository.findById(sc.nextLine()));
                }
                case "3" -> {
                    System.out.println("Введите ID пользователя, которого нужно удалить");
                    userRepository.deleteById(sc.nextLine());
                }
                case "4" -> {
                    userRepository.deleteAll();
                }
                case "5" -> {
                    System.out.println("Программа завершена");
                    isOn = false;
                }
                case "6" -> {
                    for (User u : Cache.users){
                        System.out.println(u.toString());
                    }
                }
                case "7" -> {
                    System.out.println(Cache.uniqueLogins);
                }
                default -> {
                    System.out.println("Введите действие - числом от 1 до 5!");
                }
            }
        }





    }
}
