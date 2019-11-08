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
package io.github.dragon1573.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import io.github.dragon1573.Index;
import io.github.dragon1573.threads.DownThread;

/**
 * The <code>ActionListener</code> of <i>New Task</i> action.
 *
 * @author Legend_1949
 * @version November 28, 2018
 */
public class NewTaskListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        String sourceAddress = JOptionPane.showInputDialog(null, "资源地址：", "新建下载", JOptionPane.QUESTION_MESSAGE);
        if ("".equalsIgnoreCase(sourceAddress)) {
            JOptionPane.showMessageDialog(null, "请输入资源地址！", "警告", JOptionPane.ERROR_MESSAGE);
        } else if (Index.totalTasks + 1 > Index.MAX_TASKS) {
            JOptionPane.showMessageDialog(null, "多任务数量满，请等待前序任务完成！", "警告", JOptionPane.ERROR_MESSAGE);
        } else if (sourceAddress != null) {
            JFileChooser saveAsFile = new JFileChooser();
            int choice = saveAsFile.showSaveDialog(null);
            if (choice == JFileChooser.APPROVE_OPTION) {
                File localeFilePath = saveAsFile.getCurrentDirectory();
                String localeFileName = saveAsFile.getSelectedFile().getName();
                localeFilePath = new File(localeFilePath, localeFileName);
                DownThread task = new DownThread(sourceAddress, localeFilePath);
                new Thread(task).start();
            }
        }
    }
}
