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
import java.nio.file.Path;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import io.github.dragon1573.Index;
import io.github.dragon1573.threads.DownThread;

/**
 * The <code>ActionListener</code> of <i>New Task</i> action.
 *
 * @author Legend_1949
 * @author Dragon1573
 * @date 2019/11/22
 * @since November 28, 2018
 */
public class NewTaskListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        ++Index.totalTasks;
        if (Index.totalTasks <= Index.MAX_TASKS) {
            // Get the target URL.
            String sourceAddress = JOptionPane.showInputDialog(null, "资源地址：", "新建下载", JOptionPane.QUESTION_MESSAGE);
            String fileName = sourceAddress.replaceFirst(".+/", "");
            final String extName = sourceAddress.replaceFirst(".+\\.", "");
            if ("".equalsIgnoreCase(sourceAddress)) {
                // Target URL is empty.
                JOptionPane.showMessageDialog(null, "请输入资源地址！", "警告", JOptionPane.WARNING_MESSAGE);
            } else {
                // Create an UI and users can choose a path to save the file.
                JFileChooser saveAsFile = new JFileChooser();
                // Filter the extension name.
                saveAsFile.setFileFilter(
                    new FileNameExtensionFilter(
                        extName.toUpperCase()
                            + " File (*." + extName
                            + ")",
                        extName
                    )
                );
                // Set default directory.
                saveAsFile.setCurrentDirectory(new File("."));
                // Set default filename.
                saveAsFile.setSelectedFile(new File(fileName));
                int choice = saveAsFile.showSaveDialog(null);
                if (choice == JFileChooser.APPROVE_OPTION) {
                    File localeFilePath = saveAsFile.getSelectedFile();
                    // Notice: Path got from Java methods are different from that by user input.
                    fileName = localeFilePath.getAbsolutePath().replaceFirst(".+\\\\", "");
                    if (!fileName.endsWith(extName)) {
                        localeFilePath = new File(saveAsFile.getCurrentDirectory(), fileName + "." + extName);
                    }
                    File localTempPath;
                    if (fileName.endsWith(extName)) {
                        localTempPath = new File(saveAsFile.getCurrentDirectory(), fileName.replace(extName, "sha"));
                    } else {
                        localTempPath = new File(saveAsFile.getCurrentDirectory(), fileName + ".sha");
                    }
                    DownThread task = new DownThread(sourceAddress, localeFilePath, localTempPath);
                    new Thread(task).start();
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "以达到最大并行任务数量，请等待前序任务完成！", "警告", JOptionPane.WARNING_MESSAGE);
        }
    }
}
