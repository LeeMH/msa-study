package me.forklift.catalogservice.service;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import me.forklift.catalogservice.repository.CatalogEntity;
import me.forklift.catalogservice.repository.CatalogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Data
@Slf4j
@Service
public class CatalogServiceImpl implements CatalogService {
    private CatalogRepository repository;

    @Autowired
    public CatalogServiceImpl(CatalogRepository repository) {
        this.repository = repository;
    }

    @Override
    public Iterable<CatalogEntity> getAllCatalogs() {
        return repository.findAll();
    }
}
