package ru.tony.sample.report.ex;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

@TestConfiguration
@Profile("DogTest")
public class DogTestConfiguration {

    @Bean
    public Dog dog() {
       return new Dog(voice());
    }

    @Bean
    public AnimalVoice voice(){
       return new DogVoice();
    }

/*
    @Bean
    public AnimalVoice voice(){
        return new CatVoice();
    }
*/


}
