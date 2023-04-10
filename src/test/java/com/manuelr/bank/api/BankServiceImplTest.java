package com.manuelr.bank.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;

public class BankServiceImplTest {

//  private final BankRepository bankRepository = mock(BankRepository.class);
//
//  private final EventHubService eventHubService = mock(EventHubService.class);
//
//  private final BankService bankService = new BankServiceImpl(bankRepository, eventHubService);
//
//  @Test
//  public void testFindBanks() {
//
//    // Mock data
//    var bankEntity = BankEntity.builder().id("1").name("Test Bank").build();
//    var pageable = Pageable.unpaged();
//    var headers = new LinkedMultiValueMap<String, String>();
////
////    when(bankRepository.findAll(any(Pageable.class)))
////        .thenReturn(new PageImpl<>(Collections.singletonList(bankEntity)));
//
//    // Call the method being tested
//    var response = bankService.findBanks(pageable, null, null, null, null, null, headers);
//
//    // Verify the result
//    assertEquals(HttpStatus.OK, response.getStatusCode());
//    assertEquals(1, Objects.requireNonNull(response.getBody()).getData().getBanks().size());
//    assertEquals("Test Bank", response.getBody().getData().getBanks().get(0).getName());
//  }
}
