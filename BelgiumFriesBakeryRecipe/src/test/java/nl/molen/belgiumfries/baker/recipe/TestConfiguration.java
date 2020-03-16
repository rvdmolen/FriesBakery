package nl.molen.belgiumfries.baker.recipe;

import com.ing.baker.recipe.common.Recipe;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({FriesRecipeConfiguration.class})
public class TestConfiguration {

    @Bean
    String magDatRecipeId(Recipe myFirstRecipe) {
        return "test";
    }
}
