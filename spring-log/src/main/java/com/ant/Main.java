package com.ant;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.logging.log4j.LogManager;

import java.util.logging.Logger;

public class Main {
    public static void main(String[] args) {
//        jul();
//        log4j2();
        jcl();
    }

    /**
     * java util logging
     */
    public static void jul() {
        Logger logger = Logger.getLogger("ant");
        logger.info("java util logging -->jul");
        logger.warning("java util logging -->jul");
    }

    /**
     * log4j2
     */
    public static void log4j2() {
        org.apache.logging.log4j.Logger logger = LogManager.getLogger();
        logger.error("org.apache.logging.log4j.Logger");
    }

    /**
     * apache common logging
     * 门面
     */
    public static void jcl(){
        Log log = LogFactory.getLog(Main.class);
        log.error("apache common logging");
        log.error(log.getClass().getName());
    }
}
