package com.binance.api.client;

import java.util.List;
import java.util.Optional;

import com.binance.api.client.domain.account.MarginNewOrder;
import com.binance.api.client.domain.account.MarginNewOrderResponse;
import com.binance.api.client.domain.account.MarginTransaction;
import com.binance.api.client.domain.account.Order;
import com.binance.api.client.domain.account.Trade;
import com.binance.api.client.domain.account.isolated.IsolatedMarginAccountInfo;
import com.binance.api.client.domain.account.isolated.IsolatedMarginSymbol;
import com.binance.api.client.domain.account.isolated.IsolatedMarginTransfer;
import com.binance.api.client.domain.account.isolated.IsolatedMarginTransferResult;
import com.binance.api.client.domain.account.isolated.NewIsolatedAccountResponse;
import com.binance.api.client.domain.account.request.CancelOrderRequest;
import com.binance.api.client.domain.account.request.CancelOrderResponse;
import com.binance.api.client.domain.account.request.OrderRequest;
import com.binance.api.client.domain.account.request.OrderStatusRequest;
import com.binance.api.client.domain.event.ListenKey;

public interface BinanceApiAsyncIsolatedMarginClient {

  void createAccount(String apiKey, String secret, String base, String quote, BinanceApiCallback<NewIsolatedAccountResponse> cb);

  void queryAccount(String apiKey, String secret, Optional<List<String>> symbols, BinanceApiCallback<IsolatedMarginAccountInfo> cb);

  /**
   * Get all open orders on margin account for a symbol (async).
   *
   * @param orderRequest order request parameters
   * @param callback the callback that handles the response
   */
  void getOpenOrders(String apiKey, String secret,OrderRequest orderRequest, BinanceApiCallback<List<Order>> callback);

  /**
   * Send in a new margin order (async).
   *
   * @param order the new order to submit.
   * @return a response containing details about the newly placed order.
   */
  void newOrder(String apiKey, String secret,MarginNewOrder order, BinanceApiCallback<MarginNewOrderResponse> callback);

  /**
   * Cancel an active margin order (async).
   *
   * @param cancelOrderRequest order status request parameters
   */
  void cancelOrder(String apiKey, String secret,CancelOrderRequest cancelOrderRequest, BinanceApiCallback<CancelOrderResponse> callback);

  /**
   * Check margin order's status (async).
   *
   * @param orderStatusRequest order status request options/filters
   * @return an order
   */
  void getOrderStatus(String apiKey, String secret,OrderStatusRequest orderStatusRequest, BinanceApiCallback<Order> callback);

  /**
   * Get margin trades for a specific symbol (async).
   *
   * @param symbol symbol to get trades from
   * @return a list of trades
   */
  void getMyTrades(String apiKey, String secret,String symbol, BinanceApiCallback<List<Trade>> callback);

  /**
   * Transfers funds between spot and isolated margin account (account must be
   * first created).
   *
   * @param transfer
   * @param cb
   */
  void transfer(String apiKey, String secret,IsolatedMarginTransfer transfer, BinanceApiCallback<IsolatedMarginTransferResult> cb);

  /**
   * Apply for a loan
   * @param asset asset to repay
   * @param amount amount to repay
   * @return transaction id
   */
  void borrow(String apiKey, String secret,String asset, String symbol, String amount, BinanceApiCallback<MarginTransaction> callback);

  /**
   * Repay loan for margin account
   * @param asset asset to repay
   * @param amount amount to repay
   * @return transaction id
   */
  void repay(String apiKey, String secret,String asset, String symbol, String amount, BinanceApiCallback<MarginTransaction> callback);

  void getSymbol(String apiKey, String secret,String symbol, BinanceApiCallback<IsolatedMarginSymbol> cb);

  void getSymbols(String apiKey, String secret,BinanceApiCallback<List<IsolatedMarginSymbol>> cb);

  /**
   * Start a new user data stream.
   *
   * @param symbol   the isolated account symbol you want to receive events for
   * @param callback the callback that handles the response which contains a
   *                 listenKey
   */
  void startUserDataStream(String apiKey, String symbol, BinanceApiCallback<ListenKey> callback);

  /**
   * PING a user data stream to prevent a time out.
   *
   * @param symbol    the isolated account symbol you want to receive events for
   * @param listenKey listen key that identifies a data stream
   * @param callback  the callback that handles the response which contains a
   *                  listenKey
   */
  void keepAliveUserDataStream(String apiKey, String symbol, String listenKey, BinanceApiCallback<Void> callback);

  /**
   * Close out a new user data stream.
   *
   * @param symbol    the isolated account symbol you want to stop receiving
   *                  events for
   * @param listenKey listen key that identifies a data stream
   * @param callback  the callback that handles the response which contains a
   *                  listenKey
   */
  void closeUserDataStream(String apiKey, String symbol, String listenKey, BinanceApiCallback<Void> callback);

}
