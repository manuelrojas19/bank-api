package com.ibm.academia.apirest.repository;

import com.ibm.academia.apirest.config.CacheConfig;
import com.ibm.academia.apirest.entity.BankEntity;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

@Repository
public interface BankRepository extends MongoRepository<BankEntity, String> {

  @NonNull
  @Override
  @Cacheable(cacheNames = {CacheConfig.BANK_API_CACHE_NAME})
  Page<BankEntity> findAll(@NonNull Pageable pageable);

  @Cacheable(
      cacheNames = {CacheConfig.BANK_API_CACHE_NAME},
      key = "{#postalCode}")
  Page<BankEntity> findAllByPostalCode(String postalCode, Pageable pageable);

  @Cacheable(
      cacheNames = {CacheConfig.BANK_API_CACHE_NAME},
      key = "{#state}")
  Page<BankEntity> findAllByStateIgnoreCase(String state, Pageable pageable);

  @Cacheable(
      cacheNames = {CacheConfig.BANK_API_CACHE_NAME},
      key = "{#postalCode, #state}")
  Page<BankEntity> findAllByPostalCodeAndStateIgnoreCase(
      String postalCode, String state, Pageable pageable);

  @Cacheable(
      cacheNames = {CacheConfig.BANK_API_CACHE_NAME},
      key = "{#minLat, #maxLat, #minLong, #maxLong}")
  Page<BankEntity> findAllByLocation_LatitudeBetweenAndLocation_LongitudeBetween(
      Double minLat, Double maxLat, Double minLong, Double maxLong, Pageable pageable);

  @Cacheable(
      cacheNames = {CacheConfig.BANK_API_CACHE_NAME},
      key = "{#address}")
  Page<BankEntity> findAllByAddressContainingIgnoreCase(String address, Pageable pageable);
}
