package com.binance.api.client.security;

import java.util.function.Consumer;

import org.apache.commons.lang3.StringUtils;
import org.asynchttpclient.Header;
import org.asynchttpclient.Request;
import org.asynchttpclient.RequestBuilder;
import org.asynchttpclient.RequestBuilderBase;
import org.asynchttpclient.SignatureCalculator;

import com.binance.api.client.constant.BinanceApiConstants;

/**
 * A request interceptor that injects the API Key Header into requests, and
 * signs messages, whenever required.
 */
public class NettyAuthenticationInterceptor implements Consumer<RequestBuilder> {


  public NettyAuthenticationInterceptor() {
  }
  @Override
  public void accept(RequestBuilder request) {
    boolean isApiKeyRequired = Header.get(request, BinanceApiConstants.ENDPOINT_SECURITY_TYPE_APIKEY) != null;
    boolean isSignatureRequired = Header.get(request, BinanceApiConstants.ENDPOINT_SECURITY_TYPE_SIGNED) != null;
    Header.remove(request, BinanceApiConstants.ENDPOINT_SECURITY_TYPE_APIKEY);
    Header.remove(request, BinanceApiConstants.ENDPOINT_SECURITY_TYPE_SIGNED);
    String apiKey = Header.get(request, "api");
    String secret = Header.get(request, "secret");

    // Endpoint requires sending a valid API-KEY
    if (isApiKeyRequired || isSignatureRequired) {
      request.addHeader(BinanceApiConstants.API_KEY_HEADER, apiKey);
    }
    if (isSignatureRequired) {
      request.setSignatureCalculator(new SignatureCalculator() {
        @Override
        public void calculateAndAddSignature(Request request, RequestBuilderBase<?> requestBuilderBase) {
          String payload = request.getUri().getQuery();
          if (!StringUtils.isEmpty(payload)) {
            String signature = HmacSHA256Signer.sign(payload, secret);
            requestBuilderBase.addQueryParam("signature", signature);
          }
        }
      });

    }
  }


}