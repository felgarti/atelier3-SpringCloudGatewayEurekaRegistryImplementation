package org.sid.billingservice.feign;

import org.sid.billingservice.model.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.jpa.repository.Query;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.ws.rs.QueryParam;

@FeignClient(name = "PRODUCT-SERVICE")
public interface ProductItemRestClient {
@GetMapping(path = "/products")
    public PagedModel<Product> pageProducts() ;
@GetMapping(path = "/products/{id}")
public  Product getProductById(@PathVariable long id ) ;
}

