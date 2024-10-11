package fr.jgay.mowitnow.batch;

import fr.jgay.mowitnow.model.LawnDimensions;
import fr.jgay.mowitnow.model.Position;
import fr.jgay.mowitnow.utils.FileTestUtils;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@SpringBatchTest
public class ExecuteMowerCommandsStepTest {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @ParameterizedTest
    @ValueSource(strings = {"files/empty.txt", "files/empty_line.txt", "files/only_one_mower.txt", "files/multi_mower.txt", "files/out_lawn.txt", "files/complete.txt"})
    public void should_execute_mowerCommands_from_file(String fileInput) throws IOException {
        var jobExecution = jobLauncherTestUtils.launchStep("executeMowerCommandsStep",
                new JobParametersBuilder().addString("fileInput", FileTestUtils.getFileAbsolutePath(fileInput))
                        .addLong("time", System.currentTimeMillis())
                        .toJobParameters(),
                new ExecutionContext(Map.of("lawnDimensions", new LawnDimensions(new Position(0, 0), new Position(5, 5))))
        );

        assertThat(jobExecution.getExitStatus().getExitCode()).isEqualTo("COMPLETED");
    }
}
