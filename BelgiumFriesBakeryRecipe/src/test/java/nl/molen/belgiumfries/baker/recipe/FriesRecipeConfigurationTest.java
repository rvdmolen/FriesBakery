package nl.molen.belgiumfries.baker.recipe;

import com.ing.baker.compiler.RecipeCompiler;
import com.ing.baker.il.CompiledRecipe;
import com.ing.baker.recipe.javadsl.Recipe;
import lombok.extern.java.Log;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertAll;

@Log
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfiguration.class)
class FriesRecipeConfigurationTest {

    @Autowired
    Recipe myFirstRecipe;

    @Test
    @DisplayName("Setup FriesRecipe")
    void shouldSetupRecipe() {
        CompiledRecipe recipe = RecipeCompiler.compileRecipe(myFirstRecipe);
        String visualization = recipe.getRecipeVisualization();
        assertAll("recipe",
            () -> assertNotNull(visualization),
            () -> assertNotEquals("", visualization)
        );
        log.info(visualization);
    }

}
