package com.binance.api.client.impl;

import java.util.List;

import com.binance.api.client.BinanceApiSwapRestClient;
import com.binance.api.client.constant.BinanceApiConstants;
import com.binance.api.client.domain.SwapRemoveType;
import com.binance.api.client.domain.account.Liquidity;
import com.binance.api.client.domain.account.LiquidityOperationRecord;
import com.binance.api.client.domain.account.Pool;
import com.binance.api.client.domain.account.SwapHistory;
import com.binance.api.client.domain.account.SwapQuote;
import com.binance.api.client.domain.account.SwapRecord;
import retrofit2.http.Header;

/**
 * Implementation of Binance's SWAP REST API using Retrofit method calls.
 */
public class BinanceApiSwapRestClientImpl implements BinanceApiSwapRestClient {

    private final BinanceApiService binanceApiService;

    public BinanceApiSwapRestClientImpl() {
        binanceApiService = ApiServiceGenerator.createService(BinanceApiService.class);
    }

    @Override
    public List<Pool> listAllSwapPools(String api) {
        return ApiServiceGenerator.executeSync(binanceApiService.listAllSwapPools(api));
    }

    @Override
    public Liquidity getPoolLiquidityInfo(String poolId, String apiKey, String secret) {
        long timestamp = System.currentTimeMillis();
        List<Liquidity> liquidities = ApiServiceGenerator.executeSync(binanceApiService.getPoolLiquidityInfo(poolId,
                BinanceApiConstants.DEFAULT_RECEIVING_WINDOW,
                timestamp,
                apiKey, secret));
        if (liquidities != null && !liquidities.isEmpty()) {
            return liquidities.get(0);
        }
        return null;
    }

    @Override
    public LiquidityOperationRecord addLiquidity(String poolId, String asset, String quantity, String apiKey, String secret) {
        long timestamp = System.currentTimeMillis();
        return ApiServiceGenerator.executeSync(binanceApiService.addLiquidity(poolId,
                asset,
                quantity,
                BinanceApiConstants.DEFAULT_RECEIVING_WINDOW,
                timestamp,
                apiKey, secret));
    }

    @Override
    public LiquidityOperationRecord removeLiquidity(String poolId, SwapRemoveType type, List<String> asset, String shareAmount, String apiKey, String secret) {
        long timestamp = System.currentTimeMillis();
        return ApiServiceGenerator.executeSync(binanceApiService.removeLiquidity(poolId,
                type,
                asset,
                shareAmount,
                BinanceApiConstants.DEFAULT_RECEIVING_WINDOW,
                timestamp,
                apiKey, secret));
    }

    @Override
    public List<LiquidityOperationRecord> getPoolLiquidityOperationRecords(String poolId, Integer limit, String apiKey, String secret) {
        long timestamp = System.currentTimeMillis();
        return ApiServiceGenerator.executeSync(binanceApiService.getPoolLiquidityOperationRecords(
                poolId,
                limit,
                BinanceApiConstants.DEFAULT_RECEIVING_WINDOW,
                timestamp,
                apiKey, secret));

    }

    @Override
    public LiquidityOperationRecord getLiquidityOperationRecord(String operationId, String apiKey, String secret) {
        long timestamp = System.currentTimeMillis();
        List<LiquidityOperationRecord> records = ApiServiceGenerator.executeSync(binanceApiService.getLiquidityOperationRecord(
                operationId,
                BinanceApiConstants.DEFAULT_RECEIVING_WINDOW,
                timestamp, apiKey, secret));
        if (records != null && !records.isEmpty()) {
            return records.get(0);
        }
        return null;
    }

    @Override
    public SwapQuote requestQuote(String quoteAsset,
                                  String baseAsset,
                                  String quoteQty, String apiKey, String secret) {
        long timestamp = System.currentTimeMillis();
        return ApiServiceGenerator.executeSync(binanceApiService.requestQuote(quoteAsset, baseAsset, quoteQty,
                BinanceApiConstants.DEFAULT_RECEIVING_WINDOW,
                timestamp, apiKey, secret));
    }

    @Override
    public SwapRecord swap(String quoteAsset, String baseAsset, String quoteQty, String apiKey, String secret) {
        long timestamp = System.currentTimeMillis();
        return ApiServiceGenerator.executeSync(binanceApiService.swap(quoteAsset, baseAsset, quoteQty,
                BinanceApiConstants.DEFAULT_RECEIVING_WINDOW,
                timestamp, apiKey, secret));
    }

    @Override
    public SwapHistory getSwapHistory(String swapId, String apiKey, String secret) {
        long timestamp = System.currentTimeMillis();
        List<SwapHistory> history = ApiServiceGenerator.executeSync(binanceApiService.getSwapHistory(swapId,
                BinanceApiConstants.DEFAULT_RECEIVING_WINDOW,
                timestamp, apiKey, secret));
        if (history != null && !history.isEmpty()) {
            return history.get(0);
        }
        return null;
    }
}