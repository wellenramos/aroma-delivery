package br.com.aroma.aroma_delivery.service.strategy;

public class VisaStrategy implements BandeiraCartaoStrategy {

  @Override
  public String identificarBandeira(String numeroCartao) {
    if (numeroCartao.startsWith("4") && (numeroCartao.length() == 13
        || numeroCartao.length() == 16)) {
      return "Visa";
    }
    return null;
  }
}