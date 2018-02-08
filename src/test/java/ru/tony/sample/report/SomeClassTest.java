package ru.tony.sample.report;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.*;

public class SomeClassTest {

    private SomeClass sut;
    private IMathematics multiplexer;

    @Before
    public void beforeTest() {
        multiplexer = mock(IMathematics.class);
        sut = new SomeClass(multiplexer);
    }

    @Test
    public void addActionCorrectlyAddTwoNumber() {
        when(multiplexer.add(anyInt(), anyInt())).thenReturn(3);

        int result = sut.addAction(2, 3);

        assertEquals(3, result);
        verify(multiplexer, times(1)).add(eq(2), eq(3));
    }






    @Test
    public void actionReturnOne() {
        SomeClass sut = new SomeClass();

        int result = sut.action();

        assertEquals(1, result);
    }

    @Test
    public void calculateSumOfTwoNumbersCorrectly() {
        //prepare
        int a = 1;
        int b = 2;

        // act
        int sum = a + b;

        // assert
        assertEquals(3, sum);
    }

}
