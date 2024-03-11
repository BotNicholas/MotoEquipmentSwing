package com.nicholas;

import com.nicholas.controller.MainController;
import com.nicholas.view.MainView;

import java.net.MalformedURLException;

public class Main {
    public static void main(String[] args) throws MalformedURLException {
//        SwingUtilities.invokeLater(() -> new MainView());

        new MainController(new MainView());
    }
}