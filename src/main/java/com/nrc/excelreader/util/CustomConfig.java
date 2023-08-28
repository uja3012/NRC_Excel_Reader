package com.nrc.excelreader.util;

import com.nrc.excelreader.logger.SimpleLogger;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class CustomConfig {

    private static final SimpleLogger logger = new SimpleLogger(CustomConfig.class);

    public static Properties properties;

    static {

        logger.info("------>>>>> XLS SHEET DATA READER <<<<-------" );

        String externalFileName = System.getProperty("path.properties");

        try {
            properties = new Properties();
            properties.load(new FileInputStream(externalFileName));
            logger.info("----> External property file is loaded from location --->: "+externalFileName );
        } catch (IOException ioException) {
            ioException.printStackTrace();
            logger.info("-------->  Failed to load file from location ---->: "+externalFileName );
        }
    }
}
