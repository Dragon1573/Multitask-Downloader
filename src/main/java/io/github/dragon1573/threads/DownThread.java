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

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javax.swing.JOptionPane;

import io.github.dragon1573.LoadProgress;

/**
 * Downloader core component for multitasking downloads.
 *
 * @author Legend_1949
 * @version November 28, 2018
 */
public class DownThread implements Runnable {
    private URL sourceUrl;
    private File localeFile;

    /**
     * The constructor.
     *
     * @param sourceAddress
     *     A {@link String} which refer to the remote target.
     * @param localeFile
     *     A {@link File} which refer to the locale target.
     */
    public DownThread(
        String sourceAddress, File localeFile
    ) {
        try {
            sourceUrl = new URL(sourceAddress);
        } catch (MalformedURLException e) {
            JOptionPane.showMessageDialog(null, "非法资源地址！", "错误", JOptionPane.ERROR_MESSAGE);
        }
        this.localeFile = localeFile;
    }

    @Override
    public void run() {
        try {
            URLConnection sourceConnection = sourceUrl.openConnection();
            sourceConnection.setConnectTimeout(2000);
            if (sourceConnection.getHeaderField(0) == null) {
                throw new Exception();
            } else {
                // Download 4MB for each block.
                byte[] dataBlock = new byte[4194304];
                int blockSize;
                InputStream jin = sourceConnection.getInputStream();
                FileOutputStream jout;
                // Confirm the size of the downloaded data.
                long loadedSize = localeFile.length();
                // If the downloaded file size is smaller than the resource
                // size, the file is incomplete. So resume the download task.
                if (loadedSize < sourceConnection.getContentLengthLong()) {
                    jin.skip(loadedSize);
                    jout = new FileOutputStream(localeFile, true);
                } else {
                    jout = new FileOutputStream(localeFile, false);
                }
                LoadProgress detailPanel = new LoadProgress();
                detailPanel.setLocal(localeFile);
                detailPanel.setRemote(sourceUrl.getPath());
                detailPanel.setProgressRange(sourceConnection.getContentLength());
                Thread detailThread = new Thread(detailPanel);
                detailThread.start();
                while ((blockSize = jin.read(dataBlock)) > 0) {
                    jout.write(dataBlock, 0, blockSize);
                }
                jout.close();
                jin.close();
                JOptionPane.showMessageDialog(null, localeFile.getName() + "下载完成！", "成功", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "资源不存在！", "错误", JOptionPane.ERROR_MESSAGE);
        }
    }
}
