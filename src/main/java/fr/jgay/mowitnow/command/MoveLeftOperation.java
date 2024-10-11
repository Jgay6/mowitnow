package fr.jgay.mowitnow.command;

import fr.jgay.mowitnow.model.Direction;
import fr.jgay.mowitnow.model.LawnDimensions;
import fr.jgay.mowitnow.model.Mower;

public class MoveLeftOperation implements MoveOperation {
    public void execute(Mower mower, LawnDimensions lawnDimensions) {
        Direction previousDirection = mower.getDirection();
        Direction newDirection = switch (previousDirection) {
            case N -> Direction.W;
            case E -> Direction.N;
            case W -> Direction.S;
            case S -> Direction.E;
        };
        mower.setPreviousDirection(previousDirection);
        mower.setDirection(newDirection);
    }
}
