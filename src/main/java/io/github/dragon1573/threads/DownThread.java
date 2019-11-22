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
package io.github.dragon1573.threads;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.http.HttpConnectTimeoutException;
import java.util.Scanner;

import javax.swing.JOptionPane;

import io.github.dragon1573.Index;
import io.github.dragon1573.LoadProgress;

/**
 * Downloader core component for multitasking downloads.
 *
 * @author Legend_1949
 * @author Dragon1573
 * @version November 28, 2018
 * @date 2019/11/22
 */
public class DownThread implements Runnable {
    private File localeFile;
    private File tempFile;
    private URL sourceUrl;
    private boolean isAppend = false;

    /**
     * The constructor.
     *
     * @param sourceAddress
     *     A {@link String} which refer to the remote target.
     * @param localeFile
     *     A {@link File} which refer to the locale target.
     */
    public DownThread(String sourceAddress, File localeFile, File tempFile) {
        try {
            this.sourceUrl = new URL(sourceAddress);
        } catch (MalformedURLException e) {
            JOptionPane.showMessageDialog(null, "非法资源地址！", "错误", JOptionPane.ERROR_MESSAGE);
        }
        this.localeFile = localeFile;
        this.tempFile = tempFile;
        // Check if the temp metadata is exist.
        try (Scanner scanner = new Scanner(tempFile)) {
            String unfinishedSource = scanner.next();
            // Toggle the switch of appending or overwriting.
            isAppend = (sourceAddress.equals(unfinishedSource));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // If they are different, refresh the metadata.
        if (!isAppend) {
            try (FileWriter writer = new FileWriter(tempFile)) {
                writer.write(sourceAddress);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void run() {
        try {
            // Create a connection with the resource.
            URLConnection sourceConnection = sourceUrl.openConnection();
            sourceConnection.setConnectTimeout(2000);
            if (sourceConnection.getHeaderField(0) == null) {
                throw new HttpConnectTimeoutException("错误：资源连接异常！");
            } else {
                // Download 4MB for each block.
                byte[] dataBlock = new byte[4194304];
                int blockSize;
                InputStream in = sourceConnection.getInputStream();
                FileOutputStream out;
                // Confirm the size of the downloaded data.
                long loadedSize = localeFile.length();
                /*
                 If the downloaded file size is smaller than the resource
                 size, the file is incomplete. So resume the download task.
                */
                if (loadedSize < sourceConnection.getContentLengthLong() && isAppend) {
                    in.skip(loadedSize);
                    out = new FileOutputStream(localeFile, true);
                } else {
                    out = new FileOutputStream(localeFile, false);
                }
                LoadProgress detailPanel = new LoadProgress();
                detailPanel.setLocal(localeFile);
                detailPanel.setRemote(sourceUrl.getPath());
                detailPanel.setProgressRange(sourceConnection.getContentLength());
                Thread detailThread = new Thread(detailPanel);
                detailThread.start();
                while ((blockSize = in.read(dataBlock)) > 0) {
                    out.write(dataBlock, 0, blockSize);
                }
                out.close();
                in.close();
                JOptionPane.showMessageDialog(null, localeFile.getName() + "下载完成！", "成功", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (HttpConnectTimeoutException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "文件读写权限异常！", "错误", JOptionPane.ERROR_MESSAGE);
        }
        this.tempFile.delete();
        --Index.totalTasks;
    }
}
