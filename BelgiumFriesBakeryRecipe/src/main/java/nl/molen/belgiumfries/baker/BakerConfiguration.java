package nl.molen.belgiumfries.baker;

import akka.actor.ActorSystem;
import com.ing.baker.runtime.javadsl.Baker;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BakerConfiguration {

    @Bean(name = "baker")
    public Baker baker() {
        Config config = ConfigFactory.load();
        return Baker.akka(config, ActorSystem.create("BelgiumFries"));
    }
}
