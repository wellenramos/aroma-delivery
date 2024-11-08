package br.com.aroma.aroma_delivery.service.strategy;

public class MasterCardStrategy implements BandeiraCartaoStrategy {

  @Override
  public String identificarBandeira(String numeroCartao) {
    if ((numeroCartao.startsWith("51") || numeroCartao.startsWith("52") || numeroCartao.startsWith(
        "53") ||
        numeroCartao.startsWith("54") || numeroCartao.startsWith("55") ||
        (Integer.parseInt(numeroCartao.substring(0, 4)) >= 2221
            && Integer.parseInt(numeroCartao.substring(0, 4)) <= 2720))
        && numeroCartao.length() == 16) {
      return "MasterCard";
    }
    return null;
  }
}