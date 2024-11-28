package data_access;

import entity.User;

import java.util.*;


import java.util.HashMap;
import java.util.Map;

import use_case.search_history.SearchHistoryDataAcessInterface;

public class InMemorySearchHistoryDataAcessObject implements SearchHistoryDataAcessInterface {

    private final Map<String, ArrayList<String>> users = new HashMap<>();


    @Override
    public boolean existsByName(String identifier) {
        return users.containsKey(identifier);
    }

    @Override
    public void save(User user, String SearchText) {
        final String username = user.getName();
        if (!existsByName(username)) {
            users.put(username, new ArrayList<String>());
        }
        users.get(username).add(SearchText);

    }

    @Override
    public ArrayList<String> get(String username) {
        return users.get(username);
    }

}

