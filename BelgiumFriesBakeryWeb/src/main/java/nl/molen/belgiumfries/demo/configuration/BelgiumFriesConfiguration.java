package nl.molen.belgiumfries.demo.configuration;

import nl.molen.belgiumfries.baker.BakerConfiguration;
import nl.molen.belgiumfries.baker.BakerWrapper;
import nl.molen.belgiumfries.baker.BakerWrapperConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Import({
    BakerConfiguration.class,
    BakerWrapperConfiguration.class,
    BakerWrapper.class
})
@ComponentScan({"nl.molen.belgiumfries.baker.interactions"})
@Configuration
public class BelgiumFriesConfiguration {


}
