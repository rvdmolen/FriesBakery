package nl.molen.belgiumfries.baker.recipe;

import com.ing.baker.recipe.javadsl.Recipe;
import nl.molen.belgiumfries.baker.events.SensoryEvents;
import nl.molen.belgiumfries.baker.interactions.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.ing.baker.recipe.javadsl.InteractionDescriptor.of;

@Configuration
public class FriesRecipeConfiguration {

    @Bean
    public Recipe friesRecipe() {
        return new Recipe("friesRecipe")

            .withInteractions(
                of(SlicePotato.class)
                    .withRequiredEvent(SensoryEvents.FriesRequest.class),
                of(HeatFriture.class)
                    .withRequiredEvent(SensoryEvents.FriesRequest.class),
                of(FryingFries.class)
                    .withRequiredEvent(HeatFriture.FritureHeated.class),
                of(FryingSnack.class)
                    .withRequiredEvent(HeatFriture.FritureHeated.class),
                of(AddTopping.class),
                of(ProcessOrderToCustomer.class)
            )

            .withSensoryEvent(SensoryEvents.FriesRequest.class);

    }
}
