package com.github.tgiachi.diamond.homecontrol.server.services;

import com.github.tgiachi.diamond.homecontrol.api.annotations.ScriptEngineClass;
import com.github.tgiachi.diamond.homecontrol.api.impl.services.AbstractDiamondService;
import com.github.tgiachi.diamond.homecontrol.api.interfaces.services.IFileSystemService;
import com.github.tgiachi.diamond.homecontrol.api.interfaces.services.IScriptEngineService;
import com.github.tgiachi.diamond.homecontrol.api.utils.ReflectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.openjdk.nashorn.api.scripting.NashornScriptEngineFactory;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import javax.script.Bindings;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptException;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.Executor;

@Service
public class ScriptEngineService extends AbstractDiamondService implements IScriptEngineService {

    private final Executor threadExecutor;
    private final IFileSystemService fileSystemService;
    private final ApplicationContext applicationContext;
    private ScriptEngine engine;
    private Bindings bindings;

    public ScriptEngineService(IFileSystemService fileSystemService, @Qualifier("scriptExecutor") Executor scriptExecutor, ApplicationContext applicationContext) {
        this.fileSystemService = fileSystemService;
        this.threadExecutor = scriptExecutor;
        this.applicationContext = applicationContext;
    }

    @Override
    public void onStart() {
        super.onStart();
        fileSystemService.createDirectory("scripts");
        logger.info("Initializing JS script engine");
        engine = new NashornScriptEngineFactory().getScriptEngine("--language=es6");
        buildBindings();
        scanScriptClasses();
        runSyncScript(getScriptResource("/timeout.js"));
        runSyncScript(getScriptResource("/es-promise.js"));


        checkBootstrap();
        logger.info("JS script engine is ready");

    }

    private void buildBindings() {
        bindings = engine.getBindings(ScriptContext.ENGINE_SCOPE);
        bindings.put("logger", LoggerFactory.getLogger("JsScript"));
        engine.setBindings(bindings, ScriptContext.ENGINE_SCOPE);
    }

    private void scanScriptClasses() {
        ReflectionUtils.getAnnotation(ScriptEngineClass.class).forEach(aClass -> {
            var annotation = aClass.getAnnotation(ScriptEngineClass.class);
            bindings.put(annotation.value(), applicationContext.getBean(aClass));
            logger.info("Binding class {} -> {}", aClass.getSimpleName(), annotation.value());
        });
    }

    private void runSyncScript(String script) {
        try {
            engine.eval(script);
        } catch (Exception ex) {
            logger.error("Error during execute script:", ex);
        }
    }

    private String getScriptResource(String filename) {
        try {
            return IOUtils.toString(getClass().getResourceAsStream(filename), StandardCharsets.UTF_8.name());
        } catch (Exception ex) {
            return "";
        }
    }

    private void checkBootstrap() {
        if (new File(fileSystemService.buildPath("scripts", "bootstrap.js")).exists()) {
            try {
                var bootstrapFileContent = FileUtils.readFileToString(new File(fileSystemService.buildPath("scripts", "bootstrap.js")), StandardCharsets.UTF_8);
                runThreadScript(bootstrapFileContent);

            } catch (Exception ex) {
                logger.error("Error during load bootstrap.js: {}", ex);
            }
        }
    }

    private void runThreadScript(String content) {
        threadExecutor.execute(() -> {
            try {
                var evalResult = engine.eval(content);
            } catch (ScriptException exception) {
                logger.info("Error during execute script: {}", content, exception);
            }
        });
    }


}
