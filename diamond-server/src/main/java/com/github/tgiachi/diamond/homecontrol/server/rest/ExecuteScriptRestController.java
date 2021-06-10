package com.github.tgiachi.diamond.homecontrol.server.rest;

import com.github.tgiachi.diamond.homecontrol.api.interfaces.services.IScriptEngineService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/script")
public class ExecuteScriptRestController {

    private final IScriptEngineService scriptEngineService;

    public ExecuteScriptRestController(IScriptEngineService scriptEngineService) {
        this.scriptEngineService = scriptEngineService;
    }


    @GetMapping("/execute")
    public ResponseEntity<Object> execute(String script) {

        try {
            return ResponseEntity.ok(scriptEngineService.runSyncScript(script));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }


}
