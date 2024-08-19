package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public class User {
    String id = UUID.randomUUID().toString();
    LocalDateTime date = LocalDateTime.now();
    String login;
    String password;
    String confirmPassword;
    String surname;
    String name;
    Optional<String> patronymic = Optional.empty();
    Optional<Integer> age = Optional.empty();
    Boolean isWorker;

    public User() {
    }

    public User(String login, String password, String confirmPassword, String surname, String name, Optional<String> patronymic, Optional<Integer> age, Boolean isWorker) {
        this.login = login;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.surname = surname;
        this.name = name;
        this.patronymic = patronymic;
        this.age = age;
        this.isWorker = isWorker;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public LocalDateTime getDate() {
        return date.withNano(0);
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Optional<String> getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(Optional<String> patronymic) {
        this.patronymic = patronymic;
    }

    public Optional<Integer> getAge() {
        return age;
    }

    public void setAge(Optional<Integer> age) {
        this.age = age;
    }

    public Boolean getWorker() {
        return isWorker;
    }

    public void setWorker(Boolean worker) {
        isWorker = worker;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;
        return Objects.equals(id, user.id) && Objects.equals(date, user.date) && Objects.equals(login, user.login) && Objects.equals(password, user.password) && Objects.equals(confirmPassword, user.confirmPassword) && Objects.equals(surname, user.surname) && Objects.equals(name, user.name) && Objects.equals(patronymic, user.patronymic) && Objects.equals(age, user.age) && Objects.equals(isWorker, user.isWorker);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, login, password, confirmPassword, surname, name, patronymic, age, isWorker);
    }

    @Override
    public String toString() {
        return String.join("|",
                this.getId(),
                this.getDate().format(DateTimeFormatter.ISO_DATE_TIME),
                this.getLogin(),
                this.getPassword(),
                this.getConfirmPassword(),
                this.getSurname(),
                this.getName(),
                this.getPatronymic().orElse(""),
                this.getAge().map(String::valueOf).orElse(""),
                this.getWorker().toString()
        );
    }
}

