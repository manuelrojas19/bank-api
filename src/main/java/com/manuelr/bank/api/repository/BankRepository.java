package com.manuelr.bank.api.repository;

import com.manuelr.bank.api.entity.BankEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface BankRepository extends ReactiveSortingRepository<BankEntity, String> {

  Mono<Long> countAllBy();

  Flux<BankEntity> findAllBy(Pageable pageable);

  Flux<BankEntity> findAllByPostalCode(String postalCode, Pageable pageable);

  Flux<BankEntity> findAllByStateIgnoreCase(String state, Pageable pageable);

  Flux<BankEntity> findAllByPostalCodeAndStateIgnoreCase(
      String postalCode, String state, Pageable pageable);

  Flux<BankEntity> findAllByLocation_LatitudeBetweenAndLocation_LongitudeBetween(
      Double minLat, Double maxLat, Double minLong, Double maxLong, Pageable pageable);

  Flux<BankEntity> findAllByAddressContainingIgnoreCase(String address, Pageable pageable);
}
