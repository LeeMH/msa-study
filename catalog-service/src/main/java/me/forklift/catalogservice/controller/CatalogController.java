package me.forklift.catalogservice.controller;

import me.forklift.catalogservice.repository.CatalogEntity;
import me.forklift.catalogservice.service.CatalogService;
import me.forklift.catalogservice.vo.ResponseCatalog;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/catalog-service")
public class CatalogController {

    Environment env;

    CatalogService service;


    @Autowired
    public CatalogController(Environment env, CatalogService service) {
        this.env = env;
        this.service = service;
    }

    @GetMapping("/health_check")
    public String status() {
        return String.format("it's working. port = %s", env.getProperty("local.server.port"));
    }

    @GetMapping("/catalogs")
    public ResponseEntity<List<ResponseCatalog>> catalogs() {
        Iterable<CatalogEntity> catalogList = service.getAllCatalogs();

        List<ResponseCatalog> result = new ArrayList<>();
        catalogList.forEach(it -> {
            result.add(new ModelMapper().map(it, ResponseCatalog.class));
        });

        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }
}
