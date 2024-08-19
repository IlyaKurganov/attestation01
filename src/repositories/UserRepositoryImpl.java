package repositories;

import model.User;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class UserRepositoryImpl implements UserRepository {

    public static String FILE_PATH = "C:\\innopoliseducation\\projects\\untitled\\src\\file";

    @Override
    public void create(User user) {
        Scanner sc = new Scanner(System.in);

        String login;
        String password;
        String confirmPassword;
        String surname;
        String name;
        String patronymic;
        Integer age;
        Boolean isWorker;
        try {
            while (true) {
                System.out.println("Введите логин:");
                while (true) {
                    login = sc.nextLine();
                    boolean isLoginUnique = Cache.isLoginUnique(login);
                    if (isLoginUnique && login.matches(Validation.validLogin)) {
                        user.setLogin(login);
                    } else {
                        throw new IllegalArgumentException("Логин не соответствует условиям");
                    }
                    break;
                }
                System.out.println("Введите пароль");
                while (true) {
                    password = sc.nextLine();
                    if (password.matches(Validation.validPassword)) {
                        user.setPassword(password);
                    } else {
                        throw new IllegalArgumentException("Пароль не соответствует условиям");
                    }
                    break;
                }
                System.out.println("Подтвердите пароль");
                while (true) {
                    confirmPassword = sc.nextLine();
                    if (confirmPassword.equals(password)) {
                        user.setConfirmPassword(password);
                    } else {
                        throw new IllegalArgumentException("Пароли не соовпадают условиям");
                    }
                    break;
                }

                System.out.println("Введите фамилию");
                while (true) {
                    surname = sc.nextLine();
                    if (surname.matches(Validation.validPartOfName)) {
                        user.setSurname(surname);
                    } else {
                        throw new IllegalArgumentException("Фамилия должна состоять только из букв");
                    }
                    break;
                }
                System.out.println("Введите имя");
                while (true) {
                    name = sc.nextLine();
                    if (name.matches(Validation.validPartOfName)) {
                        user.setName(name);
                    } else {
                        throw new IllegalArgumentException("Имя должно состоять только из букв");
                    }
                    break;
                }


                boolean needPatronymic = false;
                System.out.println("Добавить отчество? (да/нет)");

                boolean isOnPatronymic = true;
                while (isOnPatronymic) {
                    String answerPatronymic = sc.nextLine();
                    switch (answerPatronymic) {
                        case "да" -> {
                            needPatronymic = true;
                            isOnPatronymic = false;
                        }
                        case "нет" -> {
                            needPatronymic = false;
                            isOnPatronymic = false;
                        }
                        default -> {
                            System.out.println("Введите (да/нет)");
                        }
                    }
                }
                System.out.println("Введите отчество");
                while (needPatronymic) {
                    patronymic = sc.nextLine();
                    if (patronymic.matches(Validation.validPartOfName)) {
                        user.setPatronymic(Optional.of(patronymic));
                    } else {
                        throw new IllegalArgumentException("Отчество должно состоять только из букв");
                    }
                    break;
                }

                boolean needAge = false;
                System.out.println("Добавить возраст? (да/нет)");

                boolean isOnAge = true;
                while (isOnAge) {
                    String answerAge = sc.nextLine();
                    switch (answerAge) {
                        case "да" -> {
                            needAge = true;
                            isOnAge = false;
                        }
                        case "нет" -> {
                            needAge = false;
                            isOnAge = false;
                        }
                        default -> {
                            System.out.println("Введите (да/нет)");
                        }
                    }
                }

                System.out.println("Введите возраст");
                while (needAge) {
                    try {
                        age = Integer.parseInt(sc.nextLine());
                        if (age > 0 && age <= 99) {
                            user.setAge(Optional.of(age));
                        } else {
                            throw new IllegalArgumentException("Возраст должен быть от 1 до 99 включительно");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Введите число");
                        continue;
                    }
                    break;
                }


                System.out.println("Является ли работником? (да/нет)");
                boolean isOnWorker = true;
                while (isOnWorker) {
                    String answerWorker = sc.nextLine();
                    switch (answerWorker) {
                        case "да" -> {
                            isWorker = true;
                            user.setWorker(isWorker);
                            isOnWorker = false;
                        }
                        case "нет" -> {
                            isWorker = false;
                            user.setWorker(isWorker);
                            isOnWorker = false;
                        }
                        default -> {
                            System.out.println("Введите (да/нет)");
                        }
                    }
                }
                Cache.uniqueLogins.add(login);
                Cache.uniqueIds.add(user.getId());
                Cache.users.add(user);

                try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
                    writer.write(user.toString());
                    writer.newLine();
                } catch (IOException e) {
                    throw new RuntimeException("Ошибка при записи файла: " + FILE_PATH, e);
                }
                System.out.println("Пользователь создан!");
                break;
            }

        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            System.out.println("Попробуйте снова.");
        }
    }


    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");

                String id = parts[0];
                LocalDateTime date = LocalDateTime.parse(parts[1], DateTimeFormatter.ISO_DATE_TIME);
                String login = parts[2];
                String password = parts[3];
                String confirmPassword = parts[4];
                String surname = parts[5];
                String name = parts[6];
                Optional<String> patronymic = parts[7].isEmpty() ? Optional.empty() : Optional.of(parts[7]);
                Optional<Integer> age = parts[8].isEmpty() ? Optional.empty() : Optional.of(Integer.parseInt(parts[8]));
                Boolean isWorker = Boolean.parseBoolean(parts[9]);

                if (Cache.isIdUnique(id) && Cache.isLoginUnique(login)) {
                    if (Validation.isValidLogin(login) && Validation.isValidPassword(password) && password.equals(confirmPassword) && Validation.isValidPartOfName(surname) && Validation.isValidPartOfName(name)) {
                        User user = new User();

                        user.setId(id);
                        user.setDate(date);
                        user.setLogin(login);
                        user.setPassword(password);
                        user.setConfirmPassword(confirmPassword);
                        user.setSurname(surname);
                        user.setName(name);
                        user.setPatronymic(patronymic);
                        user.setAge(age);
                        user.setWorker(isWorker);

                        users.add(user);

                        Cache.uniqueIds.add(id);
                        Cache.uniqueLogins.add(login);

                    }

                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return users;

    }

    @Override
    public User findById(String id) throws Exception {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");

                String idFromFile = parts[0];

                if (idFromFile.equals(id)) {
                    for (User u : Cache.users) {
                        if (u.getId().equals(id)) {
                            return u;
                        }
                    }
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        throw new Exception("Пользователя с таким ID (" + id + ") не существует");
    }

    @Override
    public void update(User user) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Обновляем пользователя...");

        String oldLogin = user.getLogin();
        String oldId = user.getId();

        String login;
        String password;
        String confirmPassword;
        String surname;
        String name;
        String patronymic;
        Integer age;
        Boolean isWorker;

        try {
            while (true) {
                System.out.println("Введите логин:");
                while (true) {
                    login = sc.nextLine();
                    boolean isLoginUnique = Cache.isLoginUnique(login);
                    if (isLoginUnique && login.matches(Validation.validLogin)) {
                        user.setLogin(login);

                    } else {
                        throw new IllegalArgumentException("Логин не соответствует условиям");
                    }
                    break;
                }
                System.out.println("Введите пароль");
                while (true) {
                    password = sc.nextLine();
                    if (password.matches(Validation.validPassword)) {
                        user.setPassword(password);
                    } else {
                        throw new IllegalArgumentException("Пароль не соответствует условиям");
                    }
                    break;
                }
                System.out.println("Подтвердите пароль");
                while (true) {
                    confirmPassword = sc.nextLine();
                    if (confirmPassword.equals(password)) {
                        user.setConfirmPassword(password);
                    } else {
                        throw new IllegalArgumentException("Пароли не соовпадают условиям");
                    }
                    break;
                }

                System.out.println("Введите фамилию");
                while (true) {
                    surname = sc.nextLine();
                    if (surname.matches(Validation.validPartOfName)) {
                        user.setSurname(surname);
                    } else {
                        throw new IllegalArgumentException("Фамилия должна состоять только из букв");
                    }
                    break;
                }
                System.out.println("Введите имя");
                while (true) {
                    name = sc.nextLine();
                    if (name.matches(Validation.validPartOfName)) {
                        user.setName(name);
                    } else {
                        throw new IllegalArgumentException("Имя должно состоять только из букв");
                    }
                    break;
                }


                boolean needPatronymic = false;
                System.out.println("Добавить отчество? (да/нет)");

                boolean isOnPatronymic = true;
                while (isOnPatronymic) {
                    String answerPatronymic = sc.nextLine();
                    switch (answerPatronymic) {
                        case "да" -> {
                            needPatronymic = true;
                            isOnPatronymic = false;
                        }
                        case "нет" -> {
                            needPatronymic = false;
                            isOnPatronymic = false;
                        }
                        default -> {
                            System.out.println("Введите (да/нет)");
                        }
                    }
                }
                System.out.println("Введите отчество");
                while (needPatronymic) {
                    patronymic = sc.nextLine();
                    if (patronymic.matches(Validation.validPartOfName)) {
                        user.setPatronymic(Optional.of(patronymic));
                    } else {
                        throw new IllegalArgumentException("Отчество должно состоять только из букв");
                    }
                    break;
                }

                boolean needAge = false;
                System.out.println("Добавить возраст? (да/нет)");

                boolean isOnAge = true;
                while (isOnAge) {
                    String answerAge = sc.nextLine();
                    switch (answerAge) {
                        case "да" -> {
                            needAge = true;
                            isOnAge = false;
                        }
                        case "нет" -> {
                            needAge = false;
                            isOnAge = false;
                        }
                        default -> {
                            System.out.println("Введите (да/нет)");
                        }
                    }
                }

                System.out.println("Введите возраст");
                while (needAge) {
                    try {
                        age = Integer.parseInt(sc.nextLine());
                        if (age > 0 && age <= 99) {
                            user.setAge(Optional.of(age));
                        } else {
                            throw new IllegalArgumentException("Возраст должен быть от 1 до 99 включительно");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Введите число");
                        continue;
                    }
                    break;
                }


                System.out.println("Является ли работником? (да/нет)");
                boolean isOnWorker = true;
                while (isOnWorker) {
                    String answerWorker = sc.nextLine();
                    switch (answerWorker) {
                        case "да" -> {
                            isWorker = true;
                            user.setWorker(isWorker);
                            isOnWorker = false;
                        }
                        case "нет" -> {
                            isWorker = false;
                            user.setWorker(isWorker);
                            isOnWorker = false;
                        }
                        default -> {
                            System.out.println("Введите (да/нет)");
                        }
                    }
                }


                System.out.println("Пользователь обновлен!");

                Cache.uniqueLogins.add(login);
                Cache.uniqueLogins.remove(oldLogin);

                Cache.uniqueIds.add(user.getId());
                Cache.uniqueIds.remove(oldId);

                printAllUsers();
                break;
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            System.out.println("Попробуйте снова.");
        }

    }

    @Override
    public void deleteById(String id) throws Exception {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            boolean idExist = false;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");

                String idFromFile = parts[0];

                if (idFromFile.equals(id)) {
                    Iterator<User> iterator = Cache.users.iterator();
                    while (iterator.hasNext()) {
                        User u = iterator.next();
                        if (u.getId().equals(id)) {
                            iterator.remove();
                            idExist = true;
                            System.out.println("Пользователь удален!");
                        }
                    }
                }
            }
            if (!idExist) {
                throw new Exception("Невозможно удалить. Пользователя с таким ID нет в списке");
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        printAllUsers();

    }

    @Override
    public void deleteAll() {
        Cache.users.clear();

        try (FileWriter fileWriter = new FileWriter(FILE_PATH, false)) {

        } catch (IOException e) {
            throw new RuntimeException("Ошибка при очистке файла", e);
        }
    }

    public void printAllUsers() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (User user : Cache.users) {
                writer.write(user.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при записи файла: " + FILE_PATH, e);
        }
    }
}
