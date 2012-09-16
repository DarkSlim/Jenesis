package com.wmbest.jenesis;

import com.wmbest.jenesis.m68k.*;
import com.wmbest.jenesis.memory.*;

import org.eclipse.swt.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.graphics.*;

public class Debugger {

    private SixtyEightK cpu = null;
    private Memory mem = null;

    private Display display;
    private Shell shell;
    private Label mCPUData;
    
    public Debugger(SixtyEightK cpu, Memory mem) {
        this.cpu = cpu;
        this.mem = mem;

        cpu.setListener(new SixtyEightK.SixtyEightKListener() {
            public void onTick() {
                final String data = Debugger.this.cpu.toString();
                Display.getDefault().asyncExec(new Runnable() {
                    public void run() {
                        mCPUData.setText(data);
                    }
                });
            }
        });

        setup();
    }

    public void show() {
        while (!shell.isDisposed()) {
            if (!display.readAndDispatch())
                display.sleep();
        }
        display.dispose();

    }

    private void setup() {
        display = new Display();
        shell = new Shell(display);

        shell.setLayout(new RowLayout());

        ExpandBar bar = new ExpandBar(shell, SWT.V_SCROLL);
        bar.setLayoutData(new RowData(400, 400));

        ExpandItem controls = new ExpandItem(bar, SWT.NONE, 0);
        Composite comp = new Composite(bar, SWT.NONE);

        GridLayout layout = new GridLayout ();
        layout.marginLeft = layout.marginTop = layout.marginRight = layout.marginBottom = 10;
        layout.verticalSpacing = 10;
        comp.setLayout(layout);

        Button start = new Button(comp, SWT.PUSH);
        start.setText("Start");

        start.addListener(SWT.Selection, new Listener() {
            public void handleEvent(Event e) {
                if (cpu.isRunning()) {
                    cpu.resume();
                } else {
                    cpu.run();
                }
            }
        });

        start = new Button(comp, SWT.PUSH);
        start.setText("Stop");

        start.addListener(SWT.Selection, new Listener() {
            public void handleEvent(Event e) {
                cpu.stop();
            }
        });

        start = new Button(comp, SWT.PUSH);
        start.setText("Step");

        start.addListener(SWT.Selection, new Listener() {
            public void handleEvent(Event e) {
                cpu.step();
            }
        });

        controls.setText("Debugger Controls");
        controls.setHeight(comp.computeSize(SWT.DEFAULT, SWT.DEFAULT).y);
        controls.setControl(comp);

        ExpandItem cpu = new ExpandItem(bar, SWT.NONE, 1);
        comp = new Composite(bar, SWT.NONE);

        FillLayout fillLayout = new FillLayout();
        comp.setLayout(layout);

        mCPUData = new Label(comp, SWT.LEFT);
        mCPUData.setText(this.cpu.toString());

        cpu.setText("CPU Data");
        cpu.setHeight(comp.computeSize(SWT.DEFAULT, SWT.DEFAULT).y);
        cpu.setControl(comp);

        controls.setExpanded(true);
        cpu.setExpanded(true);

        shell.pack();
        shell.open();
    }
}
