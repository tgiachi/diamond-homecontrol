package com.github.tgiachi.diamond.homecontrol.server.rest;

import com.github.tgiachi.diamond.homecontrol.api.interfaces.services.IActionProcessorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/actions")
public class ActionRestController {

    private final IActionProcessorService actionProcessorService;

    public ActionRestController(IActionProcessorService actionProcessorService) {
        this.actionProcessorService = actionProcessorService;
    }

    @GetMapping("/{name}")
    public ResponseEntity<Boolean> executeActionByName(@PathVariable String name) {
        actionProcessorService.executeAction(name);

        return ResponseEntity.ok(true);
    }

}
