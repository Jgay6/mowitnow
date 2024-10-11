package fr.jgay.mowitnow.mapper;

import fr.jgay.mowitnow.model.LawnDimensions;
import fr.jgay.mowitnow.model.Position;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class LawnDimensionsMapperTests {
    @InjectMocks
    private LawnDimensionsMapper lawnDimensionsMapper;

    @ParameterizedTest
    @MethodSource("provideLines")
    void should_map_to_lawnDimensions(String line, LawnDimensions expectedLawnDimension) {
        LawnDimensions lawnDimensions = lawnDimensionsMapper.mapLine(line);

        assertThat(lawnDimensions).isEqualTo(expectedLawnDimension);
    }


    @ParameterizedTest
    @ValueSource(strings = {"", "a a", "1", "11", "5 5 5 abc"})
    void should_throw_exception_when_mapping_with_invalid_line(String line) {

        assertThrows(
                IllegalArgumentException.class,
                () -> lawnDimensionsMapper.mapLine(line)
        );
    }

    private static Stream<Arguments> provideLines() {
        return Stream.of(
                Arguments.of("3 2", new LawnDimensions(new Position(0, 0), new Position(3, 2))),
                Arguments.of("5 5", new LawnDimensions(new Position(0, 0), new Position(5, 5)))
        );
    }
}
