package com.binance.api.examples;

import com.binance.api.client.BinanceApiAsyncRestClient;
import com.binance.api.client.BinanceApiClientFactory;
import com.binance.api.client.domain.account.Account;

/**
 * Examples on how to get account information.
 */
public class AccountEndpointsExampleAsync {

  public static void main(String[] args) {
    BinanceApiClientFactory factory = BinanceApiClientFactory.newInstance();
    BinanceApiAsyncRestClient client = factory.newAsyncRestClient();

    // Get account balances (async)
    client.getAccount("API_KEY", "SECRET", (Account response) -> System.out.println(response.getAssetBalance("ETH")));

    // Get list of trades (async)
    client.getMyTrades("API_KEY", "SECRET", "NEOETH", response -> System.out.println(response));

    // Get withdraw history (async)
    client.getWithdrawHistory("API_KEY", "SECRET", "ETH", response -> System.out.println(response));

    // Get deposit history (async)
    client.getDepositHistory("API_KEY", "SECRET", "ETH", response -> System.out.println(response));

    // Withdraw (async)
    client.withdraw("API_KEY", "SECRET", "ETH", "0x123", "0.1", null, null, response -> {});
  }
}
