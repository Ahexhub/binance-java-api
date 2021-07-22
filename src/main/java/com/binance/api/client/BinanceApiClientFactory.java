package com.binance.api.client;

import com.binance.api.client.impl.ApiServiceGenerator;
import com.binance.api.client.impl.BinanceApiAsyncIsolatedMarginClientImpl;
import com.binance.api.client.impl.BinanceApiAsyncMarginRestClientImpl;
import com.binance.api.client.impl.BinanceApiAsyncRestClientImpl;
import com.binance.api.client.impl.BinanceApiIsolatedMarginClientImpl;
import com.binance.api.client.impl.BinanceApiMarginRestClientImpl;
import com.binance.api.client.impl.BinanceApiRestClientImpl;
import com.binance.api.client.impl.BinanceApiSwapRestClientImpl;

/**
 * A factory for creating BinanceApi client objects.
 */
public class BinanceApiClientFactory {


  /**
   * Instantiates a new binance api client factory.
   */
  private BinanceApiClientFactory() {

  }

  /**
   * New instance.
   * @return the binance api client factory
   */
  public static BinanceApiClientFactory newInstance() {
    return new BinanceApiClientFactory();
  }


  /**
   * Creates a new synchronous/blocking REST client.
   */
  public BinanceApiRestClient newRestClient() {
    return new BinanceApiRestClientImpl();
  }

  /**
   * Creates a new synchronous/blocking REST client.
   */
  public BinanceApiRestClient newTestnetRestClient() {
    return new BinanceApiRestClientImpl();
  }

  /**
   * Creates a new asynchronous/non-blocking REST client.
   */
  public BinanceApiAsyncRestClient newAsyncRestClient() {
    return new BinanceApiAsyncRestClientImpl();
  }

  public BinanceApiAsyncRestClient newTestnetAsyncRestClient() {
    return new BinanceApiAsyncRestClientImpl();
  }


  /**
   * Creates a new asynchronous/non-blocking Margin REST client.
   */
  public BinanceApiAsyncMarginRestClient newAsyncMarginRestClient() {
    return new BinanceApiAsyncMarginRestClientImpl();
  }

  /**
   * Creates a new synchronous/blocking Margin REST client.
   */
  public BinanceApiMarginRestClient newMarginRestClient() {
    return new BinanceApiMarginRestClientImpl();
  }

  /**
   * Creates a new web socket client used for handling data streams.
   */
  public BinanceApiWebSocketClient newWebSocketClient() {
    return ApiServiceGenerator.createSocket();
  }

  /**
   * Creates a new synchronous/blocking Swap REST client.
   */
  public BinanceApiSwapRestClient newSwapRestClient() {
    return new BinanceApiSwapRestClientImpl();
  }

  /**
   * Creates a new asynchronous isolated margin rest client.
   *
   * @return the client
   */
  public BinanceApiAsyncIsolatedMarginClient newAsyncIsolatedMarginRestClient() {
    return new BinanceApiAsyncIsolatedMarginClientImpl();
  }

  /**
   * Creates a new isolated margin rest client.
   *
   * @return the client
   */
  public BinanceApiIsolatedMarginClient newIsolatedMarginRestClient() {
    return new BinanceApiIsolatedMarginClientImpl();
  }

}
