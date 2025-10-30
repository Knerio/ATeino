package dario.gui;

import com.fazecast.jSerialComm.SerialPort;
import dario.DataPayload;
import dario.DataType;
import dario.test.*;

import javax.swing.*;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TestGUI {

    private JTable table;
    private DefaultTableModel tableModel;
    private DefaultCellEditor cellEditor;
    private final TestConfiguration config;
    private final SerialPort serialPort;

    public TestGUI(TestConfiguration configuration, SerialPort port) {
        this.serialPort = port;
        this.config = configuration;
        String[] columns = {"Pin 1", "Pin 2", "Pin 3", "Pin 4", "Pin 5", "Pin 6", "Pin 7", "Pin 8", "Pin 9", "Pin 10", "Pin 11", "Pin 12"};
        List<Object[]> data = new ArrayList<>();
        data.add(configuration.getPinConfiguration().getPins().stream().map(pin -> ((Object) pin)).toArray());
        for (TestCase testCase : configuration.getTestCases()) {
            data.add(testCase.getPinTypes().stream().map(type -> (Object) type).toArray());
        }

        tableModel = new DefaultTableModel(data.toArray(new Object[][]{}), columns);
        table = new JTable(tableModel);

        List<String> list = new ArrayList<>(Arrays.stream(PinType.values()).map(Enum::name).toList());
        list.addAll(Arrays.stream(Pin.values()).map(Enum::name).toList());
        JComboBox<String> comboBox = new JComboBox<>(list.toArray(new String[0]));
        cellEditor = new DefaultCellEditor(comboBox);

        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellEditor(cellEditor);
        }

        cellEditor.addCellEditorListener(new CellEditorListener() {
            @Override
            public void editingStopped(ChangeEvent e) {
                updateConfig();
            }

            @Override
            public void editingCanceled(ChangeEvent e) {
                updateConfig();
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);

        JPanel controlPanel = new JPanel();
        JButton startButton = new JButton("Start");
        JButton addRowButton = new JButton("Add Pattern");
        JButton addColumnButton = new JButton("Add Pin");

        addRowButton.addActionListener(this::onAddRow);
        addColumnButton.addActionListener(this::onAddColumn);
        startButton.addActionListener(this::startTests);

        controlPanel.add(startButton);
        controlPanel.add(addRowButton);
        controlPanel.add(addColumnButton);

        JFrame frame = new JFrame("Custom Table Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(controlPanel, BorderLayout.SOUTH);
        frame.setSize(1200, 250);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void startTests(ActionEvent actionEvent) {
        JButton button = ((JButton) actionEvent.getSource());
        button.setVisible(false);
        TestResult result = config.testAll(serialPort);
        if (result.isSuccess()) {
            new DataPayload(DataType.WRITE_HIGH, 12).write(serialPort);
            new DataPayload(DataType.WRITE_LOW, 11).write(serialPort);
            JOptionPane.showMessageDialog(null, "Tests executed successfully");
        } else {
            new DataPayload(DataType.WRITE_HIGH, 11).write(serialPort);
            new DataPayload(DataType.WRITE_LOW, 12).write(serialPort);
            JOptionPane.showMessageDialog(null, "Tests failed (" + result.getFail().stream().map(String::valueOf).collect(Collectors.joining(",")) + ")");
        }
        button.setVisible(true);
    }

    private void updateConfig() {
        for (int i = 0; i < table.getColumnCount(); i++) {
            config.getPinConfiguration().setPinAt(i, Pin.valueOf(table.getValueAt(0, i).toString()));
        }
        List<TestCase> cases = new ArrayList<>();
        for (int i = 1; i < table.getRowCount(); i++) {
            List<PinType> types = new ArrayList<>();
            for (int j = 0; j < table.getColumnCount(); j++) {
                types.add(PinType.valueOf(table.getValueAt(i, j).toString()));
            }
            cases.add(new TestCase(types));
        }
        config.getTestCases().clear();
        config.getTestCases().addAll(cases);
    }

    private void onAddRow(ActionEvent e) {
        List<PinType> types = new ArrayList<>();
        for (int i = 0; i < tableModel.getColumnCount(); i++) {
            types.add(PinType.DO_NOTHING);
        }
        tableModel.addRow(types.toArray(new PinType[0]));
    }

    private void onAddColumn(ActionEvent e) {
        int oldCols = tableModel.getColumnCount();
        int rows = tableModel.getRowCount();

        Object[][] newData = new Object[rows][oldCols + 1];
        String[] newCols = new String[oldCols + 1];

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < oldCols; c++) {
                newData[r][c] = tableModel.getValueAt(r, c);
            }
        }

        for (int r = 0; r < rows; r++) {
            newData[r][oldCols] = PinType.DO_NOTHING;
        }

        for (int c = 0; c < oldCols; c++) {
            newCols[c] = tableModel.getColumnName(c);
        }
        newCols[oldCols] = "Pin " + (oldCols + 1);

        tableModel = new DefaultTableModel(newData, newCols);
        table.setModel(tableModel);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellEditor(cellEditor);
        }
    }
}
