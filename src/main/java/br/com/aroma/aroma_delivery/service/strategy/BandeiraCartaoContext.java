package br.com.aroma.aroma_delivery.service.strategy;

import java.util.ArrayList;
import java.util.List;

public class BandeiraCartaoContext {

  private final List<BandeiraCartaoStrategy> strategies = new ArrayList<>();

  public BandeiraCartaoContext() {
    strategies.add(new VisaStrategy());
    strategies.add(new MasterCardStrategy());
  }

  public String identificarBandeira(String numeroCartao) {
    numeroCartao = numeroCartao.replaceAll(" ", "");
    for (BandeiraCartaoStrategy strategy : strategies) {
      String bandeira = strategy.identificarBandeira(numeroCartao);
      if (bandeira != null) {
        return bandeira;
      }
    }
    throw new IllegalStateException("Bandeira desconhecida");
  }
}
