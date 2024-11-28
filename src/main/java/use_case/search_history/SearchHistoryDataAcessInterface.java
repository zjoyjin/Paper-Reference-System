package use_case.search_history;

import entity.User;

import java.util.ArrayList;

public interface SearchHistoryDataAcessInterface {


    boolean existsByName(String identifier);

    void save(User user, String SearchText);


    ArrayList<String> get(String username);

}
