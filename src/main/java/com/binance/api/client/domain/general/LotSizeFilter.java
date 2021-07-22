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
    val stepSize = (int) Double.parseDouble(getStepSize());
    if (stepSize >= 1) return 0;
    DecimalFormat df = new DecimalFormat("0", DecimalFormatSymbols.getInstance(Locale.ENGLISH));
    df.setMaximumFractionDigits(340);
    return df.format(Double.parseDouble("1")).replace("0.", "").length();
  }

}
