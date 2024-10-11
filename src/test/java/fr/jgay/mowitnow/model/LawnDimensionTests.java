package fr.jgay.mowitnow.model;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class LawnDimensionTests {

    @ParameterizedTest
    @MethodSource("provideValidLawnParameters")
    void should_define_lawnDimensions(int lowerLeftX, int lowerLeftY, int upperRightX, int upperRightY) {
        Position lowerLeft = new Position(lowerLeftX, lowerLeftY);
        Position upperRight = new Position(upperRightX, upperRightY);

        LawnDimensions lawnDimensions = new LawnDimensions(lowerLeft, upperRight);

        assertThat(lawnDimensions).extracting(LawnDimensions::lowerLeft, LawnDimensions::upperRight)
                .containsExactly(lowerLeft, upperRight);
    }


    @ParameterizedTest
    @MethodSource("provideInvalidLawnParameters")
    void should_throw_exception_when_define_lawnDimensions_with_wrong_parameters(int lowerLeftX, int lowerLeftY, int upperRightX, int upperRightY) {

        assertThrows(
                IllegalArgumentException.class,
                () -> {
                    Position lowerLeft = new Position(lowerLeftX, lowerLeftY);
                    Position upperRight = new Position(upperRightX, upperRightY);

                    new LawnDimensions(lowerLeft, upperRight);
                }
        );
    }

    private static Stream<Arguments> provideValidLawnParameters() {
        return Stream.of(
                Arguments.of(1, 1, 2, 3),
                Arguments.of(1, 5, 3, 100)
        );
    }

    private static Stream<Arguments> provideInvalidLawnParameters() {
        return Stream.of(
                Arguments.of(-2, -2, -1, -1),
                Arguments.of(-2, -2, 1, 1),
                Arguments.of(2, 2, -1, -1),
                Arguments.of(2, 2, 1, 1)
        );
    }
}
