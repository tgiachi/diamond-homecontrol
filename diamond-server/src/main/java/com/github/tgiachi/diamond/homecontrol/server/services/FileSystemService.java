package com.github.tgiachi.diamond.homecontrol.server.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tgiachi.diamond.homecontrol.api.impl.services.AbstractDiamondService;
import com.github.tgiachi.diamond.homecontrol.api.interfaces.services.IFileSystemService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FileSystemService extends AbstractDiamondService implements IFileSystemService {
    private static final String rootDirectory = "diamond";
    private static final String userDirectory = System.getProperty("user.home");
    private String appDirectory = Paths.get(userDirectory, rootDirectory).toString();
    private final ObjectMapper objectMapper;
    private final ObjectMapper yamlObjectMapper;
    private final Map<String, List<String>> startupScannedDirectory = new HashMap<>();


    public FileSystemService(ObjectMapper objectMapper, @Qualifier("yaml") ObjectMapper yamlObjectMapper) {
        this.objectMapper = objectMapper;
        this.yamlObjectMapper = yamlObjectMapper;
    }


    @Override
    public void onStart() {
        checkDirectory();
        super.onStart();
    }

    private void checkDirectory() {
        if (System.getProperty("DIAMOND_HOME_DIRECTORY") != null) {
            appDirectory = Paths.get(System.getProperty("DIAMOND_HOME_DIRECTORY"), rootDirectory).toString();
        }
        logger.info("Application directory is {}", appDirectory);
        createDirectory("");
    }

    @Override
    public void createDirectory(String directory) {
        var dir = Paths.get(appDirectory, directory).toFile();
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    @Override
    public void initialScanDirectory(String directory) {
        logger.info("Scanning directory {}", directory);
        startupScannedDirectory.put(directory, listFiles(directory));
    }

    @Override
    public List<String> getInitialScanDirectoryFiles(String directory) {
        if (startupScannedDirectory.containsKey(directory)) {
            return startupScannedDirectory.get(directory);
        }

        return new ArrayList<>();
    }

    @Override
    public List<String> listFiles(String directory, String... exts) {
        if (exts.length == 0)
            exts = null;
        return FileUtils.listFiles(Paths.get(appDirectory, directory).toFile(), exts, true).stream().map(File::getAbsolutePath).collect(Collectors.toList());
    }

    @Override
    public String buildPath(String... directory) {
        return Paths.get(appDirectory, directory).toString();
    }

    @Override
    public boolean writeToFileJson(String filename, Object obj) throws Exception {
        FileUtils.writeStringToFile(new File(buildPath(filename)), objectMapper.writeValueAsString(obj), Charset.defaultCharset());
        return true;
    }

    @Override
    public <T> T readFileFromJson(String filename, Class<T> classz) throws Exception {
        var strFile = FileUtils.readFileToString(new File(buildPath(filename)), Charset.defaultCharset());
        return objectMapper.readValue(strFile, classz);
    }

    @Override
    public boolean writeToFileYaml(String filename, Object obj) throws Exception {
        FileUtils.writeStringToFile(new File(buildPath(filename)), yamlObjectMapper.writeValueAsString(obj), Charset.defaultCharset());
        return true;
    }

    @Override
    public <T> T readFileFromYaml(String filename, Class<T> classz) throws Exception {
        var strFile = FileUtils.readFileToString(new File(buildPath(filename)), Charset.defaultCharset());
        return yamlObjectMapper.readValue(strFile, classz);
    }

    @Override
    public boolean writeFile(String filename, String content) throws Exception {
        FileUtils.writeStringToFile(new File(buildPath(filename)), content, Charset.defaultCharset());

        return true;
    }

    @Override
    public String readFile(String filename) throws Exception {
        return FileUtils.readFileToString(new File(buildPath(filename)), Charset.defaultCharset());
    }
}
