/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package com.github.tgiachi.diamond.homecontrol.server;


import com.github.tgiachi.diamond.homecontrol.api.components.TestComponent;
import com.github.tgiachi.diamond.homecontrol.api.utils.ReflectionUtils;
import com.github.tgiachi.diamond.homecontrol.server.manager.DiamondServerManager;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class App implements CommandLineRunner {
    private static Logger logger = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {
        logger.info("Inizializing container");
        SpringApplication.run(App.class, args);

    }

    @Override
    public void run(String... args) throws Exception {

    }
}
