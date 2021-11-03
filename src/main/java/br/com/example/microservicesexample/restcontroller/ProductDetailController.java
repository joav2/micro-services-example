package br.com.example.microservicesexample.restcontroller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.example.microservicesexample.domain.ProductDetail;
import br.com.example.microservicesexample.service.ProductDetailService;

@RestController
@RequestMapping("/products")
public class ProductDetailController {
    private final ProductDetailService service;
    @Autowired
    public ProductDetailController(ProductDetailService service) {
        this.service = service;
    }
    @RequestMapping(method = RequestMethod.GET)
    public Iterable findAll(@RequestParam(name = "orderBy", required = false) String orderBy) {
        if (orderBy != null) {
            return service.findAllOrderBy(orderBy);
        } else {
            return service.findAll();
        }
    }
    @RequestMapping(method = RequestMethod.POST)
    public ProductDetail create(@Valid @RequestBody ProductDetail detail, BindingResult result) throws BindException {
        if (result.hasErrors()) {
            throw new BindException(result);
        }
        return service.save(detail);
    }
    @GetMapping("/{id}")
    public Optional<ProductDetail> findById(@PathVariable("id") String id, 
    @RequestParam(name = "orderBy", required = false) String orderBy) throws Exception {
        Optional<ProductDetail> detail = service.findById(id);
        if (detail == null) {
            throw new Exception();
        } else {
            return detail;
        }
    }
    @DeleteMapping("/{id}")
    public HttpEntity delete(@PathVariable String id) {
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
    @ResponseStatus(HttpStatus.NOT_FOUND)
    static class ProductNotFoundException extends RuntimeException {
    }
    @PutMapping("/{id}")
    public HttpEntity update(@PathVariable String id, Optional<ProductDetail> detail) {
        detail = service.findById(id);

        if (detail.isPresent()) {
            service.update(detail.get());
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
