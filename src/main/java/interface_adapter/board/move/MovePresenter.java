package interface_adapter.board.move;
//CreateTime: 2024-11-20 3:28 p.m.

import interface_adapter.BoardStateConstants;
import interface_adapter.board.BoardState;
import interface_adapter.board.BoardViewModel;
import interface_adapter.board.repaintboard.RepaintBoardController;
import interface_adapter.board.select.SelectController;
import interface_adapter.window.WindowState;
import interface_adapter.window.WindowViewModel;
import use_case.board.move.MoveOutputBoundary;
import use_case.board.move.MoveOutputData;

import java.awt.*;

public class MovePresenter implements MoveOutputBoundary {

    private final BoardViewModel boardViewModel;
    private final RepaintBoardController repaintBoardController;
    private final SelectController selectController;
    private final WindowViewModel windowViewModel;

    public MovePresenter(BoardViewModel boardViewModel,
                         RepaintBoardController repaintBoardController,
                         SelectController selectController, WindowViewModel windowViewModel) {
        this.boardViewModel = boardViewModel;

        this.repaintBoardController = repaintBoardController;
        this.selectController = selectController;
        this.windowViewModel = windowViewModel;
    }

    @Override
    public void prepareSuccessView(MoveOutputData outputData) {
        if (outputData.isMoved()){
            //round change, reverse timer, record steps
            WindowState windowState = new WindowState();
            windowState.setSwitchTurn(true);
            windowState.setMove(outputData.getMove());
            windowViewModel.setState(windowState);
            windowViewModel.firePropertyChanged("switchTurn");
        }if (outputData.isPromotion()){
            BoardState boardState = new BoardState();
            boardState.setBlackTurn(outputData.board().isBlackToMove());
            boardViewModel.setState(boardState);
            boardViewModel.firePropertyChanged(BoardStateConstants.PROMOTION);
        }if (outputData.isRepaint()){
            repaintBoardController.execute(outputData.board());
        }if (outputData.isSelect()){
            selectController.execute(outputData.board(), outputData.getCoordinate());
        }if (outputData.isGameOver()){

            BoardState boardState = new BoardState();
            boardState.setBlackTurn(outputData.board().isBlackToMove());
            boardState.setMsg(outputData.getMsg());

            boardViewModel.setState(boardState);
            boardViewModel.firePropertyChanged(BoardStateConstants.GAMEOVER);

            // stop timer, Update scores
            WindowState windowState = new WindowState();
            windowState.setGameOver(true);
            windowViewModel.setState(windowState);
            windowViewModel.firePropertyChanged("gameOver");
        }
    }

    @Override
    public void prepareFailView(String errorMessage) {

    }
}
