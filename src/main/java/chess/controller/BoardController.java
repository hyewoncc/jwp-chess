package chess.controller;

import chess.dto.BoardDto;
import chess.dto.CommendDto;
import chess.dto.ResultDto;
import chess.service.GameService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/boards")
public class BoardController {

    private final GameService gameService;

    public BoardController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping
    public ResponseEntity<BoardDto> findBoard(@RequestParam int roomId) {
        return ResponseEntity.ok(gameService.loadGame(roomId));
    }

    @PatchMapping(value = "/{boardId}")
    public ResponseEntity<BoardDto> movePiece(@PathVariable int boardId, @RequestBody CommendDto commendDto) {
        gameService.move(boardId, commendDto);
        return ResponseEntity.ok(gameService.gameStateAndPieces(boardId));
    }

    @GetMapping(value = "/{boardId}/result")
    public ResponseEntity<ResultDto> result(@PathVariable int boardId) {
        return ResponseEntity.ok(gameService.gameResult(boardId));
    }

    @GetMapping(value = "/{boardId}/end")
    public ResponseEntity<ResultDto> end(@PathVariable int boardId) {
        return ResponseEntity.ok(gameService.gameFinalResult(boardId));
    }
}
