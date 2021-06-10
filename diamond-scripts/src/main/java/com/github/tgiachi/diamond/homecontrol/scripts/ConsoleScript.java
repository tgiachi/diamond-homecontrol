package com.github.tgiachi.diamond.homecontrol.scripts;

import com.github.tgiachi.diamond.homecontrol.api.annotations.ScriptEngineClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@ScriptEngineClass("console")
public class ConsoleScript {

    private Logger logger = LoggerFactory.getLogger("Console");

    public void log(Object obj) {
        logger.info(obj.toString());
    }

    public void warn(Object obj) {
        logger.warn(obj.toString());
    }
}
