package ru.tony.sample.report.ex;

public class CatVoice implements AnimalVoice {

    @Override
    public void voice() {
        System.out.println("Meow!!!");
    }
}
