package fr.jgay.mowitnow.command;

public enum Command {
    A(new MoveForwardOperation()),
    B(new MoveBackwardOperation()),
    G(new MoveLeftOperation()),
    D(new MoveRightOperation());

    private final MoveOperation operation;

    Command(MoveOperation operation) {
        this.operation = operation;
    }

    public MoveOperation operation() {
        return operation;
    }
}
