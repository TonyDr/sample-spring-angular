package ru.tony.sample.report.ex;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("DogTest")
public class Dog {

    private AnimalVoice voice;

    @Autowired
    public Dog(AnimalVoice voice) {
        this.voice = voice;
    }

    public void bark() {
        voice.voice();
    }

    public void barkTwice() {
        voice.voice();
        voice.voice();
    }
}
