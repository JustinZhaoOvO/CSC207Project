package use_case.board;
//CreateTime: 2024-11-10 11:46 p.m.

import entity.BoardConstants;
import entity.Coordinate;
import entity.ImageConstants;
import view.BoardView.PiecesView.PiecesView;

import java.awt.*;

public class ChariotDataParser {


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
