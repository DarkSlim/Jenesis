package com.wmbest.jenesis;

import com.wmbest.jenesis.m68k.*;
import com.wmbest.jenesis.m68k.instructions.Instruction;
import com.wmbest.jenesis.memory.*;

import org.eclipse.swt.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.graphics.*;

public class Disassembler {
    private Memory mem = null;
    private SixtyEightK cpu;

    private Display display;
    private Shell shell;

    public Disassembler(Memory mem) {
        this.mem = mem;
        cpu = new SixtyEightK(mem);

        setup();
    }

    public void setup() {
        display = Display.getDefault();
        shell = new Shell(display);

        shell.setLayout(new GridLayout());

        Table table = new Table(shell, SWT.MULTI | SWT.BORDER | SWT.FULL_SELECTION);
        table.setLinesVisible(true);

        GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
        data.heightHint = 200;
        table.setLayoutData(data);

        cpu.setPC(0x0);

        table.setRedraw(false);

        TableColumn col0 = new TableColumn(table, SWT.NONE);
        col0.setWidth(50);
        TableColumn col1 = new TableColumn(table, SWT.NONE);
        col1.setWidth(500);

        while ((int) cpu.getPC() < 0xffff) {
            TableItem instRow = new TableItem(table, SWT.NONE);
            try {
                instRow.setText(0, "0x" + Long.toHexString(cpu.getPC()));

                int value =  mem.get((int)cpu.getPC());

                Instruction currentInst = Instruction.getInstruction(cpu,value);

                currentInst.preHandle();

                instRow.setText(1, currentInst.disassemble());
            } catch (Exception e) {
                instRow.setText(1, "Unsupported opcode");
            } finally {
                cpu.incrPC();
            }
        }
        table.setRedraw(true);

        shell.pack();
        shell.open();
    }

    public void show() {
        while (!shell.isDisposed()) {
            if (!display.readAndDispatch())
                display.sleep();
        }
        display.dispose();

    }

}
