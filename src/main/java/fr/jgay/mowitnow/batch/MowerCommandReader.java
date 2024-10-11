package fr.jgay.mowitnow.batch;

import fr.jgay.mowitnow.mapper.MowerCommandMapper;
import fr.jgay.mowitnow.model.MowerCommand;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Component
@StepScope
public class MowerCommandReader implements ItemReader<fr.jgay.mowitnow.model.MowerCommand> {
    private final Scanner fileScanner;
    private final MowerCommandMapper mowerCommandMapper;

    public MowerCommandReader(@Value("#{jobParameters['fileInput']}") String fileInput,
                              MowerCommandMapper mowerCommandMapper) throws FileNotFoundException {
        File file = new File(fileInput);
        fileScanner = new Scanner(file);
        if (fileScanner.hasNextLine()) {
            fileScanner.nextLine();
        }
        this.mowerCommandMapper = mowerCommandMapper;
    }

    public MowerCommand read() {
        List<String> lines = new ArrayList<>();

        for (int i = 0; fileScanner.hasNextLine() && i < 2; i++) {
            lines.add(fileScanner.nextLine());
        }

        if (lines.size() != 2) {
            fileScanner.close();
            return null;
        }

        return mowerCommandMapper.map(lines);
    }
}
