package homework;

import homework.annotation.After;
import homework.annotation.Before;
import homework.annotation.Test;

public class SomeMeaninglessTest {

    @Before
    public void prepareData1() {}

    @Before
    public void prepareData2() {}

    @Test
    public void testThatGoingToPass1() {}

    @Test
    public void testThatGoingToPass2() {}

    @Test
    public void testThatGoingToFail1() {
        throw new NullPointerException("something bad happened");
    }

    @Test
    public void testThatGoingToFail2() {
        throw new ArithmeticException("oops!");
    }

    @After
    public void cleanUp() {

    }

}
