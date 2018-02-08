package ru.tony.sample.report.integration;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.tony.sample.WebAppInitializer;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = WebAppInitializer.class)
public class FullAppTest {

    @Test
    public void empty() {

    }
}
