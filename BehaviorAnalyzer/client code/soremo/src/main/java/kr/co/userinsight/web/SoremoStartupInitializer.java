package kr.co.userinsight.web;

import javax.servlet.ServletContextEvent;

public class SoremoStartupInitializer extends StartupApplicationContextListener {


    private static final String APP_NAME = "SOREMO System";
    /**
     * @see org.springframework.web.context.ContextLoaderListener#contextInitialized(javax.servlet.ServletContextEvent)
     */
    @Override
    public void contextInitialized(final ServletContextEvent event) {
        logger.info(APP_NAME + " - Context - Initializing...");

        super.contextInitialized(event);

//        try {
//
//
//        }
//        catch (Exception e) {
//            String errMsg = APP_NAME + " - Error while context initializing.";
//            logger.error(errMsg, e);
//        }

        logger.info(APP_NAME + " - Context successfully initialized.");
    }

    /**
     * @see org.springframework.web.context.ContextLoaderListener#contextDestroyed(javax.servlet.ServletContextEvent)
     */
    @Override
    public void contextDestroyed(final ServletContextEvent event) {
        super.contextDestroyed(event);
        try {

        }
        catch (Exception e) {
            String errMsg = APP_NAME + " - Error while context destroying.";
            logger.error(errMsg, e);
        }

    }
}
