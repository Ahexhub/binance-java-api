package com.binance.api.examples;

import com.binance.api.client.BinanceApiClientFactory;
import com.binance.api.client.BinanceApiRestClient;
import com.binance.api.client.domain.account.Account;
import com.binance.api.client.domain.account.Trade;

import java.util.List;

/**
 * Examples on how to get account information.
 */
public class AccountEndpointsExample {

  public static void main(String[] args) {
    BinanceApiClientFactory factory = BinanceApiClientFactory.newInstance();
    BinanceApiRestClient client = factory.newRestClient();

    // Get account balances
    Account account = client.getAccount(60_000L, System.currentTimeMillis(), "API_KEY", "SECRET");
    System.out.println(account.getBalances());
    System.out.println(account.getAssetBalance("ETH"));

    // Get list of trades
    List<Trade> myTrades = client.getMyTrades("NEOETH", "API_KEY", "SECRET");
    System.out.println(myTrades);

    // Get withdraw history
    System.out.println(client.getWithdrawHistory("ETH", "API_KEY", "SECRET"));

    // Get deposit history
    System.out.println(client.getDepositHistory("ETH", "API_KEY", "SECRET"));

    // Get deposit address
    System.out.println(client.getDepositAddress("ETH", "API_KEY", "SECRET"));

    // Withdraw
    client.withdraw("ETH", "0x123", "0.1", null, null, "API_KEY", "SECRET");
  }
}
