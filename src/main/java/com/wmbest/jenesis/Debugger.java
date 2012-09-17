package com.wmbest.jenesis;

import com.wmbest.jenesis.m68k.*;
import com.wmbest.jenesis.memory.*;

import org.eclipse.swt.*;
import org.eclipse.swt.custom.*;
import org.eclipse.swt.events.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.graphics.*;

public class Debugger {

    private SixtyEightK cpu = null;
    private Memory mem = null;

    private Display display;
    private Shell shell;
    private Table table;
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
                        table.setSelection((int)(Debugger.this.cpu.getPC()/2));
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
        display = Display.getDefault();
        shell = new Shell(display);

        shell.setLayout(new RowLayout());

        ExpandBar bar = new ExpandBar(shell, SWT.V_SCROLL);
        bar.setLayoutData(new RowData(400, 400));

        table = new Table(shell, SWT.MULTI | SWT.BORDER | SWT.FULL_SELECTION | SWT.VIRTUAL);
        table.setLinesVisible(true);
        table.setLayoutData(new RowData(800, 400));
        table.setItemCount(0xfffff);

        setupMenuBar(shell);
        setupExpandBar(bar);
        BusyIndicator.showWhile(display, memoryRunnable);

        shell.pack();
        shell.open();
    }

    private void setupMenuBar(Shell shell) {
        Menu menuBar = new Menu(shell, SWT.BAR);
        MenuItem fileMenuHeader = new MenuItem(menuBar, SWT.CASCADE);
        fileMenuHeader.setText("&File");

        Menu fileMenu = new Menu(shell, SWT.DROP_DOWN);
        fileMenuHeader.setMenu(fileMenu);

        MenuItem loadROM = new MenuItem(fileMenu, SWT.PUSH);
        loadROM.setText("&Load");
        loadROM.addSelectionListener(new SelectionListener() {
            public void widgetSelected(SelectionEvent event) {
                showFilePicker();
            }

            public void widgetDefaultSelected(SelectionEvent event) {
                showFilePicker();
            }
        });

        MenuItem showDis = new MenuItem(fileMenu, SWT.PUSH);
        showDis.setText("Disassemble");

        showDis.addSelectionListener(new SelectionListener() {
            public void widgetSelected(SelectionEvent event) {
                showDisassembler();
            }

            public void widgetDefaultSelected(SelectionEvent event) {
                showDisassembler();
            }
        });

        shell.setMenuBar(menuBar);
    }

    private void showFilePicker() {
        FileDialog dialog = new FileDialog(shell, SWT.SAVE);
        dialog.setFilterNames(new String[] { "Bin Files", "All Files (*.*)" });
        dialog.setFilterExtensions(new String[] { "*.bin", "*.*" });

        String file = dialog.open();
        if (file != null) {
            try {
                mem.loadFromFile(file);
                cpu.setupProgram();
                BusyIndicator.showWhile(display, memoryRunnable);
                table.clearAll();
            } catch (Exception e) {
                // Alert HERE
            }
        }
    }

    private void showDisassembler() {
        new Disassembler(mem);
    }
    
    private void setupExpandBar(ExpandBar bar) {
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
    }

    Runnable memoryRunnable = new Runnable() {
        public void run() {
            setupMemoryTable(table);
        }
    };

    private void setupMemoryTable(final Table table) {
        if (table.getColumns().length == 0) {
            for (int i=0; i < 3; i++) {
                TableColumn col = new TableColumn(table, SWT.NONE);
                col.setWidth(800/3);
            }
        }

        table.addListener(SWT.SetData, new Listener() {
            public void handleEvent(Event event) {
                if (table.isDisposed()) return;

                TableItem instRow = (TableItem) event.item;
                int value = mem.get((int) event.index * 2);

                instRow.setText(0, "0x" + Integer.toHexString((int)event.index * 2));
                instRow.setText(1, "0x" + Integer.toHexString(value));
                instRow.setText(2, "0b" + Integer.toBinaryString(value));
            }
        });
    }
}
