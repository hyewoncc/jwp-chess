package chess.domain;

import static org.assertj.core.api.Assertions.assertThat;

import chess.domain.board.RegularRuleSetup;
import chess.domain.piece.Piece;
import chess.domain.piece.role.Bishop;
import chess.domain.piece.role.King;
import chess.domain.piece.role.Knight;
import chess.domain.piece.role.Pawn;
import chess.domain.piece.role.Queen;
import chess.domain.piece.role.Rook;
import chess.domain.position.Position;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

class RegularRuleMakerTest {

    @ParameterizedTest
    @MethodSource("rookPositions")
    @DisplayName("rook의 초기 위치를 확인")
    void rooks_initialPosition(final Position position, final Color color) {
        Map<Position, Piece> pieces = new RegularRuleSetup().initialize();
        assertThat(pieces.get(position)).isEqualTo(new Piece(color, new Rook()));
    }

    private static Stream<Arguments> rookPositions() {
        return Stream.of(
                Arguments.of(
                        Position.of("a1"), Color.WHITE
                ),
                Arguments.of(
                        Position.of("h1"), Color.WHITE
                ),
                Arguments.of(
                        Position.of("a8"), Color.BLACK
                ),
                Arguments.of(
                        Position.of("h8"), Color.BLACK
                )
        );
    }

    @ParameterizedTest
    @MethodSource("knightPositions")
    @DisplayName("knight의 초기 위치를 확인")
    void knights_initialPosition(final Position position, final Color color) {
        Map<Position, Piece> pieces = new RegularRuleSetup().initialize();
        assertThat(pieces.get(position)).isEqualTo(new Piece(color, new Knight()));
    }

    private static Stream<Arguments> knightPositions() {
        return Stream.of(
                Arguments.of(
                        Position.of("b1"), Color.WHITE
                ),
                Arguments.of(
                        Position.of("g1"), Color.WHITE
                ),
                Arguments.of(
                        Position.of("b8"), Color.BLACK
                ),
                Arguments.of(
                        Position.of("g8"), Color.BLACK
                )
        );
    }

    @ParameterizedTest
    @MethodSource("bishopPositions")
    @DisplayName("bishop의 초기 위치를 확인")
    void bishops_initialPosition(final Position position, final Color color) {
        Map<Position, Piece> pieces = new RegularRuleSetup().initialize();
        assertThat(pieces.get(position)).isEqualTo(new Piece(color, new Bishop()));
    }

    private static Stream<Arguments> bishopPositions() {
        return Stream.of(
                Arguments.of(
                        Position.of("c1"), Color.WHITE
                ),
                Arguments.of(
                        Position.of("f1"), Color.WHITE
                ),
                Arguments.of(
                        Position.of("c8"), Color.BLACK
                ),
                Arguments.of(
                        Position.of("f8"), Color.BLACK
                )
        );
    }

    @ParameterizedTest
    @MethodSource("queenPositions")
    @DisplayName("queen의 초기 위치를 확인")
    void queens_initialPosition(final Position position, final Color color) {
        Map<Position, Piece> pieces = new RegularRuleSetup().initialize();
        assertThat(pieces.get(position)).isEqualTo(new Piece(color, new Queen()));
    }

    private static Stream<Arguments> queenPositions() {
        return Stream.of(
                Arguments.of(
                        Position.of("d1"), Color.WHITE
                ),
                Arguments.of(
                        Position.of("d8"), Color.BLACK
                )
        );
    }

    @ParameterizedTest
    @MethodSource("kingPositions")
    @DisplayName("king의 초기 위치를 확인")
    void king_initialPosition(final Position position, final Color color) {
        Map<Position, Piece> pieces = new RegularRuleSetup().initialize();
        assertThat(pieces.get(position)).isEqualTo(new Piece(color, new King()));
    }

    private static Stream<Arguments> kingPositions() {
        return Stream.of(
                Arguments.of(
                        Position.of("e1"), Color.WHITE
                ),
                Arguments.of(
                        Position.of("e8"), Color.BLACK
                )
        );
    }

    @ParameterizedTest
    @ValueSource(strings = {"a2", "b2", "c2", "d2", "e2", "f2", "g2", "h2"})
    @DisplayName("흰색 pawn의 초기 위치를 확인")
    void whitePawn_initialPosition(final String rawPosition) {
        Map<Position, Piece> pieces = new RegularRuleSetup().initialize();
        assertThat(pieces.get(Position.of(rawPosition))).isEqualTo(new Piece(Color.WHITE, new Pawn()));
    }

    @ParameterizedTest
    @ValueSource(strings = {"a7", "b7", "c7", "d7", "e7", "f7", "g7", "h7"})
    @DisplayName("검은색 pawn의 초기 위치를 확인")
    void blackPawn_initialPosition(final String rawPosition) {
        Map<Position, Piece> pieces = new RegularRuleSetup().initialize();
        assertThat(pieces.get(Position.of(rawPosition))).isEqualTo(new Piece(Color.BLACK, new Pawn()));
    }

}
