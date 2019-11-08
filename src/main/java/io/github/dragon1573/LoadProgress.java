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

import java.awt.GridLayout;
import java.io.File;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

/**
 * The progress panel of downloading details.
 *
 * @author Dragon1573
 */
public class LoadProgress extends JPanel implements Runnable {
    private static final long serialVersionUID = -7067210210178703822L;
    private File localFile;
    private JLabel remoteLabel;
    private JLabel localLabel;
    private JProgressBar loadBar;
    private boolean isRunning = true;

    /**
     * The constructor
     */
    public LoadProgress() {
        setLayout(new GridLayout(3, 1));
        remoteLabel = new JLabel("远程资源：");
        add(remoteLabel);
        localLabel = new JLabel("另存为：");
        add(localLabel);
        loadBar = new JProgressBar();
        add(loadBar);
        Index.app.loadDetails.add(this);
        validate();
    }

    @Override
    public void run() {
        while (isRunning) {
            loadBar.setValue((int)localFile.length());
            try {
                Thread.sleep(50);
            } catch (InterruptedException ignored) {
            }
            if (loadBar.getValue() == loadBar.getMaximum()) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ignored) {
                }
                Index.app.loadDetails.remove(this);
                Index.app.loadDetails.repaint();
                return;
            }
        }
    }

    /**
     * Set the file object of the locale file
     */
    public void setLocal(File file) {
        localFile = file;
        localLabel.setText(localLabel.getText() + file.getAbsolutePath());
        revalidate();
    }

    /**
     * Set the size of the remote source.
     */
    public void setProgressRange(int maximum) {
        loadBar.setMinimum(0);
        loadBar.setMaximum(maximum);
        loadBar.setStringPainted(true);
        revalidate();
    }

    /**
     * Set the address of the remote file.
     */
    public void setRemote(String location) {
        remoteLabel.setText(remoteLabel.getText() + location);
        revalidate();
    }

    /**
     * Stop the Runnable thread.
     */
    public void stop() {
        isRunning = false;
    }
}
