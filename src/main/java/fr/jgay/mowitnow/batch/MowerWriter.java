package fr.jgay.mowitnow.batch;

import fr.jgay.mowitnow.model.Mower;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Component
public class MowerWriter implements ItemWriter<Mower> {
    public void write(Chunk<? extends Mower> chunk) {
        chunk.getItems().forEach(MowerWriter::printMowerStatus);
    }

    private static void printMowerStatus(Mower mower) {
        String status = String.format("%s %s %s", mower.getPosition().x(), mower.getPosition().y(), mower.getDirection());
        System.out.println(status);
    }
}
