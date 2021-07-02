package me.forklift.catalogservice.service;

import me.forklift.catalogservice.repository.CatalogEntity;

public interface CatalogService {
    Iterable<CatalogEntity> getAllCatalogs();
}
