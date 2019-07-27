package kr.co.userinsight.web;

import kr.co.userinsight.systools.SysTaskHelper;
import kr.co.userinsight.tools.spring.SpringUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class StartupApplicationContextListener implements ServletContextListener {

    protected Logger logger = LoggerFactory.getLogger(StartupApplicationContextListener.class);

    private String mainAppContextFile;
    private boolean requiredSpringContext = true;
    private boolean requiredSecApplicationContext = true;
    private boolean requiredWorkflow = true;

    /**
     *
     * @param mainAppContextFile
     */
    public void contextInitialized(String mainAppContextFile) {
        this.mainAppContextFile = mainAppContextFile;
        contextInitialized((ServletContextEvent) null);
    }

    /**
     * @see org.springframework.web.context.ContextLoaderListener#contextInitialized(javax.servlet.ServletContextEvent)
     */
    @Override
    public void contextInitialized(final ServletContextEvent event) {
        logger.info("FRMK Context - Initializing...");
        try {
            beforeInitSpringContext();

            // Initializing Spring Context
            if (event != null) {
                SpringUtils.initAppContext(event.getServletContext());
            } else {
                SpringUtils.initAppContext(mainAppContextFile);
            }

            afterInitSpringContext();

            if (isRequiredSecApplicationContext()) {
                //initSecApplicationContext();
                //ApplicationManager.init(isRequiredWorkflow());
            }

//            if (AppConfigFileHelper.isSchedulerRequired()) {
//                SchedulerManager schedulerManager = SchedulerManager.getSchedulerManager();
//                schedulerManager.init();
//                schedulerManager.start();
//            }

            afterStartup();
        } catch (final Exception e) {
            logger.error("****FRMK Context NOT initialized.*****", e);
            throw new IllegalStateException(e);
        }
        logger.info("FRMK Context successfully initialized.");

    }

    /**
     *
     */


    /**
     *
     */
    private void beforeInitSpringContext() {
        try {
            logger.info("FRMK Context - beforeInitSpringContext");
            SysTaskHelper.execTasksBeforeInitSpringContext();
        } catch (final Exception e) {
            logger.error("****FRMK Context beforeInitSpringContext ERROR.*****", e);
            throw new IllegalStateException(e);
        }
    }

    /**
     *
     */
    private void afterInitSpringContext() {
        try {
            logger.info("FRMK Context - afterInitSpringContext");
          //  SysTaskHelper.execTasksAfterInitSpringContext();
        } catch (final Exception e) {
            logger.error("****FRMK Context afterInitSpringContext ERROR.*****", e);
            throw new IllegalStateException(e);
        }
    }

    /**
     *
     */
    public void afterStartup() {
        try {
            logger.info("FRMK Context - afterStartup");
          //  SysTaskHelper.execTasksAfterStartup();
        } catch (final Exception e) {
            logger.error("****FRMK Context afterStartup ERROR.*****", e);
            throw new IllegalStateException(e);
        }

    }

    /**
     * @see org.springframework.web.context.ContextLoaderListener#contextDestroyed(javax.servlet.ServletContextEvent)
     */
    @Override
    public void contextDestroyed(final ServletContextEvent event) {
        logger.info("FRMK Context destroyed.");
        try {
//            if (AppConfigFileHelper.isSchedulerRequired()) {
//                SchedulerManager.getSchedulerManager(SecApplicationContextHolder.getContext()).stop();
//            }
        }
        catch (Exception e) {
            String errMsg = "Error while context destroying.";
            logger.error(errMsg, e);
        }
    }

    /**
     * @return the requiredSpringContext
     */
    public boolean isRequiredSpringContext() {
        return requiredSpringContext;
    }

    /**
     * @param requiredSpringContext the requiredSpringContext to set
     */
    public void setRequiredSpringContext(boolean requiredSpringContext) {
        this.requiredSpringContext = requiredSpringContext;
    }

    /**
     * @return the requiredSecApplicationContext
     */
    public boolean isRequiredSecApplicationContext() {
        return requiredSecApplicationContext;
    }

    /**
     * @param requiredSecApplicationContext the requiredSecApplicationContext to set
     */
    public void setRequiredSecApplicationContext(boolean requiredSecApplicationContext) {
        this.requiredSecApplicationContext = requiredSecApplicationContext;
    }

    /**
     * @return the requiredWorkflow
     */
    public boolean isRequiredWorkflow() {
        return requiredWorkflow;
    }

    /**
     * @param requiredWorkflow the requiredWorkflow to set
     */
    public void setRequiredWorkflow(boolean requiredWorkflow) {
        this.requiredWorkflow = requiredWorkflow;
    }

    /**
     * @return the mainAppContextFile
     */
    public String getMainAppContextFile() {
        return mainAppContextFile;
    }

    /**
     * @param mainAppContextFile the mainAppContextFile to set
     */
    public void setMainAppContextFile(String mainAppContextFile) {
        this.mainAppContextFile = mainAppContextFile;
    }


}
