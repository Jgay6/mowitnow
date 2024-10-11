package fr.jgay.mowitnow.command;

import fr.jgay.mowitnow.model.LawnDimensions;
import fr.jgay.mowitnow.model.Mower;

@FunctionalInterface
public interface MoveOperation {
    void execute(Mower mower, LawnDimensions lawnDimensions);
}
