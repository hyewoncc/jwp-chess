package chess.console;

import chess.console.view.InputView;
import chess.console.view.OutputView;
import chess.domain.board.Board;
import chess.domain.board.RegularRuleSetup;
import chess.domain.command.Commands;

public final class GameMachine {

    public void run() {
        OutputView.announceStart();
        Board board = null;
        Commands commands;
        do {
            commands = InputView.requestCommands();
            board = play(board, commands);
        } while (!commands.isEnd() && !gameEnd(board));

        if (board != null) {
            OutputView.printFinalResult(board);
        }
    }

    private boolean gameEnd(Board board) {
        return board != null && board.isEnd();
    }

    private Board play(Board board, Commands commands) {
        if (commands.isStart()) {
            board = new Board(new RegularRuleSetup());
            OutputView.printBoard(board);
        }
        if (commands.isMove()) {
            movePiece(board, commands);
        }
        if (commands.isStatus()) {
            showStatus(board);
        }
        return board;
    }

    private void showStatus(Board board) {
        if (board == null) {
            OutputView.announceNotStarted();
            return;
        }
        OutputView.printScoreAndResult(board);
    }

    private void movePiece(Board board, Commands commands) {
        if (board == null) {
            OutputView.announceNotStarted();
            return;
        }
        if (!commands.isRightMoveCommands()) {
            OutputView.announceWrongMoveCommands();
            return;
        }
        movePieceOnBoard(board, commands);
        OutputView.printBoard(board);
    }

    private void movePieceOnBoard(Board board, Commands commands) {
        try {
            board.move(commands.getSource(), commands.getTarget());
        } catch (IllegalArgumentException e) {
            OutputView.announceBadMovement(e.getMessage());
        }
    }
}
