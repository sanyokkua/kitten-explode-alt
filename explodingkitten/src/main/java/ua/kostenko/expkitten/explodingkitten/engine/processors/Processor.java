package ua.kostenko.expkitten.explodingkitten.engine.processors;

public interface Processor {
    void setNext(Processor processor);

    void process(ProcessCardModel processCardModel);
}
