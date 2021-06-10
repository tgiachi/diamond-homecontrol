package com.github.tgiachi.diamond.homecontrol.server.rest;

import com.github.tgiachi.diamond.homecontrol.api.data.ComponentInfo;
import com.github.tgiachi.diamond.homecontrol.api.interfaces.services.IDiamondComponentsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/components")
public class ComponentsRestController {

    private final IDiamondComponentsService diamondComponentsService;

    public ComponentsRestController(IDiamondComponentsService diamondComponentsService) {
        this.diamondComponentsService = diamondComponentsService;
    }

    @GetMapping("/list")
    public ResponseEntity<List<ComponentInfo>> listActiveComponents() {
        return ResponseEntity.ok(diamondComponentsService.getDiamondComponents());
    }
}
