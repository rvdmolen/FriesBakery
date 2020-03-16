package nl.molen.belgiumfries.baker.interactions;

import com.ing.baker.recipe.annotations.FiresEvent;
import com.ing.baker.recipe.annotations.RequiresIngredient;
import com.ing.baker.recipe.javadsl.Interaction;
import lombok.Value;
import org.springframework.stereotype.Component;

@Component
public class FryingFries implements Interaction {

    @FiresEvent(oneOf = {FriesFried.class})
    public FriesFried apply(@RequiresIngredient("slicedPotato") String slicedPotato) {
        return new FriesFried("Fries");
    }

    @Value
    public static class FriesFried {
        String friedFries;
    }

}
