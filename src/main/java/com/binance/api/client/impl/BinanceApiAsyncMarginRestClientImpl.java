package com.binance.api.client.impl;

import java.util.List;

import com.binance.api.client.BinanceApiAsyncMarginRestClient;
import com.binance.api.client.BinanceApiCallback;
import com.binance.api.client.constant.BinanceApiConstants;
import com.binance.api.client.domain.TransferType;
import com.binance.api.client.domain.account.MarginAccount;
import com.binance.api.client.domain.account.MarginNewOrder;
import com.binance.api.client.domain.account.MarginNewOrderResponse;
import com.binance.api.client.domain.account.MarginTransaction;
import com.binance.api.client.domain.account.Order;
import com.binance.api.client.domain.account.Trade;
import com.binance.api.client.domain.account.request.CancelOrderRequest;
import com.binance.api.client.domain.account.request.CancelOrderResponse;
import com.binance.api.client.domain.account.request.OrderRequest;
import com.binance.api.client.domain.account.request.OrderStatusRequest;
import com.binance.api.client.domain.event.ListenKey;

/**
 * Implementation of Binance's Margin REST API using Retrofit with asynchronous/non-blocking method calls.
 */
public class BinanceApiAsyncMarginRestClientImpl implements BinanceApiAsyncMarginRestClient {

    private final BinanceApiService binanceApiService;

    public BinanceApiAsyncMarginRestClientImpl() {
        binanceApiService = ApiServiceGenerator.createService(BinanceApiService.class);
    }

    // Margin Account endpoints

    @Override
    public void getAccount(String apiKey, String secret, Long recvWindow, Long timestamp, BinanceApiCallback<MarginAccount> callback) {
        binanceApiService.getMarginAccount(recvWindow, timestamp, apiKey, secret).enqueue(new BinanceApiCallbackAdapter<>(callback));
    }

    @Override
    public void getAccount(String apiKey, String secret, BinanceApiCallback<MarginAccount> callback) {
        long timestamp = System.currentTimeMillis();
        binanceApiService.getMarginAccount(BinanceApiConstants.DEFAULT_MARGIN_RECEIVING_WINDOW, timestamp, apiKey, secret).enqueue(new BinanceApiCallbackAdapter<>(callback));
    }

    @Override
    public void getOpenOrders(String apiKey, String secret, OrderRequest orderRequest, BinanceApiCallback<List<Order>> callback) {
        binanceApiService.getOpenMarginOrders(orderRequest.getSymbol(), null, orderRequest.getRecvWindow(),
                orderRequest.getTimestamp(), apiKey, secret).enqueue(new BinanceApiCallbackAdapter<>(callback));
    }

    @Override
    public void newOrder(String apiKey, String secret, MarginNewOrder order, BinanceApiCallback<MarginNewOrderResponse> callback) {
        binanceApiService.newMarginOrder(order.getSymbol(), null, order.getSide(), order.getType(), order.getTimeInForce(),
                order.getQuantity(), order.getPrice(), order.getNewClientOrderId(), order.getStopPrice(), order.getIcebergQty(),
                order.getNewOrderRespType(), order.getSideEffectType(), order.getRecvWindow(), order.getTimestamp(), apiKey, secret).enqueue(new BinanceApiCallbackAdapter<>(callback));
    }

    @Override
    public void cancelOrder(String apiKey, String secret, CancelOrderRequest cancelOrderRequest, BinanceApiCallback<CancelOrderResponse> callback) {
        binanceApiService.cancelMarginOrder(cancelOrderRequest.getSymbol(), null,
                cancelOrderRequest.getOrderId(), cancelOrderRequest.getOrigClientOrderId(), cancelOrderRequest.getNewClientOrderId(),
                cancelOrderRequest.getRecvWindow(), cancelOrderRequest.getTimestamp(), apiKey, secret).enqueue(new BinanceApiCallbackAdapter<>(callback));
    }

    @Override
    public void getOrderStatus(String apiKey, String secret, OrderStatusRequest orderStatusRequest, BinanceApiCallback<Order> callback) {
        binanceApiService.getMarginOrderStatus(orderStatusRequest.getSymbol(), null,
                orderStatusRequest.getOrderId(), orderStatusRequest.getOrigClientOrderId(),
                orderStatusRequest.getRecvWindow(), orderStatusRequest.getTimestamp(), apiKey, secret).enqueue(new BinanceApiCallbackAdapter<>(callback));
    }

    @Override
    public void getMyTrades(String apiKey, String secret, String symbol, BinanceApiCallback<List<Trade>> callback) {
        binanceApiService.getMyMarginTrades(symbol, null, null, null, BinanceApiConstants.DEFAULT_RECEIVING_WINDOW, System.currentTimeMillis(), apiKey, secret).enqueue(new BinanceApiCallbackAdapter<>(callback));
    }

    // user stream endpoints

    @Override
    public void startUserDataStream(String apiKey, BinanceApiCallback<ListenKey> callback) {
        binanceApiService.startMarginUserDataStream(apiKey).enqueue(new BinanceApiCallbackAdapter<>(callback));
    }

    @Override
    public void keepAliveUserDataStream(String apiKey, String listenKey, BinanceApiCallback<Void> callback) {
        binanceApiService.keepAliveMarginUserDataStream(listenKey, apiKey).enqueue(new BinanceApiCallbackAdapter<>(callback));
    }

    @Override
    public void transfer(String apiKey, String secret, String asset, String amount, TransferType type, BinanceApiCallback<MarginTransaction> callback) {
        long timestamp = System.currentTimeMillis();
        binanceApiService.transfer(asset, amount, type.getValue(), BinanceApiConstants.DEFAULT_MARGIN_RECEIVING_WINDOW, timestamp, apiKey, secret).enqueue(new BinanceApiCallbackAdapter<>(callback));
    }

    @Override
    public void borrow(String apiKey, String secret, String asset, String amount, BinanceApiCallback<MarginTransaction> callback) {
        long timestamp = System.currentTimeMillis();
        binanceApiService.borrow(asset, null, null, amount, BinanceApiConstants.DEFAULT_MARGIN_RECEIVING_WINDOW, timestamp, apiKey, secret).enqueue(new BinanceApiCallbackAdapter<>(callback));
    }

    @Override
    public void repay(String apiKey, String secret, String asset, String amount, BinanceApiCallback<MarginTransaction> callback) {
        long timestamp = System.currentTimeMillis();
        binanceApiService.repay(asset, null, null, amount, BinanceApiConstants.DEFAULT_MARGIN_RECEIVING_WINDOW, timestamp, apiKey, secret).enqueue(new BinanceApiCallbackAdapter<>(callback));
    }
}
