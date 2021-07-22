package com.binance.api.client.impl;

import java.util.List;
import java.util.Optional;

import com.binance.api.client.BinanceApiAsyncIsolatedMarginClient;
import com.binance.api.client.BinanceApiCallback;
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

public class BinanceApiAsyncIsolatedMarginClientImpl implements BinanceApiAsyncIsolatedMarginClient {

    private BinanceApiAsyncIsolatedMarginClientBase clientBase;

    public BinanceApiAsyncIsolatedMarginClientImpl() {
        clientBase = new BinanceApiAsyncIsolatedMarginClientBase();
    }

    @Override
    public void createAccount(String apiKey, String secret, String base, String quote, BinanceApiCallback<NewIsolatedAccountResponse> cb) {
        clientBase.createAccount(base, quote, apiKey, secret).enqueue(new BinanceApiCallbackAdapter<>(cb));
    }

    @Override
    public void queryAccount(String apiKey, String secret, Optional<List<String>> symbols, BinanceApiCallback<IsolatedMarginAccountInfo> cb) {
        clientBase.queryAccount(symbols, apiKey, secret).enqueue(new BinanceApiCallbackAdapter<>(cb));
    }

    @Override
    public void getOpenOrders(String apiKey, String secret, OrderRequest orderRequest, BinanceApiCallback<List<Order>> callback) {
        clientBase.getOpenOrders(orderRequest, apiKey, secret).enqueue(new BinanceApiCallbackAdapter<>(callback));
    }

    @Override
    public void newOrder(String apiKey, String secret, MarginNewOrder order, BinanceApiCallback<MarginNewOrderResponse> callback) {
        clientBase.newOrder(order, apiKey, secret).enqueue(new BinanceApiCallbackAdapter<>(callback));
    }

    @Override
    public void cancelOrder(String apiKey, String secret, CancelOrderRequest cancelOrderRequest, BinanceApiCallback<CancelOrderResponse> callback) {
        clientBase.cancelOrder(cancelOrderRequest, apiKey, secret).enqueue(new BinanceApiCallbackAdapter<>(callback));
    }

    @Override
    public void getOrderStatus(String apiKey, String secret, OrderStatusRequest orderStatusRequest, BinanceApiCallback<Order> callback) {
        clientBase.getOrderStatus(orderStatusRequest, apiKey, secret).enqueue(new BinanceApiCallbackAdapter<>(callback));
    }

    @Override
    public void getMyTrades(String apiKey, String secret, String symbol, BinanceApiCallback<List<Trade>> callback) {
        clientBase.getMyTrades(symbol, apiKey, secret).enqueue(new BinanceApiCallbackAdapter<>(callback));
    }

    @Override
    public void borrow(String apiKey, String secret, String asset, String symbol, String amount, BinanceApiCallback<MarginTransaction> callback) {
        clientBase.borrow(asset, symbol, amount, apiKey, secret).enqueue(new BinanceApiCallbackAdapter<>(callback));
    }

    @Override
    public void repay(String apiKey, String secret, String asset, String symbol, String amount, BinanceApiCallback<MarginTransaction> callback) {
        clientBase.repay(asset, symbol, amount, apiKey, secret).enqueue(new BinanceApiCallbackAdapter<>(callback));
    }

    @Override
    public void transfer(String apiKey, String secret, IsolatedMarginTransfer transfer, BinanceApiCallback<IsolatedMarginTransferResult> cb) {
        clientBase.transfer(transfer, apiKey, secret).enqueue(new BinanceApiCallbackAdapter<>(cb));
    }

    @Override
    public void getSymbol(String apiKey, String secret, String symbol, BinanceApiCallback<IsolatedMarginSymbol> cb) {
        clientBase.getSymbol(symbol, apiKey, secret).enqueue(new BinanceApiCallbackAdapter<>(cb));
    }

    @Override
    public void getSymbols(String apiKey, String secret, BinanceApiCallback<List<IsolatedMarginSymbol>> cb) {
        clientBase.getSymbols(apiKey, secret).enqueue(new BinanceApiCallbackAdapter<>(cb));
    }

    @Override
    public void startUserDataStream(String apiKey, String symbol, BinanceApiCallback<ListenKey> callback) {
        clientBase.startUserDataStream(symbol, apiKey).enqueue(new BinanceApiCallbackAdapter<>(callback));
    }

    @Override
    public void keepAliveUserDataStream(String apiKey, String symbol, String listenKey, BinanceApiCallback<Void> callback) {
        clientBase.keepAliveUserDataStream(symbol, listenKey, apiKey).enqueue(new BinanceApiCallbackAdapter<>(callback));
    }

    @Override
    public void closeUserDataStream(String apiKey, String symbol, String listenKey, BinanceApiCallback<Void> callback) {
        clientBase.closeUserDataStream(symbol, listenKey, apiKey).enqueue(new BinanceApiCallbackAdapter<>(callback));
    }

}
