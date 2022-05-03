package chess.service;

import chess.domain.Room;
import chess.dto.RoomDto;
import chess.dto.RoomResponseDto;
import chess.repository.BoardDao;
import chess.repository.RoomDao;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RoomService {

    private final RoomDao roomDao;
    private final BoardDao boardDao;

    public RoomService(RoomDao roomDao, BoardDao boardDao) {
        this.roomDao = roomDao;
        this.boardDao = boardDao;
    }

    public RoomResponseDto create(RoomDto roomDto) {
        Room room = new Room(roomDto.getName(), roomDto.getPassword());
        int id = roomDao.save(room);
        return RoomResponseDto.of(id, room);
    }

    public void delete(int roomId, String password) {
        Optional<Integer> boardId = boardDao.findBoardIdByRoom(roomId);
        Room room = roomDao.findById(roomId);
        checkPassword(room, password);
        if (boardId.isEmpty()) {
            roomDao.delete(roomId);
            return;
        }
        checkGameEnd(boardId);
        roomDao.delete(roomId);
    }

    public List<RoomResponseDto> findRooms() {
        List<Room> rooms = roomDao.findAll();
        return rooms.stream()
                .map(RoomResponseDto::from)
                .collect(Collectors.toList());
    }

    public void validateId(int roomId) {
        roomDao.findById(roomId);
    }

    private void checkGameEnd(Optional<Integer> boardId) {
        if (!boardDao.getEnd(boardId.get())) {
            throw new IllegalArgumentException("진행 중인 게임은 삭제할 수 없습니다.");
        }
    }

    private void checkPassword(Room room, String password) {
        if (!room.isPasswordCorrect(password)) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
    }

}
