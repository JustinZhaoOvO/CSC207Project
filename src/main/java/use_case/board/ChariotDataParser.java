package use_case.board;
//CreateTime: 2024-11-10 11:46 p.m.

import entity.BoardConstants;
import entity.ImageConstants;
import view.BoardView.PiecesView.PiecesView;

import java.awt.*;
import java.util.ArrayList;

public class ChariotDataParser {


    public static int[] parsingAPosition(String pos){
        //len(pos) == 2 & pos is a coordinate at chessboard, e.g. e1, g2
        int[] ans = new int[2];
        ans[0] = pos.charAt(0) - BoardConstants.UPPERLEFTLETTER;
        ans[1] = BoardConstants.UPPERLEFTNUMBER - pos.charAt(1);
        return ans;
    }

    public static String parsingACoordinate(int[] coord){
        String ans = "";
        ans += coord[0] + BoardConstants.UPPERLEFTLETTER;
        ans += BoardConstants.UPPERLEFTNUMBER - coord[1];
        return ans;
    }

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
                }ans[i][j] = new PiecesView(curColor, curImage);
            }
        }return ans;
    }
}
