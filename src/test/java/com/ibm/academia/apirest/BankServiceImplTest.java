package com.ibm.academia.apirest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.Objects;
import com.ibm.academia.apirest.entity.BankEntity;
import com.ibm.academia.apirest.repository.BankRepository;
import com.ibm.academia.apirest.service.BankService;
import com.ibm.academia.apirest.service.EventHubService;
import com.ibm.academia.apirest.service.impl.BankServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.util.LinkedMultiValueMap;

public class BankServiceImplTest {

  private final BankRepository bankRepository = mock(BankRepository.class);

  private final EventHubService eventHubService = mock(EventHubService.class);

  private final BankService bankService = new BankServiceImpl(bankRepository, eventHubService);

  @Test
  public void testFindBanks() {

    // Mock data
    var bankEntity = BankEntity.builder().id("1").name("Test Bank").build();
    var pageable = Pageable.unpaged();
    var headers = new LinkedMultiValueMap<String, String>();
//
//    when(bankRepository.findAll(any(Pageable.class)))
//        .thenReturn(new PageImpl<>(Collections.singletonList(bankEntity)));

    // Call the method being tested
    var response = bankService.findBanks(pageable, null, null, null, null, null, headers);

    // Verify the result
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(1, Objects.requireNonNull(response.getBody()).getData().getBanks().size());
    assertEquals("Test Bank", response.getBody().getData().getBanks().get(0).getName());
  }
}
