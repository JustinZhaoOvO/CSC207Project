package use_case.board.repaintboard;
//CreateTime: 2024-11-14 4:24 p.m.


import api_adapters.ChariotAPI.ChariotBoard;
import entity.Cell;
import use_case.board.ChariotDataParser;

public class RepaintBoardInteractor implements RepaintBoardInputBoundary {

    private RepaintBoardOutputBoundary presenter;

    public RepaintBoardInteractor(RepaintBoardOutputBoundary presenter) {

        this.presenter = presenter;

    }

    @Override
    public void execute(RepaintBoardInputData data) {
        try {
            ChariotBoard board = data.getBoard();
            Cell[][] cells = ChariotDataParser.parsingABoard(board.toString());
            RepaintBoardOutputData repaintOutputData = new RepaintBoardOutputData(cells);
            this.presenter.prepareSuccessView(repaintOutputData);
        }catch (Exception e) {
            System.out.println(e.getMessage());
            this.presenter.prepareFailView(e.getMessage());
        }
    }
}
