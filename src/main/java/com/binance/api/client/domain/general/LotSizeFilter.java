package com.binance.api.client.domain.general;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.val;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

@Data
@EqualsAndHashCode(callSuper = true)
public class LotSizeFilter extends SymbolFilter {

  String minQty;
  String maxQty;
  String stepSize;

  public boolean validate(double quantity) {
    return quantity >= dbl(minQty) && quantity <= dbl(maxQty) && (quantity - dbl(minQty)) % dbl(stepSize) == 0;
  }

  public int getPrecisionCount() {
    if (stepSize.startsWith("1"))
      return 0;
    return stepSize.split("1")[0].replace("0.", "").length() +1;
  }

}
