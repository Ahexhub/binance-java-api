package com.binance.api.client.impl;

import static com.binance.api.client.constant.BinanceApiConstants.DEFAULT_RECEIVING_WINDOW;
import static java.lang.System.currentTimeMillis;
import static java.util.stream.Collectors.joining;

import java.util.List;
import java.util.Optional;

import com.binance.api.client.constant.BinanceApiConstants;
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

import retrofit2.Call;

public class BinanceApiAsyncIsolatedMarginClientBase {

  private static final String IS_ISOLATED = "TRUE";
  private BinanceApiService binanceApiService;

  public BinanceApiAsyncIsolatedMarginClientBase() {
    binanceApiService = ApiServiceGenerator.createService(BinanceApiService.class);
  }

  public Call<NewIsolatedAccountResponse> createAccount(String base, String quote, String apiKey, String secret) {
    return binanceApiService.createIsolatedMarginAccount(base, quote, DEFAULT_RECEIVING_WINDOW, currentTimeMillis(), apiKey, secret);
  }

  public Call<IsolatedMarginAccountInfo> queryAccount(Optional<List<String>> symbols, String apiKey, String secret) {
    if (symbols.isPresent()) {
      String ss = symbols.get().stream().collect(joining(","));
      return binanceApiService.queryIsolatedMarginAccount(ss, DEFAULT_RECEIVING_WINDOW, currentTimeMillis(), apiKey, secret);
    } else {
      return binanceApiService.queryIsolatedMarginAccount(DEFAULT_RECEIVING_WINDOW, currentTimeMillis(), apiKey, secret);
    }
  }

  public Call<List<Order>> getOpenOrders(OrderRequest orderRequest, String apiKey, String secret) {
    return binanceApiService.getOpenMarginOrders(orderRequest.getSymbol(), IS_ISOLATED, orderRequest.getRecvWindow(),
        orderRequest.getTimestamp(), apiKey, secret);
  }

  public Call<MarginNewOrderResponse> newOrder(MarginNewOrder order, String apiKey, String secret) {
    return binanceApiService.newMarginOrder(order.getSymbol(), IS_ISOLATED, order.getSide(), order.getType(), order.getTimeInForce(),
        order.getQuantity(), order.getPrice(), order.getNewClientOrderId(), order.getStopPrice(), order.getIcebergQty(),
        order.getNewOrderRespType(), order.getSideEffectType(), order.getRecvWindow(), order.getTimestamp(), apiKey, secret);
  }

  public Call<CancelOrderResponse> cancelOrder(CancelOrderRequest cancelOrderRequest, String apiKey, String secret) {
    return binanceApiService.cancelMarginOrder(cancelOrderRequest.getSymbol(), IS_ISOLATED, cancelOrderRequest.getOrderId(),
        cancelOrderRequest.getOrigClientOrderId(), cancelOrderRequest.getNewClientOrderId(), cancelOrderRequest.getRecvWindow(),
        cancelOrderRequest.getTimestamp(), apiKey, secret);
  }

  public Call<Order> getOrderStatus(OrderStatusRequest orderStatusRequest, String apiKey, String secret) {
    return binanceApiService.getMarginOrderStatus(orderStatusRequest.getSymbol(), IS_ISOLATED, orderStatusRequest.getOrderId(),
        orderStatusRequest.getOrigClientOrderId(), orderStatusRequest.getRecvWindow(), orderStatusRequest.getTimestamp(), apiKey, secret);
  }

  public Call<List<Trade>> getMyTrades(String symbol, String apiKey, String secret) {
    return binanceApiService.getMyMarginTrades(symbol, IS_ISOLATED, null, null, BinanceApiConstants.DEFAULT_RECEIVING_WINDOW,
        currentTimeMillis(), apiKey, secret);
  }

  public Call<MarginTransaction> borrow(String asset, String symbol, String amount, String apiKey, String secret) {
    return binanceApiService.borrow(asset, IS_ISOLATED, symbol, amount, BinanceApiConstants.DEFAULT_MARGIN_RECEIVING_WINDOW,
        currentTimeMillis(), apiKey, secret);
  }

  public Call<MarginTransaction> repay(String asset, String symbol, String amount, String apiKey, String secret) {
    return binanceApiService.repay(asset, IS_ISOLATED, symbol, amount, BinanceApiConstants.DEFAULT_MARGIN_RECEIVING_WINDOW,
        currentTimeMillis(), apiKey, secret);
  }

  public Call<IsolatedMarginTransferResult> transfer(IsolatedMarginTransfer transfer, String apiKey, String secret) {
    return binanceApiService.transfer(transfer.getAsset(), transfer.getSymbol(), transfer.getFrom().name(), transfer.getTo().name(),
        transfer.getAmount().toPlainString(), DEFAULT_RECEIVING_WINDOW, currentTimeMillis(), apiKey, secret);
  }

  public Call<IsolatedMarginSymbol> getSymbol(String symbol, String apiKey, String secret) {
    return binanceApiService.querySymbol(symbol, DEFAULT_RECEIVING_WINDOW, currentTimeMillis(), apiKey, secret);
  }

  public Call<List<IsolatedMarginSymbol>> getSymbols(String apiKey, String secret) {
    return binanceApiService.querySymbols(DEFAULT_RECEIVING_WINDOW, currentTimeMillis(), apiKey, secret);
  }

  public Call<ListenKey> startUserDataStream(String symbol, String apiKey) {
    return binanceApiService.startIsolatedMarginUserDataStream(symbol, apiKey);
  }

  public Call<Void> keepAliveUserDataStream(String symbol, String listenKey, String apiKey) {
    return binanceApiService.keepAliveIsolatedMarginUserDataStream(symbol, listenKey, apiKey);
  }

  public Call<Void> closeUserDataStream(String symbol, String listenKey, String apiKey) {
    return binanceApiService.closeIsolatedMarginAliveUserDataStream(symbol, listenKey, apiKey);
  }

}
