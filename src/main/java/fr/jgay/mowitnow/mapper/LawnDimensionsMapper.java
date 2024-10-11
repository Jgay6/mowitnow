package fr.jgay.mowitnow.mapper;

import fr.jgay.mowitnow.model.LawnDimensions;
import fr.jgay.mowitnow.model.Position;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

@Component
public class LawnDimensionsMapper {

    public LawnDimensions mapLine(String line) {
        Assert.isTrue(StringUtils.hasText(line), "dimension line should not be empty or null");
        var coordinates = line.trim().split(" ");
        Assert.isTrue(coordinates.length == 2, "dimension line should be in format '# #'");
        return new LawnDimensions(new Position(0, 0),
                new Position(Integer.parseInt(coordinates[0]), Integer.parseInt(coordinates[1])));
    }
}
