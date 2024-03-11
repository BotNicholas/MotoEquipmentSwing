package com.nicholas.view;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class OnlyNumbersListener implements KeyListener {
    private static final String ALLOWED_CHARS = "0123456789";
    @Override
    public void keyTyped(KeyEvent e) {
        if(ALLOWED_CHARS.indexOf(e.getKeyChar())<0){
            e.consume();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
