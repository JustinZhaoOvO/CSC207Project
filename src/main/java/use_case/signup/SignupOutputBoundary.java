package use_case.signup;

/**
 * The Output Boundary for the Signup Use Case.
 */
public interface SignupOutputBoundary {

    void prepareSuccessView(SignupOutputData response);

    /**
     * Prepares the success view for the Signup Use Case.
     *
     * @param outputData the output data
     * @param isPlayer1  indicates whether it's Player 1
     */
    void prepareSuccessView(SignupOutputData outputData, boolean isPlayer1);

    /**
     * Prepares the failure view for the Signup Use Case.
     *
     * @param errorMessage the explanation of the failure
     * @param isPlayer1    indicates whether it's Player 1
     */
    void prepareFailView(String errorMessage, boolean isPlayer1);
}
