package fr.jgay.mowitnow.batch;

import fr.jgay.mowitnow.mapper.LawnDimensionsMapper;
import fr.jgay.mowitnow.model.LawnDimensions;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

@Component
@StepScope
public class ReadLawnDimensionTasklet implements Tasklet {

    private final String fileInput;
    private final LawnDimensionsMapper lawnDimensionsMapper;

    public ReadLawnDimensionTasklet(@Value("#{jobParameters['fileInput']}") String fileInput,
                                    LawnDimensionsMapper lawnDimensionsMapper) {
        Assert.notNull(fileInput, "the argument fileInput is mandatory");
        this.fileInput = fileInput;
        this.lawnDimensionsMapper = lawnDimensionsMapper;
    }

    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        String firstLine =  getFirstLine();
        LawnDimensions lawnDimensions = lawnDimensionsMapper.mapLine(firstLine);
        chunkContext.getStepContext().getStepExecution().getJobExecution().getExecutionContext().put("lawnDimensions", lawnDimensions);
        return RepeatStatus.FINISHED;
    }

    private String getFirstLine() throws FileNotFoundException {
        Scanner scan = null;
        try {
            File file = new File(fileInput);
            scan = new Scanner(file);
            if (scan.hasNextLine()) {
                return scan.nextLine();
            }

            return null;
        } finally {
            if (scan != null) {
                scan.close();
            }
        }
    }
}
