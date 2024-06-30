package com.cropsense.app_server.Logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AppLogger {
   
    // define and initialize logger 
    private Logger logger;

    // default constructor
    public AppLogger() {}

    // constructor
    public AppLogger(String className) {
        logger = LoggerFactory.getLogger(className);
    }

    // log info msg
    public void logInfoMsg(String msg) {
        logger.info(msg);
    }

    // log warn msg
    public void logWarnMsg(String msg) {
        logger.warn(msg);
    }

    // log error msg
    public void logErrorMsg(String msg) {
        logger.error(msg);
    }
    
}
