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
package io.github.dragon1573.panels;

import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import io.github.dragon1573.listeners.ExitListener;
import io.github.dragon1573.listeners.NewTaskListener;

/**
 * The Menu Bar in the window of application.
 *
 * @author Legend_1949
 * @version November 28, 2018
 */
public class MenuArea extends JMenuBar {
    private static final long serialVersionUID = -3997746759666524535L;
    private static String versionStr;
    private JMenuItem newItem, exitItem, aboutItem;

    /**
     * The constructor.
     */
    public MenuArea() {
        init();
        setExitPerformance();
        setNewTaskPerformance();
        setAboutPerformance();
    }

    /**
     * Initialize the menu bar.
     */
    private void init() {
        newItem = new JMenuItem("新建");
        exitItem = new JMenuItem("退出");
        JMenu startMenu = new JMenu("开始");
        startMenu.add(newItem);
        startMenu.add(exitItem);
        this.add(startMenu);
        aboutItem = new JMenuItem("关于");
        JMenu helpMenu = new JMenu("帮助");
        helpMenu.add(aboutItem);
        this.add(helpMenu);
    }

    /**
     * Add an event monitor for the <code>About</code> button and define feedback methods.
     */
    private void setAboutPerformance() {
        aboutItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame aboutFrame = new JFrame("关于");
                JPanel aboutPanel = new JPanel(new GridLayout(1, 2));
                // Add a picture into a JPanel
                aboutPanel.add(new JPanel() {
                    private static final long serialVersionUID = -3054630750364647400L;

                    @Override
                    protected void paintComponent(Graphics g) {
                        super.paintComponent(g);
                        ImageIcon icon = new ImageIcon("tradeMark.jpg");
                        // Make sure the size of the picture is the same as the
                        // size of the JPanel
                        icon.setImage(icon.getImage().getScaledInstance(getWidth(), getHeight(), java.awt.Image.SCALE_AREA_AVERAGING));
                        g.drawImage(icon.getImage(), 0, 0, this);
                    }
                });
                setVersion();
                JTextArea versionArea = new JTextArea(versionStr);
                versionArea.setLineWrap(true);
                versionArea.setEditable(false);
                JScrollPane scrollPane = new JScrollPane(versionArea);
                aboutPanel.add(scrollPane);
                aboutFrame.add(aboutPanel);
                aboutFrame.setSize(540, 304);
                aboutFrame.setVisible(true);
                aboutFrame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
            }
        });
    }

    /**
     * Add an event monitor for the <code>Exit</code> button and define feedback methods.
     */
    private void setExitPerformance() {
        exitItem.addActionListener(new ExitListener());
    }

    /**
     * Add an event monitor for the <code>New Task</code> button and define feedback methods.
     */
    private void setNewTaskPerformance() {
        newItem.addActionListener(new NewTaskListener());
    }

    /**
     * Append version information to the application.
     */
    private void setVersion() {
        versionStr = "传奇下载\n";
        versionStr += "\n";
        versionStr += "版本：v2019.11\n";
        versionStr += "©版权所有 2019 Dragon_1573\n";
        versionStr += "遵循 MIT 开源许可证限制。";
    }
}
