package com.wmbest.jenesis.m68k;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.wmbest.jenesis.m68k.instructions.*;

/**
 * Unit test for simple App.
 */
public class MoveTest 
    extends TestCase
{

    int[] mem;
    SixtyEightK cpu;

    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public MoveTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( MoveTest.class );
    }

    public void setUp() throws Exception {
        super.setUp();

        mem = new int[0xffff];
        cpu = new SixtyEightK(mem);
    }

    public void testMoveDtoD()
    {
        cpu.setDx(1, 0xffcc);
        mem[0] = 0x2601;
        cpu.run();

        assertEquals("D3 did not have the proper value.", cpu.getDx(3), 0xffcc);
    }

    public void testMoveAtoA()
    {
        cpu.setAx(1, 0xffcc);
        mem[0] = 0x3649;
        cpu.run();

        assertEquals("A3 did not have the proper value.", cpu.getAx(3), 0xffcc);
    }

    public void testMoveDtoA()
    {
        cpu.setDx(1, 0xffcc);
        mem[0] = 0x3641;
        cpu.run();

        assertEquals("A3 did not have the proper value.", cpu.getAx(3), 0xffcc);
    }

    public void testMoveAtoD()
    {
        cpu.setAx(1, 0xffcc);
        mem[0] = 0x3609;
        cpu.run();

        assertEquals("D3 did not have the proper value.", cpu.getDx(3), 0xffcc);
    }

    public void testMoveToIndirectPost() {
        cpu.setAx(1, 0xffcc);
        mem[0] = 0x387C;
        mem[1] = 0x0100;
        mem[2] = 0x38C9;

        cpu.run();
        System.out.println(cpu.toString());

        assertEquals("A4 did not have the proper value.", 0x102, cpu.getAx(4));
        assertEquals("Memory did not have the proper value.", 0xffcc, mem[0x100]);
    }
}
