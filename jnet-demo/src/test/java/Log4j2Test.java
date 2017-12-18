import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author bjxieb
 * @date 2017-12-18
 */
public class Log4j2Test {
    public static void main(String[] args) {
        Logger logger = LogManager.getLogger(Log4j2Test.class);
        logger.debug("debug log.");
        logger.info("debug log.");
        logger.error("debug log.");
    }
}
