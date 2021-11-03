package br.com.example.microservicesexample.repository;

import br.com.example.microservicesexample.domain.ProductDetail;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ProductDetailRepository extends JpaRepository<ProductDetail, String> {

    List<ProductDetail> findAllByOrderByProductNameAsc();

    Optional<ProductDetail> findByProductId(String id, Sort sort);
}