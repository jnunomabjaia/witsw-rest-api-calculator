package com.jnmabjaia.conections;

import com.jnmabjaia.constantes.RabbitMQConstantes;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.stereotype.Component;
import org.springframework.amqp.core.Queue;

import javax.annotation.PostConstruct;

@Component //Significa que esta classe sera gerenciada pelo proprio spring
public class RabbitMQConection {
    private static final String NOME_EXCHANGE = "amq.direct";
    private AmqpAdmin amqpAdmin;

    public RabbitMQConection(AmqpAdmin amqpAdmin){
        this.amqpAdmin = amqpAdmin;
    }

    //Criando a fila
    private Queue fila(String nomeFila){
        return new Queue(nomeFila, true, false, false);
    }

    //Criando a Exchange
    private DirectExchange trocaDirecta(){
        return new DirectExchange(NOME_EXCHANGE);
    }

    //Criando o relacionamento entre fila e a exchange
    private Binding relacionamento(Queue fila, DirectExchange troca){
        return new Binding(fila.getName(), Binding.DestinationType.QUEUE, troca.getName(), fila.getName(), null);
    }

    //Caso haja outro consumidor no futuro, sera adicionada a fila neste metodo e o seu nome la no pacote de constantes
    @PostConstruct //Assim que a app levantar, o metodo e chamado criando assim a fila
    private void adiciona(){
        Queue filaRest = this.fila(RabbitMQConstantes.FILA_REST);

        DirectExchange trocaDirecta = this.trocaDirecta();

        Binding ligacaoRest = this.relacionamento(filaRest, trocaDirecta);

        //Criacao da(s) fila(s)
        this.amqpAdmin.declareQueue(filaRest);

        //Criando a Exchange
        this.amqpAdmin.declareExchange(trocaDirecta);

        //Criando o relacionamento
        this.amqpAdmin.declareBinding(ligacaoRest);
    }
}
