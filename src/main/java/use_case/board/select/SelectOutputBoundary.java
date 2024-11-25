package use_case.board.select;


public interface SelectOutputBoundary {

    void prepareSuccessView(SelectOutputData outputData);

    void prepareFailView(String errorMessage);
}
