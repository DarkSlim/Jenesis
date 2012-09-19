package com.wmbest.jenesis.m68k;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.wmbest.jenesis.m68k.instructions.*;
import com.wmbest.jenesis.memory.*;

/**
 * Unit test for simple App.
 */
public class MoveTest 
    extends TestCase
{

    Memory mem = new Memory();
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

        cpu = new SixtyEightK(mem);
    }

    public void tearDown() throws Exception {
        super.tearDown();

        mem.clear();
    }

    public void testMoveDtoD()
    {
        cpu.setDx(1, 0xffcc);
        mem.put(0x2601);
        cpu.runSync();

        assertEquals("D3 did not have the proper value.", cpu.getDx(3), 0xffcc);
    }

    public void testMoveAtoA()
    {
        cpu.setAx(1, 0xffcc);
        mem.put(0x3649);
        cpu.runSync();

        assertEquals("A3 did not have the proper value.", cpu.getAx(3), 0xffcc);
    }

    public void testMoveDtoA()
    {
        cpu.setDx(1, 0xffcc);
        mem.put(0x3641);
        cpu.runSync();

        assertEquals("A3 did not have the proper value.", cpu.getAx(3), 0xffcc);
    }

    public void testMoveAtoD()
    {
        cpu.setAx(1, 0xffcc);
        mem.put(0x3609);
        cpu.runSync();

        assertEquals("D3 did not have the proper value.", cpu.getDx(3), 0xffcc);
    }

    public void testMoveToIndirectPost() {
        cpu.setAx(1, 0xffcc);
        mem.put(0x387C);
        mem.put(0x0100);
        mem.put(0x38C9);

        cpu.runSync();
        System.out.println(cpu.toString());

        assertEquals("A4 did not have the proper value.", 0x102, cpu.getAx(4));
        assertEquals("Memory did not have the proper value.", 0xffcc, mem.get(0x100));
    }

    public void testMoveToIndirectPre() {
        System.out.println("INDIRECT PRE-INCR");
        cpu.setAx(1, 0xffcc);
        cpu.setAx(4, 0x100);

        mem.put(0x3909);

        cpu.runSync();
        System.out.println(cpu.toString());

        assertEquals("A4 did not have the proper value.", 0xfe, cpu.getAx(4));
        assertEquals("Memory did not have the proper value.", 0xffcc, mem.get(0xfe));
    }

    public void testMoveIndirectOffset() {
        cpu.setDx(1, 0xffcc);
        cpu.setAx(1, 0x100);
        cpu.setAx(2, 0x200);

        assertEquals("D1 did not have the proper value.", 0xffcc, cpu.getDx(1));
        assertEquals("A1 did not have the proper value.", 0x100, cpu.getAx(1));
        assertEquals("A2 did not have the proper value.", 0x200, cpu.getAx(2));

        mem.put(0x3341);
        mem.put(0x0003);
        mem.put(0x3569);
        mem.put(0x0003);
        mem.put(0x0002);

        cpu.runSync();
        System.out.println(cpu.toString());

        assertEquals("Memory did not have the proper value.", 0xffcc, mem.get(0x103));
        assertEquals("Memory did not have the proper value.", 0xffcc, mem.get(0x202));
    }

    public void testMoveIndirectOffsetWithIndex() {
        //cpu.setDx(1, 0xffcc);
        //cpu.setDx(3, 0xc);
        //cpu.setAx(1, 0x100);
        //cpu.setAx(2, 0x200);
        //cpu.setAx(3, 0xc);

        //assertEquals("D1 did not have the proper value.", 0xffcc, cpu.getDx(1));
        //assertEquals("A1 did not have the proper value.", 0x100, cpu.getAx(1));
        //assertEquals("A2 did not have the proper value.", 0x200, cpu.getAx(2));

        //mem.put(0x3381);
        //mem.put(0x3002);
        ////mem.put(0x3571);
        ////mem.put(0xB002);
        ////mem.put(0x0002);

        //cpu.runSync();
        //System.out.println(cpu.toString());

        //assertEquals("Memory[0x10c] did not have the proper value.", 0xffcc, mem.get(0x10e));
        //assertEquals("Memory[0x202] did not have the proper value.", 0xffcc, mem.get(0x202));
    }
}
