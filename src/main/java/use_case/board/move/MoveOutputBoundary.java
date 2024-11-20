package use_case.board.move;

public interface MoveOutputBoundary {

    void prepareSuccessView(MoveOutputData outputData);

    void prepareFailView(String errorMessage);
}
