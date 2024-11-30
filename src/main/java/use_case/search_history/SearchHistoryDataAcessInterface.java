package use_case.search_history;

import java.util.ArrayList;

/**
 * DAI for the Search History Use Case.
 */
public interface SearchHistoryDataAcessInterface {

    /**
     * Checks if the user exists by name.
     * @param identifier of the user.
     * @return whether the username exists.
     */
    boolean existsByName(String identifier);

    /**
     * Checks if the user exists by name.
     * @param username of the user.
     * @param SearchText the key topic searched.
     */
    void save(String username, String SearchText);

    /**
     * Checks if the user exists by name.
     * @param username of the user.
     * @return the list of topics searched by the user.
     */
    ArrayList<String> get(String username);

}
