package use_case.board.repaintboard;
//CreateTime: 2024-11-14 4:24 p.m.


import entity.BoardConstants;
import entity.ChariotBoard;
import entity.Coordinate;
import entity.ImageConstants;
import view.BoardView.PiecesView.PiecesView;

import java.awt.*;

public class RepaintBoardInteractor implements RepaintBoardInputBoundary {

    private final RepaintBoardOutputBoundary presenter;

    public RepaintBoardInteractor(RepaintBoardOutputBoundary presenter) {

        this.presenter = presenter;

    }

    /**
     * convert a chariot board to piecesview components, and update the Board view correspondingly.
     * @param data : a chariot board
     */
    @Override
    public void execute(RepaintBoardInputData data) {

        ChariotBoard board = data.board();
        PiecesView[][] piecesViews = parsingABoard(board.toString());
        RepaintBoardOutputData repaintOutputData = new RepaintBoardOutputData(piecesViews);
        this.presenter.prepareSuccessView(repaintOutputData);

    }

    /**
     * convert a string format board to a 2D board array
     * @param board : a string format board
     * @return : 2D piecesview array, represents the board
     */
    public static PiecesView[][] parsingABoard(String board){
        PiecesView[][] ans = new PiecesView[BoardConstants.SIZEOFABOARD][BoardConstants.SIZEOFABOARD];
        String[] rows = board.split("\n");
        for (int i = 0; i < BoardConstants.SIZEOFABOARD; i++) {
            char[] row = rows[i].toCharArray();
            for (int j = 0; j < BoardConstants.SIZEOFABOARD; j++) {
                Color curColor = (i + j) % 2 == 0 ? BoardConstants.EVENCELLCOLOR : BoardConstants.ODDCELLCOLOR;
                Image curImage = null;
                switch (row[j*2]) {
                    case BoardConstants.BLACKPAWN -> curImage = ImageConstants.BLACKPAWN;
                    case BoardConstants.WHITEPAWN -> curImage = ImageConstants.WHITEPAWN;
                    case BoardConstants.BLACKBISHOP -> curImage = ImageConstants.BLACKBISHOP;
                    case BoardConstants.WHITEBISHOP -> curImage = ImageConstants.WHITEBISHOP;
                    case BoardConstants.BLACKKNIGHT -> curImage = ImageConstants.BLACKKNIGHT;
                    case BoardConstants.WHITEKNIGHT -> curImage = ImageConstants.WHITEKNIGHT;
                    case BoardConstants.BLACKQUEEN -> curImage = ImageConstants.BLACKQUEEN;
                    case BoardConstants.WHITEQUEEN -> curImage = ImageConstants.WHITEQUEEN;
                    case BoardConstants.BLACKKING -> curImage = ImageConstants.BLACKKING;
                    case BoardConstants.WHITEKING -> curImage = ImageConstants.WHITEKING;
                    case BoardConstants.BLACKROOK -> curImage = ImageConstants.BLACKROOK;
                    case BoardConstants.WHITEROOK -> curImage = ImageConstants.WHITEROOK;
                }ans[i][j] = new PiecesView(curColor, curImage, new Coordinate(i, j));
            }
        }return ans;
    }
}
