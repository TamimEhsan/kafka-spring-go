import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;

public class KafkaFilter {
    

    // create a kafka filter which will read the tenant information from somewhere in the message and sets the tenant context
    // for the current thread. This will allow the tenant context to be available to all the downstream services.
    // The filter should be able to handle the case where the tenant information is not present in the message.
    // The filter should be able to handle the case where the tenant information is not present in the message.

   
}
