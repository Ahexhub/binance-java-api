package com.binance.api.client.impl;

import static com.binance.api.client.impl.ApiServiceGenerator.executeSync;

import java.util.List;
import java.util.Optional;

import com.binance.api.client.BinanceApiIsolatedMarginClient;
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

public class BinanceApiIsolatedMarginClientImpl implements BinanceApiIsolatedMarginClient {

  private BinanceApiAsyncIsolatedMarginClientBase clientBase;

  public BinanceApiIsolatedMarginClientImpl() {
    clientBase = new BinanceApiAsyncIsolatedMarginClientBase();
  }

  @Override
  public NewIsolatedAccountResponse createAccount(String base, String quote, String apiKey, String secret) {
    return executeSync(clientBase.createAccount(base, quote, apiKey, secret));
  }

  @Override
  public IsolatedMarginAccountInfo queryAccount(Optional<List<String>> symbols, String apiKey, String secret) {
    return executeSync(clientBase.queryAccount(symbols, apiKey, secret));
  }

  @Override
  public List<Order> getOpenOrders(OrderRequest orderRequest, String apiKey, String secret) {
    return executeSync(clientBase.getOpenOrders(orderRequest, apiKey, secret));
  }

  @Override
  public MarginNewOrderResponse newOrder(MarginNewOrder order, String apiKey, String secret) {
    return executeSync(clientBase.newOrder(order, apiKey, secret));
  }

  @Override
  public CancelOrderResponse cancelOrder(CancelOrderRequest cancelOrderRequest, String apiKey, String secret) {
    return executeSync(clientBase.cancelOrder(cancelOrderRequest, apiKey, secret));
  }

  @Override
  public Order getOrderStatus(OrderStatusRequest orderStatusRequest, String apiKey, String secret) {
    return executeSync(clientBase.getOrderStatus(orderStatusRequest, apiKey, secret));
  }

  @Override
  public List<Trade> getMyTrades(String symbol, String apiKey, String secret) {
    return executeSync(clientBase.getMyTrades(symbol, apiKey, secret));
  }

  @Override
  public MarginTransaction borrow(String asset, String symbol, String amount, String apiKey, String secret) {
    return executeSync(clientBase.borrow(asset, symbol, amount, apiKey, secret));
  }

  @Override
  public MarginTransaction repay(String asset, String symbol, String amount, String apiKey, String secret) {
    return executeSync(clientBase.repay(asset, symbol, amount, apiKey, secret));
  }

  @Override
  public IsolatedMarginTransferResult transfer(IsolatedMarginTransfer transfer, String apiKey, String secret) {
    return executeSync(clientBase.transfer(transfer, apiKey, secret));
  }

  @Override
  public IsolatedMarginSymbol getSymbol(String symbol, String apiKey, String secret) {
    return executeSync(clientBase.getSymbol(symbol, apiKey, secret));
  }

  @Override
  public List<IsolatedMarginSymbol> getSymbols(String apiKey, String secret) {
    return executeSync(clientBase.getSymbols(apiKey, secret));
  }

  @Override
  public ListenKey startUserDataStream(String symbol, String apiKey) {
    return executeSync(clientBase.startUserDataStream(symbol, apiKey));
  }

  @Override
  public void keepAliveUserDataStream(String symbol, String listenKey, String apiKey) {
    executeSync(clientBase.keepAliveUserDataStream(symbol, listenKey, apiKey));
  }

  @Override
  public void closeUserDataStream(String symbol, String listenKey, String apiKey) {
    executeSync(clientBase.closeUserDataStream(symbol, listenKey, apiKey));
  }

}
