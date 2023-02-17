package org.sid.billingservice.services;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.Grouped;
import org.apache.kafka.streams.kstream.KStream;
import org.sid.billingservice.entities.Bill;
import org.sid.billingservice.repository.BillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.support.serializer.JsonSerde;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

@Service
public class BillService {
    @Autowired
    BillRepository billRepository;

    @Bean
    public Consumer<Bill> billConsumer() {
        return (input) -> {
            System.out.println("*********************");
            System.out.println(input.toString());
            System.out.println("*********************");
            billRepository.save(input);
        };
    }


    @Bean
    public Supplier<Bill> billSupplier() {
        return () -> new Bill(null, new Date(), null,
                new Random().nextLong(4L), null);

    }

    @Bean
    public Function<KStream<String, Bill>, KStream<String, Long>> KStreamFunction() {
        return (input) -> {
            return input
                    .map((k, v) ->
                            new KeyValue<>(v.getBillingDate().toString(), v))
                     .groupBy((k, v) -> k, Grouped.with(Serdes.String(),
                            new JsonSerde<>(Bill.class)))
                    .count()
                    .toStream();
//
//            output.foreach((k, v) -> {
//                System.out.println("Billing date: " + k + ", count: " + v);
//            });
//
//            return output;
        };
    }

}
