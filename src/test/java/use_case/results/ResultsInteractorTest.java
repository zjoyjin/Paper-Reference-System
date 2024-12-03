package use_case.results;

import data_access.TestDataAccessObject;
import entity.Article;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ResultsInteractorTest {

    @Test
    void successTest() {
        ResultsInputData inputData = new ResultsInputData("hippo and jnk");
        ResultsDataAccessInterface apiAccess = new TestDataAccessObject();

        // For the success test, we need to add Paul to the data access repository before we log in.
        apiAccess.get(inputData.getQuery());

        // This creates a successPresenter that tests whether the test case is as we expect.
        ResultsOutputBoundary successPresenter = new ResultsOutputBoundary() {
            @Override
            public void prepareSuccessView(ResultsOutputData results) {
                final Set<String> dois = new HashSet<>();
                for (Article article : results.getArticles()) {
                    dois.add(article.getDoi());
                }
                Set<String> expected = new HashSet<>(Arrays.asList(
                        "10.3934/genet.2014.1.20", "10.1016/j.cub.2014.07.034", "10.1126/scisignal.2004324",
                        "10.1101/2024.06.19.599466", "10.1111/cpr.12529", "10.1038/nrm3677",
                        "10.1016/j.mod.2016.07.001","10.1371/journal.pone.0151251", "10.1038/embor.2012.185",
                        "10.1016/j.gendis.2022.02.020"));

                assertEquals(expected, dois);
                assertEquals(7, results.getEdges().size());
                assertFalse(results.isUseCaseFailed());
            }

            @Override
            public void switchToQueryView() {
                // not relevant to this test
            }
        };

        ResultsInputBoundary interactor = new ResultsInteractor(apiAccess, successPresenter);
        interactor.execute(inputData);
    }

    @Test
    void switchToQueryViewTest() {
        ResultsInputData inputData = new ResultsInputData("hippo and jnk");
        ResultsDataAccessInterface apiAccess = new TestDataAccessObject();

        // For the success test, we need to add Paul to the data access repository before we log in.
        apiAccess.get(inputData.getQuery());

        // This creates a successPresenter that tests whether the test case is as we expect.
        ResultsOutputBoundary successPresenter = new ResultsOutputBoundary() {
            @Override
            public void prepareSuccessView(ResultsOutputData results) {
                // not relevant for this test
            }

            @Override
            public void switchToQueryView() {
                // nothing to check; just make sure it doesn't crash haha
            assertTrue(true);
            }
        };

        ResultsInputBoundary interactor = new ResultsInteractor(apiAccess, successPresenter);
        interactor.switchToQueryView();
    }

}