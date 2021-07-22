package com.binance.api.client.impl;

import static com.binance.api.client.impl.ApiServiceGenerator.executeSync;
import static java.lang.System.currentTimeMillis;

import java.util.List;

import com.binance.api.client.BinanceApiRestClient;
import com.binance.api.client.config.BinanceApiConfig;
import com.binance.api.client.constant.BinanceApiConstants;
import com.binance.api.client.domain.OrderType;
import com.binance.api.client.domain.TimeInForce;
import com.binance.api.client.domain.account.Account;
import com.binance.api.client.domain.account.DepositAddress;
import com.binance.api.client.domain.account.DepositHistory;
import com.binance.api.client.domain.account.DustTransferResponse;
import com.binance.api.client.domain.account.NewOrder;
import com.binance.api.client.domain.account.NewOrderResponse;
import com.binance.api.client.domain.account.OcoOrderResponse;
import com.binance.api.client.domain.account.Order;
import com.binance.api.client.domain.account.Trade;
import com.binance.api.client.domain.account.TradeHistoryItem;
import com.binance.api.client.domain.account.WithdrawHistory;
import com.binance.api.client.domain.account.WithdrawResult;
import com.binance.api.client.domain.account.request.AllOrdersRequest;
import com.binance.api.client.domain.account.request.CancelOrderRequest;
import com.binance.api.client.domain.account.request.CancelOrderResponse;
import com.binance.api.client.domain.account.request.OcoOrderStatusRequest;
import com.binance.api.client.domain.account.request.OrderRequest;
import com.binance.api.client.domain.account.request.OrderStatusRequest;
import com.binance.api.client.domain.account.request.SubAccountTransfer;
import com.binance.api.client.domain.general.Asset;
import com.binance.api.client.domain.general.ExchangeInfo;
import com.binance.api.client.domain.market.AggTrade;
import com.binance.api.client.domain.market.BookTicker;
import com.binance.api.client.domain.market.Candlestick;
import com.binance.api.client.domain.market.CandlestickInterval;
import com.binance.api.client.domain.market.OrderBook;
import com.binance.api.client.domain.market.TickerPrice;
import com.binance.api.client.domain.market.TickerStatistics;

import retrofit2.Call;

/**
 * Implementation of Binance's REST API using Retrofit with synchronous/blocking method calls.
 */
public class BinanceApiRestClientImpl implements BinanceApiRestClient {

    private final BinanceApiService binanceApiService;

    public BinanceApiRestClientImpl() {
        binanceApiService = ApiServiceGenerator.createService(BinanceApiService.class);
    }

    public BinanceApiRestClientImpl(boolean testnet) {
        if (testnet)
            binanceApiService = ApiServiceGenerator.createTestnetService(BinanceApiService.class);
        else
            binanceApiService = ApiServiceGenerator.createService(BinanceApiService.class);

    }

    // General endpoints

    @Override
    public void ping() {
        executeSync(binanceApiService.ping());
    }

    @Override
    public Long getServerTime() {
        return executeSync(binanceApiService.getServerTime()).getServerTime();
    }

    @Override
    public ExchangeInfo getExchangeInfo() {
        return executeSync(binanceApiService.getExchangeInfo());
    }

    @Override
    public List<Asset> getAllAssets() {
        return executeSync(binanceApiService.getAllAssets(BinanceApiConfig.getAssetInfoApiBaseUrl() + "assetWithdraw/getAllAsset.html"));
    }

    // Market Data endpoints

    @Override
    public OrderBook getOrderBook(String symbol, Integer limit) {
        return executeSync(binanceApiService.getOrderBook(symbol, limit));
    }

    @Override
    public List<TradeHistoryItem> getTrades(String symbol, Integer limit) {
        return executeSync(binanceApiService.getTrades(symbol, limit));
    }

    @Override
    public List<TradeHistoryItem> getHistoricalTrades(String symbol, Integer limit, Long fromId) {
        return executeSync(binanceApiService.getHistoricalTrades(symbol, limit, fromId));
    }

    @Override
    public List<AggTrade> getAggTrades(String symbol, String fromId, Integer limit, Long startTime, Long endTime) {
        return executeSync(binanceApiService.getAggTrades(symbol, fromId, limit, startTime, endTime));
    }

    @Override
    public List<AggTrade> getAggTrades(String symbol) {
        return getAggTrades(symbol, null, null, null, null);
    }

    @Override
    public List<Candlestick> getCandlestickBars(String symbol, CandlestickInterval interval, Integer limit, Long startTime, Long endTime) {
        return executeSync(binanceApiService.getCandlestickBars(symbol, interval.getIntervalId(), limit, startTime, endTime));
    }

    @Override
    public List<Candlestick> getCandlestickBars(String symbol, CandlestickInterval interval) {
        return getCandlestickBars(symbol, interval, null, null, null);
    }

    @Override
    public TickerStatistics get24HrPriceStatistics(String symbol) {
        return executeSync(binanceApiService.get24HrPriceStatistics(symbol));
    }

    @Override
    public List<TickerStatistics> getAll24HrPriceStatistics() {
        return executeSync(binanceApiService.getAll24HrPriceStatistics());
    }

    @Override
    public TickerPrice getPrice(String symbol) {
        return executeSync(binanceApiService.getLatestPrice(symbol));
    }

    @Override
    public List<TickerPrice> getAllPrices() {
        return executeSync(binanceApiService.getLatestPrices());
    }

    @Override
    public List<BookTicker> getBookTickers() {
        return executeSync(binanceApiService.getBookTickers());
    }

    @Override
    public NewOrderResponse newOrder(NewOrder order, String apiKey, String secret) {
        if (order.getType() == OrderType.OCO) {
            throw new IllegalArgumentException("Please use newOcoOrder instead");
        }
        final Call<NewOrderResponse> call;
        if (order.getQuoteOrderQty() == null) {
            call = binanceApiService.newOrder(order.getSymbol(), order.getSide(), order.getType(),
                    order.getTimeInForce(), order.getQuantity(), order.getPrice(),
                    order.getNewClientOrderId(), order.getStopPrice(), order.getIcebergQty(), order.getNewOrderRespType(),
                    order.getRecvWindow(), order.getTimestamp(), apiKey, secret);
        } else {
            call = binanceApiService.newOrderQuoteQty(order.getSymbol(), order.getSide(), order.getType(),
                    order.getTimeInForce(), order.getQuoteOrderQty(), order.getPrice(),
                    order.getNewClientOrderId(), order.getStopPrice(), order.getIcebergQty(), order.getNewOrderRespType(),
                    order.getRecvWindow(), order.getTimestamp());
        }
        return executeSync(call);
    }

    @Override
    public OcoOrderResponse newOcoOrder(NewOrder order, String apiKey, String secret) {
        return executeSync(binanceApiService.newOcoOrder(order.getSymbol(), order.getNewClientOrderId(), order.getSide(), order.getQuantity(), order.getLimitClientOrderId(),
                order.getPrice(), order.getIcebergQty(), order.getStopClientOrderId(), order.getStopPrice(), order.getStopLimitPrice(), TimeInForce.GTC,
                order.getNewOrderRespType(), order.getRecvWindow(), order.getTimestamp(), apiKey, secret));
    }

    @Override
    public void newOrderTest(NewOrder order, String apiKey, String secret) {
        if (order.getType() == OrderType.OCO) {
            executeSync(binanceApiService.newOcoOrderTest(order.getSymbol(), order.getNewClientOrderId(), order.getSide(), order.getQuantity(), order.getLimitClientOrderId(),
                    order.getPrice(), order.getIcebergQty(), order.getStopClientOrderId(), order.getStopPrice(), order.getStopLimitPrice(), TimeInForce.GTC,
                    order.getNewOrderRespType(), order.getRecvWindow(), order.getTimestamp(), apiKey, secret));
        } else {
            executeSync(binanceApiService.newOrderTest(order.getSymbol(), order.getSide(), order.getType(),
                    order.getTimeInForce(), order.getQuantity(), order.getPrice(), order.getNewClientOrderId(), order.getStopPrice(),
                    order.getIcebergQty(), order.getNewOrderRespType(), order.getRecvWindow(), order.getTimestamp(), apiKey, secret));
        }
    }

    // Account endpoints

    @Override
    public Order getOrderStatus(OrderStatusRequest orderStatusRequest, String apiKey, String secret) {
        return executeSync(binanceApiService.getOrderStatus(orderStatusRequest.getSymbol(),
                orderStatusRequest.getOrderId(), orderStatusRequest.getOrigClientOrderId(),
                orderStatusRequest.getRecvWindow(), orderStatusRequest.getTimestamp(), apiKey, secret));
    }

    @Override
    public OcoOrderResponse getOcoOrderStatus(OcoOrderStatusRequest statusRequest, String apiKey, String secret) {
        return executeSync(binanceApiService.getOcoOrderStatus(statusRequest.getOrderListId(), statusRequest.getOrigClientOrderId(),
                statusRequest.getRecvWindow(), statusRequest.getTimestamp(), apiKey, secret));
    }

    @Override
    public CancelOrderResponse cancelOrder(CancelOrderRequest cancelOrderRequest, String apiKey, String secret) {
        return executeSync(binanceApiService.cancelOrder(cancelOrderRequest.getSymbol(),
                cancelOrderRequest.getOrderId(), cancelOrderRequest.getOrigClientOrderId(), cancelOrderRequest.getNewClientOrderId(),
                cancelOrderRequest.getRecvWindow(), cancelOrderRequest.getTimestamp(), apiKey, secret));
    }

    @Override
    public OcoOrderResponse cancelOcoOrder(CancelOrderRequest cancelOrderRequest, String apiKey, String secret) {
        return executeSync(binanceApiService.cancelOcoOrder(cancelOrderRequest.getSymbol(),
                cancelOrderRequest.getOrderId(), cancelOrderRequest.getOrigClientOrderId(), cancelOrderRequest.getNewClientOrderId(),
                cancelOrderRequest.getRecvWindow(), cancelOrderRequest.getTimestamp(), apiKey, secret));
    }

    @Override
    public List<Order> getOpenOrders(OrderRequest orderRequest, String apiKey, String secret) {
        return executeSync(binanceApiService.getOpenOrders(orderRequest.getSymbol(), orderRequest.getRecvWindow(), orderRequest.getTimestamp(), apiKey, secret));
    }

    @Override
    public List<Order> getAllOrders(AllOrdersRequest orderRequest, String apiKey, String secret) {
        return executeSync(binanceApiService.getAllOrders(orderRequest.getSymbol(),
                orderRequest.getOrderId(), orderRequest.getLimit(),
                orderRequest.getRecvWindow(), orderRequest.getTimestamp(), apiKey, secret));
    }

    @Override
    public Account getAccount(Long recvWindow, Long timestamp, String apiKey, String secret) {
        return executeSync(binanceApiService.getAccount(recvWindow, timestamp, apiKey, secret));
    }

    @Override
    public Account getAccount(String apiKey, String secret) {
        return getAccount(BinanceApiConstants.DEFAULT_RECEIVING_WINDOW, System.currentTimeMillis(), apiKey, secret);
    }

    @Override
    public List<Trade> getMyTrades(String symbol, Integer limit, Long fromId, Long recvWindow, Long timestamp, String apiKey, String secret) {
        return executeSync(binanceApiService.getMyTrades(symbol, limit, fromId, recvWindow, timestamp, apiKey, secret));
    }

    @Override
    public List<Trade> getMyTrades(String symbol, Integer limit, String apiKey, String secret) {
        return getMyTrades(symbol, limit, null, BinanceApiConstants.DEFAULT_RECEIVING_WINDOW, System.currentTimeMillis(), apiKey, secret);
    }

    @Override
    public List<Trade> getMyTrades(String symbol, String apiKey, String secret) {
        return getMyTrades(symbol, null, null, BinanceApiConstants.DEFAULT_RECEIVING_WINDOW, System.currentTimeMillis(), apiKey, secret);
    }


    @Override
    public List<Trade> getMyTrades(String symbol, Long fromId, String apiKey, String secret) {
        return getMyTrades(symbol, null, fromId, BinanceApiConstants.DEFAULT_RECEIVING_WINDOW,
                System.currentTimeMillis(), apiKey, secret);
    }

    @Override
    public WithdrawResult withdraw(String asset, String address, String amount, String name, String addressTag, String apiKey, String secret) {
        return executeSync(binanceApiService.withdraw(asset, address, amount, name, addressTag, BinanceApiConstants.DEFAULT_RECEIVING_WINDOW, System.currentTimeMillis(), apiKey, secret));
    }

    @Override
    public DepositHistory getDepositHistory(String asset, String apiKey, String secret) {
        return executeSync(binanceApiService.getDepositHistory(asset, BinanceApiConstants.DEFAULT_RECEIVING_WINDOW, System.currentTimeMillis(), apiKey, secret));
    }

    @Override
    public WithdrawHistory getWithdrawHistory(String asset, String apiKey, String secret) {
        return executeSync(binanceApiService.getWithdrawHistory(asset, BinanceApiConstants.DEFAULT_RECEIVING_WINDOW, System.currentTimeMillis(), apiKey, secret));
    }

    @Override
    public List<SubAccountTransfer> getSubAccountTransfers(String apiKey, String secret) {
        return executeSync(binanceApiService.getSubAccountTransfers(System.currentTimeMillis(), apiKey, secret));
    }

    @Override
    public DepositAddress getDepositAddress(String asset, String apiKey, String secret) {
        return executeSync(binanceApiService.getDepositAddress(asset, BinanceApiConstants.DEFAULT_RECEIVING_WINDOW, System.currentTimeMillis(), apiKey, secret));
    }

    @Override
    public DustTransferResponse convertDustToBnb(List<String> assets, String apiKey, String secret) {
        return executeSync(binanceApiService.dustTransfer(assets, BinanceApiConstants.DEFAULT_RECEIVING_WINDOW, currentTimeMillis(), apiKey, secret));
    }

    // User stream endpoints

    @Override
    public String startUserDataStream(String api) {
        return executeSync(binanceApiService.startUserDataStream(api)).getListenKey();
    }

    @Override
    public void keepAliveUserDataStream(String listenKey, String api) {
        executeSync(binanceApiService.keepAliveUserDataStream(listenKey, api));
    }

    @Override
    public void closeUserDataStream(String listenKey, String api) {
        executeSync(binanceApiService.closeAliveUserDataStream(listenKey, api));
    }
}
