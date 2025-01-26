package com.hszg.demo.data.api;

import com.hszg.demo.model.game.Board;

import java.util.List;

public interface BoardManager {

    Board getBoard(int i);
    List<Board> getAllBoards();
    void storeBoard(Board board);
    void storeAllBoards(List<Board> boards);
    void deleteBoard(int i);
}
