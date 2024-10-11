package fr.jgay.mowitnow.service;

import fr.jgay.mowitnow.command.Command;
import fr.jgay.mowitnow.model.LawnDimensions;
import fr.jgay.mowitnow.model.MowerCommand;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class MoveOperationService {
    public void execute(MowerCommand mowerCommand, LawnDimensions lawnDimensions) {
        Arrays.stream(mowerCommand.commands().split("")).map(Command::valueOf)
                .forEach(command -> command.operation().execute(mowerCommand.mower(), lawnDimensions));
    }
}
