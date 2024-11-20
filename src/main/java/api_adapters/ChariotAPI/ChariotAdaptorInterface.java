package api_adapters.ChariotAPI;

import chariot.util.Board;

import java.util.List;

public interface ChariotAdaptorInterface {

    List<String> getValidMoves();

    List<String> getValidMovesOfPosition(String position);

    void move(String moveToPlay);

    boolean ended();

    Board.GameState gameState();

    boolean isBlackToMove();

    boolean isPromotionMove(String move);

}
