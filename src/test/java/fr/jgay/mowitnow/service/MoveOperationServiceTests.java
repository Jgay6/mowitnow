package fr.jgay.mowitnow.service;

import fr.jgay.mowitnow.model.Direction;
import fr.jgay.mowitnow.model.LawnDimensions;
import fr.jgay.mowitnow.model.Mower;
import fr.jgay.mowitnow.model.MowerCommand;
import fr.jgay.mowitnow.model.Position;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class MoveOperationServiceTests {
    @InjectMocks
    private MoveOperationService moveOperationService;

    @ParameterizedTest
    @MethodSource("provideCommands")
    void should_execute_commands(Mower mower, String commands, Position expectedPosition, Direction expectedDirection) {
        LawnDimensions lawnDimensions = new LawnDimensions(new Position(0, 0), new Position(5, 5));
        MowerCommand mowerCommand = new MowerCommand(mower, commands);

        moveOperationService.execute(mowerCommand, lawnDimensions);

        assertThat(mower).extracting(Mower::getPosition, Mower::getDirection)
                .containsExactly(expectedPosition, expectedDirection);
    }


    @ParameterizedTest
    @MethodSource("provideInvalidCommands")
    void should_throw_exception_when_executing_invalid_commands(Position upperRight, Mower mower, String commands) {

        assertThrows(
                IllegalArgumentException.class,
                () -> {
                    LawnDimensions lawnDimensions = new LawnDimensions(new Position(1, 1), upperRight);
                    MowerCommand mowerCommand = new MowerCommand(mower, commands);

                    moveOperationService.execute(mowerCommand, lawnDimensions);
                }
        );
    }

    private static Stream<Arguments> provideCommands() {
        return Stream.of(
                Arguments.of(new Mower(new Position(1, 1), Direction.N), "ADAG", new Position(2, 2), Direction.N),
                Arguments.of(new Mower(new Position(1, 1), Direction.N), "AA", new Position(1, 3), Direction.N),
                Arguments.of(new Mower(new Position(1, 2), Direction.N), "GAGAGAGAA", new Position(1, 3), Direction.N),
                Arguments.of(new Mower(new Position(3, 3), Direction.E), "AADAADADDA", new Position(5, 1), Direction.E)
        );
    }

    private static Stream<Arguments> provideInvalidCommands() {
        return Stream.of(
                Arguments.of(null, new Mower(new Position(1, 1), Direction.N), "AAAAA"),
                Arguments.of(new Position(3, 3), null, "AAAAA"),
                Arguments.of(new Position(3, 3), new Mower(new Position(1, 1), Direction.N), null),
                Arguments.of(new Position(3, 3), new Mower(new Position(1, 1), Direction.N), "XYZ"),
                Arguments.of(new Position(3, 3), new Mower(new Position(1, 1), Direction.N), "agd")
        );
    }
}
