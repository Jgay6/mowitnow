package fr.jgay.mowitnow.batch;

import fr.jgay.mowitnow.model.LawnDimensions;
import fr.jgay.mowitnow.model.Mower;
import fr.jgay.mowitnow.model.MowerCommand;
import fr.jgay.mowitnow.service.MoveOperationService;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
@StepScope
public class MowerCommandProcessor implements ItemProcessor<MowerCommand, Mower> {
    private final LawnDimensions lawnDimension;
    private final MoveOperationService moveOperationService;

    public MowerCommandProcessor(@Value("#{stepExecution.jobExecution}") JobExecution jobExecution,
                                 MoveOperationService moveOperationService) {
        this.lawnDimension = jobExecution.getExecutionContext().get("lawnDimensions", LawnDimensions.class);
        this.moveOperationService = moveOperationService;
    }

    public Mower process(MowerCommand item) {
        Assert.notNull(item, "MowerCommand cannot be null");
        moveOperationService.execute(item, lawnDimension);
        return item.mower();
    }
}
