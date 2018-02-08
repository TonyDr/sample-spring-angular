package ru.tony.sample.report.ex;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DogTestConfiguration.class})
@ActiveProfiles("DogTest")
public class DogTest {

    @Autowired
    Dog dog;

    @Test
    public void dogVoice() {
        dog.bark();
    }
}
