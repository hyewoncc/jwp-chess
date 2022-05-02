package chess.controller;

import chess.domain.Room;
import chess.dto.RoomResponseDto;
import chess.service.GameService;
import chess.service.RoomService;
import chess.dto.BoardDto;
import chess.dto.RoomDto;
import java.util.List;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/rooms")
public class RoomController {

    private final RoomService roomService;
    private final GameService gameService;

    public RoomController(RoomService roomService, GameService gameService) {
        this.roomService = roomService;
        this.gameService = gameService;
    }

    @GetMapping
    public ResponseEntity<List<RoomResponseDto>> findRooms() {
        return ResponseEntity.ok(roomService.findRooms());
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> createRoom(Room room) {
        RoomDto roomDto = roomService.create(room);
        return ResponseEntity.ok(Map.of("url", "/rooms/" + roomDto.getId()));
    }

    @GetMapping("/{roomId}")
    public String board(@PathVariable int roomId) {
        roomService.validateId(roomId);
        return "/board.html";
    }

    @DeleteMapping("/{roomId}")
    public ResponseEntity<Map<String, String>> deleteRoom(@PathVariable int roomId, @RequestParam String password) {
        roomService.delete(roomId, password);
        return ResponseEntity.ok(Map.of("url", "/"));
    }

    @GetMapping(value = "/{roomId}/new")
    public ResponseEntity<BoardDto> startNewGame(@PathVariable int roomId) {
        return ResponseEntity.ok(gameService.newBoard(roomId));
    }
}
