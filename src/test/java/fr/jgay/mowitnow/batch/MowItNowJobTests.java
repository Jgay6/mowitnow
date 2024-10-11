package fr.jgay.mowitnow.batch;

import fr.jgay.mowitnow.utils.FileTestUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@SpringBatchTest
public class MowItNowJobTests {
    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @ParameterizedTest
    @ValueSource(strings = {"files/only_one_mower.txt", "files/multi_mower.txt"})
    public void should_execute_MowItNowJob(String fileInput) throws Exception {
        var jobExecution = jobLauncherTestUtils.launchJob(
                new JobParametersBuilder().addString("fileInput", FileTestUtils.getFileAbsolutePath(fileInput))
                        .addLong("time", System.currentTimeMillis())
                        .toJobParameters());

        assertThat(jobExecution.getExitStatus().getExitCode()).isEqualTo("COMPLETED");
    }

    @ParameterizedTest
    @ValueSource(strings = {"files/empty.txt", "files/empty_line.txt"})
    public void should_fail_MowItNowJob(String fileInput) throws Exception {
        var jobExecution = jobLauncherTestUtils.launchJob(
                new JobParametersBuilder().addString("fileInput", FileTestUtils.getFileAbsolutePath(fileInput))
                        .addLong("time", System.currentTimeMillis())
                        .toJobParameters());

        assertThat(jobExecution.getExitStatus().getExitCode()).isEqualTo("FAILED");
    }

    @Test
    public void should_fail_MowItNowJob_with_undefined_fileInput() throws Exception {
        var jobExecution = jobLauncherTestUtils.launchJob(
                new JobParametersBuilder()
                        .addLong("time", System.currentTimeMillis())
                        .toJobParameters());

        assertThat(jobExecution.getExitStatus().getExitCode()).isEqualTo("FAILED");
    }
}
