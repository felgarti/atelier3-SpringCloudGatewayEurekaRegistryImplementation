package org.sid.billingservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.sid.billingservice.entities.Bill;
import org.sid.billingservice.entities.ProductItem;
import org.sid.billingservice.feign.CustomerRestClient;
import org.sid.billingservice.feign.ProductItemRestClient;
import org.sid.billingservice.model.Customer;
import org.sid.billingservice.model.Product;
import org.sid.billingservice.repository.BillRepository;
import org.sid.billingservice.repository.ProductItemRepository;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.geo.GeoModule;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.mediatype.MessageResolver;
import org.springframework.hateoas.mediatype.hal.CurieProvider;
import org.springframework.hateoas.mediatype.hal.HalConfiguration;
import org.springframework.hateoas.server.LinkRelationProvider;
import org.springframework.hateoas.server.mvc.RepresentationModelProcessorInvoker;
import org.springframework.stereotype.Component;
import org.springframework.web.util.pattern.PathPatternParser;

import java.util.Collection;
import java.util.Date;
import java.util.Random;

@SpringBootApplication
@EnableFeignClients
public class BillingServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BillingServiceApplication.class, args);
	}
@Bean CommandLineRunner start(BillRepository billRepository , ProductItemRepository productItemRepository ,
							  CustomerRestClient customerRestClient , ProductItemRestClient productItemRestClient)
{
return  args -> {
	Customer customer=customerRestClient.getCustomerById(1L) ;
	Bill bill =billRepository.save(new Bill(null , new Date() ,null , customer.getId() ,null)) ;
	PagedModel<Product> products = productItemRestClient.pageProducts() ;
	products.forEach(p->{
		ProductItem productItem= new ProductItem() ;
		productItem.setPrice(p.getPrice());
		productItem.setQuantity(1+new Random().nextInt(100));
productItem.setBill(bill);
productItem.setProductId(p.getId());
productItemRepository.save(productItem) ;
	});


	System.out.println(customer.getEmail());
	System.out.println(customer.getName());
	System.out.println(customer.getId());

};
}


}
