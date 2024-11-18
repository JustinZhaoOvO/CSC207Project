package use_case.board.repaintboard;

public interface RepaintBoardOutputBoundary {

    void prepareSuccessView(RepaintBoardOutputData outputData);

    void prepareFailView(String errorMessage);
}
