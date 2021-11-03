package br.com.example.microservicesexample.service;

import br.com.example.microservicesexample.domain.ProductDetail;
import br.com.example.microservicesexample.repository.ProductDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductDetailService {

    @Autowired
    private ProductDetailRepository repository;


    public Iterable findAll() {
        return repository.findAllByOrderByProductNameAsc();
    }

    public ProductDetail save(ProductDetail detail) {
        return repository.save(detail);
    }

    public Optional<ProductDetail> findById(String id) {
        return repository.findById(id);
    }

    public void delete(String id) {
        Optional<ProductDetail> detail = repository.findById(id);

        repository.delete(detail.get());
    }

    public void update(ProductDetail detail) {

        Optional<ProductDetail> persistent = findById(detail.getProductId());

        if(!persistent.get().getProductId().equals(detail.getProductId())) {
            persistent.get().setProductId(detail.getProductId());
        }
        repository.save(persistent.get());
    }

    public Optional<ProductDetail> findByIdOrderBy(String id, String orderBy) {
        return repository.findByProductId(id, Sort.by(orderBy).ascending());
    }

    public Iterable findAllOrderBy(String orderBy) {
        return repository.findAll(Sort.by(orderBy).ascending());
    }
}
