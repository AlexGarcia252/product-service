package com.example.demo;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final Map<Long, Product> store = new ConcurrentHashMap<>();
    private final AtomicLong sequence = new AtomicLong(2);

    public ProductController() {
        reset();
    }

    public final void reset() {
        store.clear();
        sequence.set(2);
        store.put(1L, new Product(1L, "Laptop Pro", 1200.0, 10));
        store.put(2L, new Product(2L, "Mouse Inalambrico", 25.0, 0));
    }

    @GetMapping
    public Collection<Product> list() {
        return store.values();
    }

    @PostMapping
    public ResponseEntity<Product> create(@RequestBody Product input) {
        long id = sequence.incrementAndGet();
        Product created = new Product(id, input.name(), input.price(), input.stock());
        store.put(id, created);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
}
