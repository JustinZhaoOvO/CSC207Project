package use_case.signup;

/**
 * The Input Boundary for the Signup Use Case.
 */
public interface SignupInputBoundary {

    /**
     * Executes the Signup Use Case.
     *
     * @param signupInputData the input data
     */
    void execute(SignupInputData signupInputData);
}
