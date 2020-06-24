package com.gmail.seliverstova.hanna;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;
import org.apache.commons.net.ftp.FTPClient;

public class ProcessFiles {
    private static Logger logger = Logger.getLogger(NotifyServlet.class.getName());
    private Properties properties = new Properties();

    public ProcessFiles(Properties properties) {
        super();
        this.properties = properties;
    }

    public ProcessFiles() {
        super();
    }

    public void read(String filenames) throws IOException {
        System.out.println("test");
        List<String> files = Arrays.asList(filenames.split(","));
        FTPClient ftpClient = new FTPClient();
        String localdir = properties.getProperty("localdirectory");
        String remotedir = properties.getProperty("remotedirectory");

        logger.info("File processing is started");

        try {
            ftpClient.connect(properties.getProperty("server"));
            ftpClient.enterLocalPassiveMode();
            ftpClient.login(properties.getProperty("user"), properties.getProperty("password"));

            logger.info("FTP connection OK");

            for (String file : files) {
                FileInputStream fis = new FileInputStream(localdir + file);
                ftpClient.storeFile(remotedir + file, fis);
                logger.info("File " + file + " was sent");
                printFile(localdir + file);
                logger.info("File " + file + " was printed");
            }
            ftpClient.logout();
            ftpClient.disconnect();
            logger.info("FTP was disconnected");
        } finally {
            if (ftpClient.isConnected()) {
                ftpClient.logout();
                ftpClient.disconnect();
                logger.info("FTP was disconnected after error.");
            }
        }
    }

    private void printFile(String fileName) throws IOException {
        FileInputStream fis = new FileInputStream(fileName);
        BufferedInputStream bis = new BufferedInputStream(fis, 1024);
        int readByte = 0;
        for (;(readByte = bis.read()) > 0;) {
            System.out.print((char)readByte);
        }
    }
}
