package fr.jgay.mowitnow.command;

import fr.jgay.mowitnow.model.Direction;
import fr.jgay.mowitnow.model.LawnDimensions;
import fr.jgay.mowitnow.model.Mower;

public class MoveRightOperation implements MoveOperation {
    public void execute(Mower mower, LawnDimensions lawnDimension) {
        Direction previousDirection = mower.getDirection();
        Direction newDirection = switch (previousDirection) {
            case N -> Direction.E;
            case E -> Direction.S;
            case W -> Direction.N;
            case S -> Direction.W;
        };
        mower.setPreviousDirection(previousDirection);
        mower.setDirection(newDirection);
    }
}
