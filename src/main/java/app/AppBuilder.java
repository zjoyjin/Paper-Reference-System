package app;

import java.awt.BorderLayout;
import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import data_access.InMemoryUserDataAccessObject;
import data_access.TestDataAccessObject;
import entity.CommonUserFactory;
import entity.UserFactory;
import interface_adapter.ViewManagerModel;
import interface_adapter.change_password.ChangePasswordController;
import interface_adapter.change_password.ChangePasswordPresenter;
import interface_adapter.change_password.LoggedInViewModel;
import interface_adapter.login.LoginController;
import interface_adapter.login.LoginPresenter;
import interface_adapter.login.LoginViewModel;
import interface_adapter.logout.LogoutController;
import interface_adapter.logout.LogoutPresenter;
import interface_adapter.query.QueryController;
import interface_adapter.query.QueryPresenter;
import interface_adapter.query.QueryViewModel;
import interface_adapter.results.ResultsController;
import interface_adapter.results.ResultsPresenter;
import interface_adapter.results.ResultsViewModel;
import interface_adapter.signup.SignupController;
import interface_adapter.signup.SignupPresenter;
import interface_adapter.signup.SignupViewModel;
import use_case.change_password.ChangePasswordInputBoundary;
import use_case.change_password.ChangePasswordInteractor;
import use_case.change_password.ChangePasswordOutputBoundary;
import use_case.login.LoginInputBoundary;
import use_case.login.LoginInteractor;
import use_case.login.LoginOutputBoundary;
import use_case.logout.LogoutInputBoundary;
import use_case.logout.LogoutInteractor;
import use_case.logout.LogoutOutputBoundary;
import use_case.query.QueryInputBoundary;
import use_case.query.QueryInteractor;
import use_case.query.QueryOutputBoundary;
import use_case.results.ResultsDataAccessInterface;
import use_case.results.ResultsInputBoundary;
import use_case.results.ResultsInteractor;
import use_case.results.ResultsOutputBoundary;
import use_case.signup.SignupInputBoundary;
import use_case.signup.SignupInteractor;
import use_case.signup.SignupOutputBoundary;
import view.LoggedInView;
import view.LoginView;
import view.QueryView;
import view.ResultsView;
import view.SignupView;
import view.ViewManager;

/**
 * The AppBuilder class is responsible for putting together the pieces of
 * our CA architecture; piece by piece.
 * <p/>
 * This is done by adding each View and then adding related Use Cases.
 */
public class AppBuilder {
    private final JPanel cardPanel = new JPanel();
    private final CardLayout cardLayout = new CardLayout();
    // thought question: is the hard dependency below a problem?
    private final UserFactory userFactory = new CommonUserFactory();
    private final ViewManagerModel viewManagerModel = new ViewManagerModel();
    private final ViewManager viewManager = new ViewManager(cardPanel, cardLayout, viewManagerModel);

    // thought question: is the hard dependency below a problem?
    private final InMemoryUserDataAccessObject userDataAccessObject = new InMemoryUserDataAccessObject();

    private final ResultsDataAccessInterface queryDao = new TestDataAccessObject();

    private SignupView signupView;
    private SignupViewModel signupViewModel;
    private LoginViewModel loginViewModel;
    private LoggedInViewModel loggedInViewModel;
    private LoggedInView loggedInView;
    private LoginView loginView;
    private QueryView queryView;
    private QueryViewModel queryViewModel;
    private QueryController queryController;
    private ResultsView resultsView;
    private ResultsViewModel resultsViewModel;

    private ResultsController resultsController;
    private ResultsOutputBoundary resultsOutputBoundary;
    private ResultsInputBoundary resultsInteractor;

    public AppBuilder() {
        cardPanel.setLayout(cardLayout);
    }

    /**
     * Adds the Signup View to the application.
     * @return this builder
     */
    public AppBuilder addSignupView() {
        signupViewModel = new SignupViewModel();
        signupView = new SignupView(signupViewModel);

        //        cardPanel.setLayout(new BorderLayout());
        //        signupView.setBorder(null);

        cardPanel.add(signupView, signupView.getViewName());

        //        cardPanel.add(signupView, signupView.getViewName());
        return this;
    }

    /**
     * Adds the Login View to the application.
     * @return this builder
     */
    public AppBuilder addLoginView() {
        loginViewModel = new LoginViewModel();
        loginView = new LoginView(loginViewModel);
        cardPanel.add(loginView, loginView.getViewName());
        return this;
    }

    /**
     * Adds the LoggedIn View to the application.
     * @return this builder
     */
    public AppBuilder addLoggedInView() {
        loggedInViewModel = new LoggedInViewModel();
        queryViewModel = new QueryViewModel();
        //        LoggedInView loggedInView = new LoggedInView(loggedInViewModel, queryViewModel);
        this.loggedInView = new LoggedInView(loggedInViewModel, queryViewModel);

        cardPanel.add(loggedInView, loggedInView.getViewName());
        return this;

    }

    /**
     * Adds the Query View to the application.
     * @return this builder
     */
    public AppBuilder addQueryView() {
        queryViewModel = new QueryViewModel();
        this.queryView = new QueryView(queryViewModel);
        cardPanel.add(queryView, queryView.getViewName());
        return this;
    }

    /**
     * Adds the Query View to the application.
     * @return this builder
     */
    public AppBuilder addResultsView() {
        resultsViewModel = new ResultsViewModel();
        this.resultsView = new ResultsView(resultsViewModel);
        cardPanel.add(resultsView, resultsView.getViewName());
        return this;

    }

    /**
     * Adds the Signup Use Case to the application.
     * @return this builder
     */
    public AppBuilder addSignupUseCase() {
        final SignupOutputBoundary signupOutputBoundary = new SignupPresenter(viewManagerModel,
                signupViewModel, loginViewModel);
        final SignupInputBoundary userSignupInteractor = new SignupInteractor(
                userDataAccessObject, signupOutputBoundary, userFactory);

        final SignupController controller = new SignupController(userSignupInteractor);
        signupView.setSignupController(controller);
        return this;
    }

    /**
     * Adds the Login Use Case to the application.
     * @return this builder
     */
    public AppBuilder addLoginUseCase() {
        final LoginOutputBoundary loginOutputBoundary = new LoginPresenter(viewManagerModel,
                loggedInViewModel, loginViewModel, queryViewModel);
        final LoginInputBoundary loginInteractor = new LoginInteractor(
                userDataAccessObject, loginOutputBoundary);

        final LoginController loginController = new LoginController(loginInteractor);
        loginView.setLoginController(loginController);
        return this;
    }

    /**
     * Adds the Change Password Use Case to the application.
     * @return this builder
     */
    public AppBuilder addChangePasswordUseCase() {
        final ChangePasswordOutputBoundary changePasswordOutputBoundary =
                new ChangePasswordPresenter(loggedInViewModel);

        final ChangePasswordInputBoundary changePasswordInteractor =
                new ChangePasswordInteractor(userDataAccessObject, changePasswordOutputBoundary, userFactory);

        final ChangePasswordController changePasswordController =
                new ChangePasswordController(changePasswordInteractor);
        loggedInView.setChangePasswordController(changePasswordController);
        return this;
    }

    /**
     * Adds the Logout Use Case to the application.
     * @return this builder
     */
    public AppBuilder addLogoutUseCase() {
        final LogoutOutputBoundary logoutOutputBoundary = new LogoutPresenter(viewManagerModel,
                loggedInViewModel, loginViewModel);

        final LogoutInputBoundary logoutInteractor =
                new LogoutInteractor(userDataAccessObject, logoutOutputBoundary);

        final LogoutController logoutController = new LogoutController(logoutInteractor);
        loggedInView.setLogoutController(logoutController);
        return this;
    }

    /**
     * Adds the Query Use Case to the application.
     * @return this builder
     */
    public AppBuilder addQueryUseCase() {
        // resultsViewModel = new ResultsViewModel();
        final QueryOutputBoundary queryOutputBoundary = new QueryPresenter(viewManagerModel, queryViewModel);
        final QueryInputBoundary queryInteractor = new QueryInteractor(queryOutputBoundary);

        this.queryController = new QueryController(queryInteractor, queryDao);
        queryView.setQueryController(queryController);
        loggedInView.setQueryController(queryController);

        queryView.setResultsController(resultsController);

        return this;
    }

    /**
     * Adds the Results Use Case to the application.
     * @return this builder
     */
    public AppBuilder addResultsUseCase() {
        // resultsViewModel = new ResultsViewModel();
        // alr init in queryViewModel  (switched to resultsView)

        // final ResultsOutputBoundary resultsOutputBoundary = new ResultsPresenter(resultsViewModel, viewManagerModel);
        // final ResultsInputBoundary resultsInteractor = new ResultsInteractor(queryDao, resultsOutputBoundary);
        // final ResultsController resultsController = new ResultsController(resultsInteractor);
        resultsOutputBoundary = new ResultsPresenter(resultsViewModel, viewManagerModel);
        resultsInteractor = new ResultsInteractor(queryDao, resultsOutputBoundary);

        resultsController = new ResultsController(resultsInteractor);

        resultsView.setResultsController(resultsController);
        return this;
    }

    /**
     * Creates the JFrame for the application and initially sets the SignupView to be displayed.
     * @return the application
     */
    public JFrame build() {
        final JFrame application = new JFrame("Login Example");
        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        cardPanel.setBorder(null);

        application.add(cardPanel, BorderLayout.CENTER);
        //        cardPanel.setLayout(new BorderLayout());
        viewManagerModel.setState(signupView.getViewName());
        viewManagerModel.firePropertyChanged();

        return application;
    }
}
