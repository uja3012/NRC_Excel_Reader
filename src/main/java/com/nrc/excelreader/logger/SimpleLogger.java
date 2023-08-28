package com.nrc.excelreader.logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Logger to monitor the application activities
 * Date : 27.08.2023
 * Version : 1.0 (Initial Version)
 * @author Ujwala Vanve
 */

public class SimpleLogger {

    private final Logger logger;

    public SimpleLogger(Class<?> clazz) {
        logger = LoggerFactory.getLogger(clazz);
    }

    public void debug(String message){
        if(this.logger.isDebugEnabled()) {
            logger.debug(message);
        }
    }

    public void info(String message) {
        logger.info(message);
    }

    public void debug(String format, Object arg){
        if(this.logger.isDebugEnabled()) {
            logger.debug(format, arg);
        }
    }

    public void error(String message) {
        logger.info(message);
    }

    public void fatal(String message) {
        logger.info(message);
    }
}
