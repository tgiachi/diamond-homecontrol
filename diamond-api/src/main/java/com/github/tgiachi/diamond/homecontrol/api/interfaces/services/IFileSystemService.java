package com.github.tgiachi.diamond.homecontrol.api.interfaces.services;

import com.github.tgiachi.diamond.homecontrol.api.interfaces.services.base.IDiamondService;

import java.util.List;

public interface IFileSystemService extends IDiamondService {

    void initialScanDirectory(String directory);

    List<String> getInitialScanDirectoryFiles(String directory);

    String buildPath(String... directory);

    boolean writeToFileJson(String filename, Object obj) throws Exception;

    <T> T readFileFromJson(String filename, Class<T> classz) throws Exception;

    boolean writeToFileYaml(String filename, Object obj) throws Exception;

    <T> T readFileFromYaml(String filename, Class<T> classz) throws Exception;

    boolean writeFile(String filename, String content) throws Exception;

    String readFile(String filename) throws Exception;

    void createDirectory(String directory);

    List<String> listFiles(String directory, String... exts);
}
