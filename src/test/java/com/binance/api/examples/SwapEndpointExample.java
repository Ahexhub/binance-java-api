package com.binance.api.examples;

import com.binance.api.client.BinanceApiClientFactory;
import com.binance.api.client.BinanceApiSwapRestClient;
import com.binance.api.client.domain.account.*;

import java.util.List;

public class SwapEndpointExample {

    public static String API_KEY = "api-key";
    public static String SECRET_KEY = "secret-key";

    public static void main(String[] args) {
        BinanceApiClientFactory binanceApiClientFactory = BinanceApiClientFactory.newInstance();
        BinanceApiSwapRestClient swapClient = binanceApiClientFactory.newSwapRestClient();
        List<Pool> pools = swapClient.listAllSwapPools(API_KEY);
        for(Pool pool:pools) {
            System.out.println(pool);
            Liquidity poolLiquidityInfo = swapClient.getPoolLiquidityInfo(pool.getPoolId(), API_KEY, SECRET_KEY);
            System.out.println(poolLiquidityInfo);
        }
        SwapQuote swapQuote = swapClient.requestQuote("USDT", "USDC", "10", API_KEY, SECRET_KEY);
        System.out.println(swapQuote);
        SwapRecord swapRecord = swapClient.swap("USDT", "USDC", "10", API_KEY, SECRET_KEY);
        SwapHistory swapHistory = swapClient.getSwapHistory(swapRecord.getSwapId(), API_KEY, SECRET_KEY);
        System.out.println(swapHistory);
    }


}
