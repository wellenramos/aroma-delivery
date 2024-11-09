package br.com.aroma.aroma_delivery.dto.enums;

/**
 * PENDENTE: O pagamento foi iniciado, mas ainda não foi concluído ou confirmado.
 * APROVADO: O pagamento foi aprovado pela instituição financeira ou pelo sistema de pagamento.
 * REPROVADO: O pagamento foi recusado por algum motivo, como fundos insuficientes ou erro de validação.
 * CANCELADO: O pagamento foi cancelado, seja pelo cliente ou pelo sistema (por exemplo, se o pedido foi cancelado).
 * CONCLUÍDO: O pagamento foi finalizado com sucesso e o valor foi recebido.
 * EM_PROCESSAMENTO: O pagamento está sendo processado e aguarda confirmação.
 * ESTORNADO: O pagamento foi revertido após um cancelamento ou devolução do pedido.
 */
public enum StatusPagamentoEnum {
  PENDENTE, APROVADO, REPROVADO, CANCELADO, CONCLUÍDO, EM_PROCESSAMENTO, ESTORNADO
}
