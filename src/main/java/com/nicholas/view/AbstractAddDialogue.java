package com.nicholas.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public abstract class AbstractAddDialogue extends JDialog{
    protected BoxLayout layout;
    protected JPanel panel;
    protected JPanel header;
    protected JPanel input;
    protected JLabel title;
    protected JPanel controls;
    protected Toolkit toolkit;
    protected Dimension screen;
    protected Dimension dialog;
    protected JButton cancel;
    protected JButton save;
    protected JPanel errorPanel;
    protected JLabel error;

    public AbstractAddDialogue(Frame owner, String title){
        super(owner, true);
        setTitle(title);
        toolkit = Toolkit.getDefaultToolkit();
        screen = toolkit.getScreenSize();
        dialog = new Dimension(250, 290);

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        panel = new JPanel();
        layout = new BoxLayout(panel, BoxLayout.Y_AXIS);
        panel.setLayout(layout);

        header = new JPanel();
        this.title = new JLabel(title);
        this.title.setHorizontalAlignment(SwingConstants.CENTER);
        header.add(this.title);
        panel.add(header);
        panel.setBorder(new EmptyBorder(0, 10, 0, 10));

        errorPanel = new JPanel();
        error = new JLabel();
        error.setForeground(Color.red);
        error.setVisible(false);
        errorPanel.add(error);

        panel.add(errorPanel);

        input = new JPanel();

        panel.add(input);

        controls = new JPanel(new FlowLayout(FlowLayout.CENTER));
        cancel = new JButton("Cancel");
        cancel.addActionListener((e)->this.dispose());
        save = new JButton("Save");
        save.addActionListener((e)->this.dispose());
        controls.add(cancel);
        controls.add(save);
        controls.setBorder(new EmptyBorder(20, 0, 20, 0));
        panel.add(controls);

        add(panel);
        setBounds(screen.width/2-dialog.width/2, screen.height/2-dialog.height/2, dialog.width, dialog.height);
    }

    public JPanel getPanel() {
        return panel;
    }

    public void setPanel(JPanel panel) {
        this.panel = panel;
    }

    public JPanel getHeader() {
        return header;
    }

    public void setHeader(JPanel header) {
        this.header = header;
    }

    public JPanel getInput() {
        return input;
    }

    public void setInput(JPanel input) {
        this.input = input;
    }

    public JPanel getControls() {
        return controls;
    }

    public void setControls(JPanel controls) {
        this.controls = controls;
    }

    public JButton getSave() {
        return save;
    }

    public void setSave(JButton save) {
        this.save = save;
    }

    public void showDialogue(boolean flag){
        setVisible(flag);
    }

    public Dimension getDialogDimension() {
        return dialog;
    }

    public void setDialogDimension(Dimension dialog) {
        this.dialog = dialog;
        setSize(dialog);
    }

    public JLabel getError() {
        return error;
    }

    public void setError(JLabel error) {
        this.error = error;
    }
}
