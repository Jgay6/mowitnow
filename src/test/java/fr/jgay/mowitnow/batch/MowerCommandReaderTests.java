package fr.jgay.mowitnow.batch;

import fr.jgay.mowitnow.mapper.MowerCommandMapper;
import fr.jgay.mowitnow.model.Direction;
import fr.jgay.mowitnow.model.Mower;
import fr.jgay.mowitnow.model.MowerCommand;
import fr.jgay.mowitnow.model.Position;
import fr.jgay.mowitnow.utils.FileTestUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MowerCommandReaderTests {

    private final MowerCommandMapper mowerCommandMapper = new MowerCommandMapper();

    @ParameterizedTest
    @MethodSource("provideFiles")
    public void should_read_mowerCommand_from_file(String fileInput, MowerCommand expectedMowerCommand) throws IOException {
        MowerCommandReader mowerCommandReader = new MowerCommandReader(FileTestUtils.getFileAbsolutePath(fileInput), mowerCommandMapper);

        MowerCommand currentMowerCommand = mowerCommandReader.read();

        assertThat(currentMowerCommand).isEqualTo(expectedMowerCommand);
    }

    @Test
    void should_throw_exception_when_invalid_fileInput() {
        assertThrows(
                FileNotFoundException.class, () -> {
                    MowerCommandReader mowerCommandReader = new MowerCommandReader("/invalid/path/file.txt", mowerCommandMapper);
                    mowerCommandReader.read();
                }
        );
    }

    private static Stream<Arguments> provideFiles() {
        return Stream.of(
                Arguments.of("files/empty.txt", null),
                Arguments.of("files/empty_line.txt", null),
                Arguments.of("files/only_lawnDimension.txt", null),
                Arguments.of("files/only_one_mower.txt", new MowerCommand(new Mower(new Position(1, 2), Direction.N), "GAGAGAGAA")),
                Arguments.of("files/multi_mower.txt", new MowerCommand(new Mower(new Position(1, 2), Direction.N), "GAGAGAGAA"))
        );
    }
}
