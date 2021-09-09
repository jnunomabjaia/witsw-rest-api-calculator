package com.jnmabjaia.consumer;

import com.jnmabjaia.constantes.RabbitMQConstantes;
import com.jnmabjaia.dto.OperacaoDto;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class OperacaoConsumer {

    @RabbitListener(queues = RabbitMQConstantes.FILA_REST)
    private void consumir(OperacaoDto operacaoDto){
        System.out.println(operacaoDto.getA());
        System.out.println(operacaoDto.getB());
    }
}
