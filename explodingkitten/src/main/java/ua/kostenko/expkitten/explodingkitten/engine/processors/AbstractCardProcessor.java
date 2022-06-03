package ua.kostenko.expkitten.explodingkitten.engine.processors;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
public abstract class AbstractCardProcessor implements Processor {
    protected Processor next;

    @Override
    public void setNext(@NonNull Processor processor) {
        this.next = processor;
    }

    @Override
    public void process(ProcessCardModel processCardModel) {
        if (Objects.isNull(processCardModel)) {
            throw new IllegalArgumentException("ProcessCardModel can't be null");
        }
        log.debug("ProcessCardModel: {}", processCardModel);
        log.debug("CurrentProcessorIs: {}", this.getClass().getSimpleName());
        if (Objects.isNull(processCardModel.getMoveType())
                && Objects.isNull(processCardModel.getActivePlayer())
                && Objects.isNull(processCardModel.getGameState())) {
            throw new IllegalArgumentException("MoveType/ActivePlayer/GameState shouldn't be null");
        }
        processCardModel(processCardModel);
    }

    protected abstract void processCardModel(ProcessCardModel processCardModel);
}