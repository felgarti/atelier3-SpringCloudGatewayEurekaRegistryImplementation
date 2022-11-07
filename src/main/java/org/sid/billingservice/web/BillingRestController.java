package org.sid.billingservice.web;

import org.sid.billingservice.entities.Bill;
import org.sid.billingservice.feign.CustomerRestClient;
import org.sid.billingservice.feign.ProductItemRestClient;
import org.sid.billingservice.model.Customer;
import org.sid.billingservice.model.Product;
import org.sid.billingservice.repository.BillRepository;
import org.sid.billingservice.repository.ProductItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BillingRestController {

    private CustomerRestClient customerRestClient ;
    private ProductItemRestClient productItemRestClient ;
    private BillRepository billRepository ;
    private ProductItemRepository productItemRepository ;

    public BillingRestController(CustomerRestClient customerRestClient, ProductItemRestClient productItemRestClient, BillRepository billRepository, ProductItemRepository productItemRepository) {
        this.customerRestClient = customerRestClient;
        this.productItemRestClient = productItemRestClient;
        this.billRepository = billRepository;
        this.productItemRepository = productItemRepository;
    }

    @GetMapping(path = "/fullBill/{id}")
    public Bill getBill(@PathVariable Long id )
    {
        Bill bill = billRepository.findById(id).get();
        Customer customer = customerRestClient.getCustomerById(bill.getCustomrId()) ;
        bill.setCustomer(customer);
        bill.getProductItems().forEach(p->{
            Product product = productItemRestClient.getProductById(p.getProductId()) ;
            p.setProduct(product);

        });
        PagedModel<Product> productPagedModel=productItemRestClient.pageProducts() ;
        return  bill ;

    }

}
