package com.binance.api.examples;

import com.binance.api.client.BinanceApiClientFactory;
import com.binance.api.client.BinanceApiMarginRestClient;
import com.binance.api.client.domain.account.MarginTransaction;
import com.binance.api.client.domain.account.MaxBorrowableQueryResult;
import com.binance.api.client.domain.account.RepayQueryResult;

/**
 * Examples on how to get margin account information.
 */
public class MarginAccountEndpointsLoanQueryExample {

    public static void main(String[] args) {
        BinanceApiClientFactory factory = BinanceApiClientFactory.newInstance();
        BinanceApiMarginRestClient client = factory.newMarginRestClient();
        MaxBorrowableQueryResult usdt = client.queryMaxBorrowable("USDT", "API_KEY", "SECRET");
        System.out.println(usdt.getAmount());
        MaxBorrowableQueryResult bnb = client.queryMaxBorrowable("BNB", "API_KEY", "SECRET");
        System.out.println(bnb.getAmount());
        MarginTransaction borrowed = client.borrow("USDT", "310", "API_KEY", "SECRET");
        System.out.println(borrowed.getTranId());
        MarginTransaction repaid = client.repay("USDT", "310", "API_KEY", "SECRET");
        System.out.println(repaid);
        RepayQueryResult repayQueryResult = client.queryRepay("BNB", System.currentTimeMillis() - 1000, "API_KEY", "SECRET");
        System.out.println(repayQueryResult);
    }
}
