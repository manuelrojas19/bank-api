package com.manuelr.bank.api.utils;

public final class Constants {

  private Constants() {
    throw new UnsupportedOperationException(Constants.UNSUPPORTED_OPERATION_EXCEPTION_MSG);
  }

  public static final String ATM_CACHE_NAME = "BankApi_ATM_Cache";

  public static final double LOCATION_MARGIN = 0.05;

  public static final String LATITUDE_QUERY_PARAM = "latitude";

  public static final String LONGITUDE_QUERY_PARAM = "longitude";

  public static final String POSTAL_CODE_QUERY_PARAM = "postalCode";

  public static final String STATE_QUERY_PARAM = "state";

  public static final String ADDRESS_QUERY_PARAM = "address";

  public static final String REQUEST_RECEIVED_LOG_MSG = "Request received in {}";

  public static final String SENDING_RESPONSE_LOG_MSG = "Sending response to the client from {}";

  public static final String BANK_INFO_RETRIEVED_LOG_MSG =
      "Banks information retrieved successfully --> {}";

  public static final String BANK_INFO_ERROR_LOG_MSG = "Error trying to retrieve bank information";

  public static final String RECEIVED_HEADERS_LOG_MSG = "Received headers --> {}";

  public static final String EVENT_HUB_MESSAGE_SENT_LOG_MSG =
      "Message sending to event hub with message id {}";

  public static final String EVENT_HUB_ERROR_LOG_MSG = "Error trying to send message to event hub";

  public static final String PAGE_HEADER = "page";

  public static final String SIZE_HEADER = "size";

  public static final int DEFAULT_PAGE = 0;

  public static final int DEFAULT_SIZE = 20;

  public static final String UNSUPPORTED_OPERATION_EXCEPTION_MSG =
      "This is a utility class and cannot be instantiated";

  public static final String URI_FORMATTER = "%s:%o";
}
