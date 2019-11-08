/*
  MIT License

  Copyright (c) 2019 Dragon1573

  Permission is hereby granted, free of charge, to any person obtaining a copy
  of this software and associated documentation files (the "Software"), to deal
  in the Software without restriction, including without limitation the rights
  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
  copies of the Software, and to permit persons to whom the Software is
  furnished to do so, subject to the following conditions:

  The above copyright notice and this permission notice shall be included in all
  copies or substantial portions of the Software.

  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
  SOFTWARE.
 */
package io.github.dragon1573;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Frame;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import io.github.dragon1573.listeners.ExitListener;
import io.github.dragon1573.panels.MenuArea;

/**
 * The Main Class of the whole application.
 *
 * @author Legend_1949
 * @version JavaSE-1.6
 * @since November 28, 2018
 */
public class Index extends JFrame {
    private static final long serialVersionUID = -5631852300484059364L;
    public static final int MAX_TASKS = 5;
    static Index app;
    public static int totalTasks = 0;
    JPanel loadDetails;

    /**
     * The constructor.
     */
    private Index() {
        MenuArea menuBar = new MenuArea();
        this.add(menuBar, BorderLayout.NORTH);
        loadDetails = new JPanel(new GridLayout(MAX_TASKS, 1));
        loadDetails.setBackground(Color.pink);
        this.add(loadDetails, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        app = new Index();
        app.setTitle("传奇下载2.0");
        app.setVisible(true);
        app.setLocation(0, 0);
        app.setExtendedState(Frame.MAXIMIZED_BOTH);
        app.setExitPerformance();
    }

    /**
     * Set the <i>Exit</i> button in the upper right corner of the window to confirm before exiting.
     */
    private void setExitPerformance() {
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new ExitListener());
    }
}
