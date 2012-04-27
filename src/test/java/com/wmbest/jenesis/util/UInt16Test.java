package com.wmbest.jenesis.util;

import junit.framework.*;

/**
 * Unit test for simple App.
 */
public class UInt16Test 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public UInt16Test( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( UInt16Test.class );
    }

    public void testGet()
    {
        UInt16 uint = new UInt16(0xffffffffL);

        assertTrue(uint.get() == 0xffff);
    }

    public void testSplit() {
        UInt16[] array = UInt16.split(0x1234123412341234L);
        assertEquals(4, array.length);

        for (int i = 0; i < array.length; ++i) {
            assertEquals(0x1234, array[i].get());
        }
    }
}
