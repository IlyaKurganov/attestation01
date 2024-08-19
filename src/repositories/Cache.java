package repositories;

import model.User;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Cache {
    public static List<User> users = new ArrayList<>();
    public static HashSet<String> uniqueLogins = new HashSet<>();
    public static HashSet<String> uniqueIds = new HashSet<>();

    public static boolean isLoginUnique(String login){
        for (String str : uniqueLogins){
            if (login.equals(str)){
                return false;
            }
        }
        return true;
    }

    public static boolean isIdUnique(String id){
        for (String str : uniqueIds){
            if (id.equals(str)){
                return false;
            }
        }
        return true;
    }
}
