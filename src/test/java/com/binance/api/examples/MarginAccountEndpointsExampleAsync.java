package com.binance.api.examples;

import com.binance.api.client.BinanceApiAsyncMarginRestClient;
import com.binance.api.client.BinanceApiClientFactory;
import com.binance.api.client.domain.TransferType;

/**
 * Examples on how to get margin account information asynchronously.
 */
public class MarginAccountEndpointsExampleAsync {

    public static void main(String[] args) {
        BinanceApiClientFactory factory = BinanceApiClientFactory.newInstance();
        BinanceApiAsyncMarginRestClient client = factory.newAsyncMarginRestClient();

        // Get account balances
        client.getAccount("API_KEY", "SECRET", marginAccount -> {
            System.out.println(marginAccount.getUserAssets());
            System.out.println(marginAccount.getAssetBalance("ETH"));
            System.out.println(marginAccount.getMarginLevel());
        });

        // Get list of trades
        client.getMyTrades("API_KEY", "SECRET", "NEOETH", myTrades -> {
            System.out.println(myTrades);
        });

        // Transfer, borrow, repay
        client.transfer("API_KEY", "SECRET", "USDT", "1", TransferType.SPOT_TO_MARGIN, transaction -> System.out.println(transaction.getTranId()));
        client.borrow("API_KEY", "SECRET", "USDT", "1", transaction -> System.out.println(transaction.getTranId()));
        client.repay("API_KEY", "SECRET", "USDT", "1", transaction -> System.out.println(transaction.getTranId()));
        client.transfer("API_KEY", "SECRET", "USDT", "1", TransferType.MARGIN_TO_SPOT, transaction -> System.out.println(transaction.getTranId()));
    }
}
