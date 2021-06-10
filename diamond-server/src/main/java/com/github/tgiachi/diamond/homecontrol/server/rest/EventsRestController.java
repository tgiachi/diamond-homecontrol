package com.github.tgiachi.diamond.homecontrol.server.rest;

import com.github.tgiachi.diamond.homecontrol.api.interfaces.entities.IBaseEntity;
import com.github.tgiachi.diamond.homecontrol.api.interfaces.services.IEventService;
import com.github.tgiachi.diamond.homecontrol.api.interfaces.services.INoSqlService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/events")
public class EventsRestController {

    private final INoSqlService noSqlService;
    private final IEventService eventService;

    public EventsRestController(INoSqlService noSqlService, IEventService eventService) {
        this.noSqlService = noSqlService;
        this.eventService = eventService;
    }

    @GetMapping("/collections")
    public ResponseEntity<List<String>> getCollections() {
        return ResponseEntity.ok(new ArrayList<>(eventService.getCollectionsName().keySet()));
    }

    @GetMapping("/get/{collectionName}")
    public ResponseEntity<List<? extends IBaseEntity>> getEvents(@PathVariable("collectionName") String collectionName) {
        if (eventService.getCollectionsName().containsKey(collectionName)) {
            var classz =  eventService.getCollectionsName().get(collectionName);
            return ResponseEntity.ok(noSqlService.findAll((Class<? extends IBaseEntity>)classz));
        }
        return ResponseEntity.ok(new ArrayList<>());
    }
}
