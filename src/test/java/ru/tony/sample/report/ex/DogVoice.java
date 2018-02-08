package ru.tony.sample.report.ex;

public class DogVoice implements AnimalVoice {
    @Override
    public void voice() {
        System.out.println("Bark");
    }
}
