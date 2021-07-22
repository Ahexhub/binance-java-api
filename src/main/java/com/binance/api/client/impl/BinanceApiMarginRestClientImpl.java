package com.binance.api.client.impl;

import static com.binance.api.client.impl.ApiServiceGenerator.executeSync;
import static java.lang.System.currentTimeMillis;

import java.util.List;

import com.binance.api.client.BinanceApiMarginRestClient;
import com.binance.api.client.constant.BinanceApiConstants;
import com.binance.api.client.domain.TransferType;
import com.binance.api.client.domain.account.LoanQueryResult;
import com.binance.api.client.domain.account.MarginAccount;
import com.binance.api.client.domain.account.MarginNewOrder;
import com.binance.api.client.domain.account.MarginNewOrderResponse;
import com.binance.api.client.domain.account.MarginTransaction;
import com.binance.api.client.domain.account.MaxBorrowableQueryResult;
import com.binance.api.client.domain.account.Order;
import com.binance.api.client.domain.account.RepayQueryResult;
import com.binance.api.client.domain.account.Trade;
import com.binance.api.client.domain.account.request.CancelOrderRequest;
import com.binance.api.client.domain.account.request.CancelOrderResponse;
import com.binance.api.client.domain.account.request.OrderRequest;
import com.binance.api.client.domain.account.request.OrderStatusRequest;

/**
 * Implementation of Binance's Margin REST API using Retrofit with asynchronous/non-blocking method calls.
 */
public class BinanceApiMarginRestClientImpl implements BinanceApiMarginRestClient {

    private final BinanceApiService binanceApiService;

    public BinanceApiMarginRestClientImpl() {
        binanceApiService = ApiServiceGenerator.createService(BinanceApiService.class);
    }

    @Override
    public MarginAccount getAccount(String apiKey, String secret) {
        long timestamp = currentTimeMillis();
        return executeSync(binanceApiService.getMarginAccount(BinanceApiConstants.DEFAULT_RECEIVING_WINDOW, timestamp, apiKey, secret));
    }

    @Override
    public List<Order> getOpenOrders(OrderRequest orderRequest, String apiKey, String secret) {
        return executeSync(binanceApiService.getOpenMarginOrders(orderRequest.getSymbol(), null, orderRequest.getRecvWindow(),
                orderRequest.getTimestamp(), apiKey, secret));
    }

    @Override
    public MarginNewOrderResponse newOrder(MarginNewOrder order, String apiKey, String secret) {
        return executeSync(binanceApiService.newMarginOrder(order.getSymbol(), null, order.getSide(), order.getType(),
                order.getTimeInForce(), order.getQuantity(), order.getPrice(), order.getNewClientOrderId(), order.getStopPrice(),
                order.getIcebergQty(), order.getNewOrderRespType(), order.getSideEffectType(), order.getRecvWindow(), order.getTimestamp(), apiKey, secret));
    }

    @Override
    public CancelOrderResponse cancelOrder(CancelOrderRequest cancelOrderRequest, String apiKey, String secret) {
        return executeSync(binanceApiService.cancelMarginOrder(cancelOrderRequest.getSymbol(), null,
                cancelOrderRequest.getOrderId(), cancelOrderRequest.getOrigClientOrderId(), cancelOrderRequest.getNewClientOrderId(),
                cancelOrderRequest.getRecvWindow(), cancelOrderRequest.getTimestamp(), apiKey, secret));
    }

    @Override
    public Order getOrderStatus(OrderStatusRequest orderStatusRequest, String apiKey, String secret) {
        return executeSync(binanceApiService.getMarginOrderStatus(orderStatusRequest.getSymbol(), null,
                orderStatusRequest.getOrderId(), orderStatusRequest.getOrigClientOrderId(),
                orderStatusRequest.getRecvWindow(), orderStatusRequest.getTimestamp(), apiKey, secret));
    }

    @Override
    public List<Trade> getMyTrades(String symbol, String apiKey, String secret) {
        return executeSync(binanceApiService.getMyTrades(symbol, null, null, BinanceApiConstants.DEFAULT_RECEIVING_WINDOW, System.currentTimeMillis(), apiKey, secret));
    }

    // user stream endpoints

    @Override
    public String startUserDataStream(String apiKey) {
        return executeSync(binanceApiService.startMarginUserDataStream(apiKey)).toString();
    }

    @Override
    public void keepAliveUserDataStream(String listenKey, String apiKey) {
        executeSync(binanceApiService.keepAliveMarginUserDataStream(listenKey, apiKey));
    }

    @Override
    public MarginTransaction transfer(String asset, String amount, TransferType type, String apiKey, String secret) {
        long timestamp = System.currentTimeMillis();
        return executeSync(binanceApiService.transfer(asset, amount, type.getValue(), BinanceApiConstants.DEFAULT_RECEIVING_WINDOW, timestamp, apiKey, secret));
    }

    @Override
    public MarginTransaction borrow(String asset, String amount, String apiKey, String secret) {
        long timestamp = System.currentTimeMillis();
        return executeSync(binanceApiService.borrow(asset, null, null, amount, BinanceApiConstants.DEFAULT_RECEIVING_WINDOW, timestamp, apiKey, secret));
    }

    @Override
    public LoanQueryResult queryLoan(String asset, String txId, String apiKey, String secret) {
        long timestamp = System.currentTimeMillis();
        return executeSync(binanceApiService.queryLoan(asset, null, txId, timestamp, apiKey, secret));
    }

    @Override
    public RepayQueryResult queryRepay(String asset, String txId, String apiKey, String secret) {
        long timestamp = System.currentTimeMillis();
        return executeSync(binanceApiService.queryRepay(asset, null, txId, timestamp, apiKey, secret));
    }

    @Override
    public RepayQueryResult queryRepay(String asset, long startTime, String apiKey, String secret) {
        long timestamp = System.currentTimeMillis();
        return executeSync(binanceApiService.queryRepay(asset, null, startTime, timestamp, apiKey, secret));
    }

    @Override
    public MaxBorrowableQueryResult queryMaxBorrowable(String asset, String apiKey, String secret) {
        long timestamp = System.currentTimeMillis();
        return executeSync(binanceApiService.queryMaxBorrowable(asset, null, timestamp, apiKey, secret));
    }

    @Override
    public MarginTransaction repay(String asset, String amount, String apiKey, String secret) {
        long timestamp = System.currentTimeMillis();
        return executeSync(binanceApiService.repay(asset, null, null, amount, BinanceApiConstants.DEFAULT_RECEIVING_WINDOW, timestamp, apiKey, secret));
    }
}