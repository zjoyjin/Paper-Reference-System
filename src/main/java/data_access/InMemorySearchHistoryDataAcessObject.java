package data_access;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import use_case.search_history.SearchHistoryDataAcessInterface;

/**
 * DAO for history data.
 */
public class InMemorySearchHistoryDataAcessObject implements SearchHistoryDataAcessInterface {

    private final Map<String, ArrayList<String>> users = new HashMap<>();

    @Override
    public boolean existsByName(String identifier) {
        return users.containsKey(identifier);
    }

    @Override
    public void save(String username, String SearchText) {
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

