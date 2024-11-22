package br.com.aroma.aroma_delivery.dto.enums;

/**
 * PENDENTE: Pedido foi criado, mas o pagamento ainda não foi processado.
 * PAGO: Pagamento foi concluído e confirmado.
 * PROCESSANDO: Pedido está sendo separado ou preparado para envio.
 * ENVIADO: Pedido foi enviado para o cliente.
 * ENTREGUE: Pedido foi entregue ao cliente.
 * CANCELADO: Pedido foi cancelado pelo cliente ou pelo sistema.
 * DEVOLVIDO: Pedido foi devolvido pelo cliente.
 * CONCLUIDO: Pedido foi finalizado.
 */
public enum StatusPedidoEnum {
  PENDENTE, PAGO, PROCESSANDO, ENVIADO, CANCELADO, DEVOLVIDO, ENTREGUE, CONCLUIDO
}
