package repositories;

import model.User;

import java.util.List;

/*
- Метод void create(User user) – создание пользователя и запись его в
файл;
- Метод User findById(String id) — поиск пользователя в файле по
идентификатору;
- Метод List<User> findAll() - выгрузка всех пользователей из файла;
- Метод void update(User user) — обновление полей существующего в
файле пользователя;
- Метод void deleteById(String id) — удаление пользователя по
идентификатору.
- Метод void deleteAll() — удаление всех пользователей.

 */
public interface UserRepository {
    void create(User user);

    List<User> findAll();

    User findById(String id) throws Exception;

    void update(User user);
    void deleteById(String id) throws Exception;
    void deleteAll();

}
