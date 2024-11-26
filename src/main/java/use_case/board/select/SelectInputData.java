package use_case.board.select;
//CreateTime: 2024-11-20 3:14 p.m.

import entity.ChariotBoard;
import entity.Coordinate;

public record SelectInputData(ChariotBoard board, Coordinate coordinate) {

}
