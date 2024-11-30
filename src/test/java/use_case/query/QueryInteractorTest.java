package use_case.query;

import data_access.InMemorySearchHistoryDataAcessObject;
import data_access.InMemoryUserDataAccessObject;
import entity.CommonUserFactory;
import entity.User;
import entity.UserFactory;
import interface_adapter.query.QueryPresenter;
import org.junit.jupiter.api.Test;
import use_case.login.*;
import use_case.search_history.SearchHistoryDataAcessInterface;

import javax.management.Query;
import javax.swing.*;

import java.sql.Array;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class QueryInteractorTest {
    @Test
    void successTest() {
        SearchHistoryDataAcessInterface sHRepository = new InMemorySearchHistoryDataAcessObject();

        // For the success test, we need to add Paul to the data access repository before we log in.
        UserFactory factory = new CommonUserFactory();
        User user = factory.create("mr.bwz", "password");

        QueryInputData inputData1 = new QueryInputData("PSG", "mr.bwz");
        QueryInputData inputData2 = new QueryInputData("Lyon", "mr.bwz");
//        sHRepository.save(inputData1.getUsername(), inputData1.getTopic());
//        sHRepository.save(inputData2.getUsername(), inputData2.getTopic());

        ArrayList<String> test_arr = new ArrayList<>();
        test_arr.add("PSG");
        test_arr.add("Lyon");

        // This creates a successPresenter that tests whether the test case is as we expect.
        QueryOutputBoundary successPresenter = new QueryOutputBoundary() {
            @Override
            public void showSearchHistory(ArrayList<String> arr, JTextField queryInputField, JPopupMenu popupMenu) {
                assertEquals(test_arr, arr);
            }

            @Override
            public void prepareSuccessView(QueryOutputData outputData){};

            @Override
            public void switchToResultsView(){};

            @Override
            public void switchToQueryView(){};

            @Override
            public void switchToLoggedInView(){};

        };
        QueryInputBoundary interactor = new QueryInteractor(successPresenter, sHRepository);
        interactor.execute(inputData1);
        interactor.execute(inputData2);
    }

}
