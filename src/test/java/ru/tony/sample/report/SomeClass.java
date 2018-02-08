package ru.tony.sample.report;

public class SomeClass {
    private IMathematics multiplexer;

    public SomeClass(IMathematics multiplexer) {
        this.multiplexer = multiplexer;
    }

    public int addAction(int argument1, int argument2) {
        return multiplexer.add(argument1, argument2);
    }

    public SomeClass() {

    }

    public int action() {
        return 1;
    }
}
