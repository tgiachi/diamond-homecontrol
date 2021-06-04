package com.github.tgiachi.diamond.homecontrol.components.script;

import com.github.tgiachi.diamond.homecontrol.api.annotations.ScriptEngineClass;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@ScriptEngineClass("test_obj")
public class TestScriptClass {

    public void test() {
        LoggerFactory.getLogger(getClass()).info("OK from test script class");
    }
}
