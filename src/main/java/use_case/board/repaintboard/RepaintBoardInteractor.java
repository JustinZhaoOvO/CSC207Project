package use_case.board.repaintboard;
//CreateTime: 2024-11-14 4:24 p.m.


import api_adapters.ChariotAPI.ChariotBoard;
import use_case.board.ChariotDataParser;
import view.BoardView.PiecesView.PiecesView;

public class RepaintBoardInteractor implements RepaintBoardInputBoundary {

    private RepaintBoardOutputBoundary presenter;

    public RepaintBoardInteractor(RepaintBoardOutputBoundary presenter) {

        this.presenter = presenter;

    }

    @Override
    public void execute(RepaintBoardInputData data) {

        ChariotBoard board = data.board();
        PiecesView[][] piecesViews = ChariotDataParser.parsingABoard(board.toString());
        RepaintBoardOutputData repaintOutputData = new RepaintBoardOutputData(piecesViews);
        this.presenter.prepareSuccessView(repaintOutputData);

    }
}
