package com.binance.api.client;

import java.util.List;

import com.binance.api.client.domain.SwapRemoveType;
import com.binance.api.client.domain.account.Liquidity;
import com.binance.api.client.domain.account.LiquidityOperationRecord;
import com.binance.api.client.domain.account.Pool;
import com.binance.api.client.domain.account.SwapHistory;
import com.binance.api.client.domain.account.SwapQuote;
import com.binance.api.client.domain.account.SwapRecord;

public interface BinanceApiSwapRestClient {

    /**
     * Get metadata about all swap pools.
     *
     * @return
     */
    List<Pool> listAllSwapPools(String api);

    /**
     * Get liquidity information and user share of a pool.
     *
     * @param poolId
     * @return
     */
    Liquidity getPoolLiquidityInfo(String poolId, String apiKey,  String secret);

    /**
     * Add liquidity to a pool.
     *
     * @param poolId
     * @param asset
     * @param quantity
     * @return
     */
    LiquidityOperationRecord addLiquidity(String poolId,
                                          String asset,
                                          String quantity, String apiKey,  String secret);

    /**
     * Remove liquidity from a pool, type include SINGLE and COMBINATION, asset is mandatory for single asset removal
     *
     * @param poolId
     * @param type
     * @param asset
     * @param shareAmount
     * @return
     */
    LiquidityOperationRecord removeLiquidity(String poolId, SwapRemoveType type, List<String> asset, String shareAmount, String apiKey,  String secret);

    /**
     * Get liquidity operation (add/remove) records of a pool
     *
     * @param poolId
     * @param limit
     * @return
     */
    List<LiquidityOperationRecord> getPoolLiquidityOperationRecords(
            String poolId,
            Integer limit, String apiKey,  String secret);

    /**
     * Get liquidity operation (add/remove) record.
     *
     * @param operationId
     * @return
     */
    LiquidityOperationRecord getLiquidityOperationRecord(String operationId, String apiKey,  String secret);

    /**
     * Request a quote for swap quote asset (selling asset) for base asset (buying asset), essentially price/exchange rates.
     *
     * @param quoteAsset
     * @param baseAsset
     * @param quoteQty
     * @return
     */
    SwapQuote requestQuote(String quoteAsset,
                           String baseAsset,
                           String quoteQty, String apiKey,  String secret);

    /**
     * Swap quoteAsset for baseAsset
     *
     * @param quoteAsset
     * @param baseAsset
     * @param quoteQty
     * @return
     */
    SwapRecord swap(String quoteAsset,
                    String baseAsset,
                    String quoteQty, String apiKey,  String secret);

    SwapHistory getSwapHistory(String swapId, String apiKey,  String secret);
}
