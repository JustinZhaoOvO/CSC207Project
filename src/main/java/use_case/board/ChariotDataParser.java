package use_case.board;
//CreateTime: 2024-11-10 11:46 p.m.

import entity.BoardConstants;
import entity.Cell;
import entity.ImageConstants;

import java.awt.*;

public class ChariotDataParser {


    public static int[] parsingAPosition(String pos){
        //len(pos) == 2 & pos is a coordinate at chessboard, e.g. e1, g2
        int[] ans = new int[2];
        ans[0] = pos.charAt(0) - BoardConstants.UPPERLEFTLETTER;
        ans[1] = BoardConstants.UPPERLEFTNUMBER - pos.charAt(1);
        return ans;
    }

    public static Cell[][] parsingABoard(String board){
        Cell[][] ans = new Cell[BoardConstants.SIZEOFABOARD][BoardConstants.SIZEOFABOARD];
        String[] rows = board.split("\n");
        for (int i = 0; i < BoardConstants.SIZEOFABOARD; i++) {
            char[] row = rows[i].toCharArray();
            for (int j = 0; j < BoardConstants.SIZEOFABOARD; j++) {
                Color curColor = (i + j) % 2 == 0 ? BoardConstants.EVENCELLCOLOR : BoardConstants.ODDCELLCOLOR;
                Image cur = null;
                switch (row[j*2]) {
                    case BoardConstants.BLACKPAWN -> cur = ImageConstants.BLACKPAWN;
                    case BoardConstants.WHITEPAWN -> cur = ImageConstants.WHITEPAWN;
                    case BoardConstants.BLACKBISHOP -> cur = ImageConstants.BLACKBISHOP;
                    case BoardConstants.WHITEBISHOP -> cur = ImageConstants.WHITEBISHOP;
                    case BoardConstants.BLACKKNIGHT -> cur = ImageConstants.BLACKKNIGHT;
                    case BoardConstants.WHITEKNIGHT -> cur = ImageConstants.WHITEKNIGHT;
                    case BoardConstants.BLACKQUEEN -> cur = ImageConstants.BLACKQUEEN;
                    case BoardConstants.WHITEQUEEN -> cur = ImageConstants.WHITEQUEEN;
                    case BoardConstants.BLACKKING -> cur = ImageConstants.BLACKKING;
                    case BoardConstants.WHITEKING -> cur = ImageConstants.WHITEKING;
                    case BoardConstants.BLACKROOK -> cur = ImageConstants.BLACKROOK;
                    case BoardConstants.WHITEROOK -> cur = ImageConstants.WHITEROOK;
                }ans[i][j] = new Cell(cur, curColor);
            }
        }return ans;
    }
}
