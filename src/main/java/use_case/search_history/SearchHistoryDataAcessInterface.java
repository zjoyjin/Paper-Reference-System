package use_case.search_history;

import entity.User;

import java.util.ArrayList;

public interface SearchHistoryDataAcessInterface {


    boolean existsByName(String identifier);

    void save(String username, String SearchText);


    ArrayList<String> get(String username);

}