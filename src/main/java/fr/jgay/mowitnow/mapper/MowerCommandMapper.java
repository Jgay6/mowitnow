package fr.jgay.mowitnow.mapper;

import fr.jgay.mowitnow.model.Direction;
import fr.jgay.mowitnow.model.Mower;
import fr.jgay.mowitnow.model.MowerCommand;
import fr.jgay.mowitnow.model.Position;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.List;

@Component
public class MowerCommandMapper {

    public MowerCommand map(List<String> lines) {
        Assert.isTrue(lines.size() == 2, "mower command should be defined in 2 lines");
        return new MowerCommand(mapMower(lines.get(0)), lines.get(1));
    }

    private Mower mapMower(String line) {
        Assert.isTrue(StringUtils.hasText(line), "dimension line should not be empty or null");
        var parameters = line.trim().split(" ");
        Assert.isTrue(parameters.length == 3, "mower line should be in format '# # #'");
        return new Mower(new Position(Integer.parseInt(parameters[0]), Integer.parseInt(parameters[1])),
                Direction.valueOf(parameters[2])
        );
    }
}
