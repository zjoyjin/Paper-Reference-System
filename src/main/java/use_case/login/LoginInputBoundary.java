package use_case.login;

/**
 * Input Boundary for actions which are related to logging in.
 */
public interface LoginInputBoundary {

    /**
     * Executes the login use case.
     * @param loginInputData the input data
     */
    void execute(LoginInputData loginInputData);

    /**
     * Switch to sign up view.
     */
    void switchToSignupView();

    /**
     * Switch to query view.
     */
    void switchToQueryView();
}
