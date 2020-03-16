package nl.molen.belgiumfries.baker;


import com.ing.baker.compiler.RecipeCompiler;
import com.ing.baker.recipe.common.Recipe;
import com.ing.baker.recipe.javadsl.Interaction;
import com.ing.baker.runtime.javadsl.Baker;
import com.ing.baker.runtime.javadsl.InteractionInstance;
import nl.molen.belgiumfries.baker.recipe.FriesRecipeConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Configuration
@Import({
    BakerConfiguration.class,
    FriesRecipeConfiguration.class
})
public class BakerWrapperConfiguration {

    @Bean
    CompletableFuture<String> friesRecipeId(Baker baker, Recipe friesRecipe, List<Interaction> interactions) {
        baker.addInteractionInstances(convertToInterActionInstance(interactions));
        return baker.addRecipe(RecipeCompiler.compileRecipe(friesRecipe));
    }

    private List<InteractionInstance> convertToInterActionInstance(List<Interaction> interactions) {
        return interactions.stream()
            .map(InteractionInstance::from)
            .collect(Collectors.toList());
    }
}
