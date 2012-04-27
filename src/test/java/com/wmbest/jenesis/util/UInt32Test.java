package com.wmbest.jenesis.util;

import junit.framework.*;

/**
 * Unit test for simple App.
 */
public class UInt32Test 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public UInt32Test( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( UInt32Test.class );
    }

    public void testGet()
    {
        UInt32 uint = new UInt32();
        uint.set(0xffffffffffffffffL);

        assertTrue(uint.get() == 0xffffffff);
    }
}
