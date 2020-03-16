package nl.molen.belgiumfries.baker.interactions;

import com.ing.baker.recipe.annotations.FiresEvent;
import com.ing.baker.recipe.annotations.RequiresIngredient;
import com.ing.baker.recipe.javadsl.Interaction;
import lombok.Value;
import nl.molen.belgiumfries.model.FriesTopping;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AddTopping implements Interaction {

    @FiresEvent(oneOf = {ToppingAdded.class})
    public ToppingAdded apply(@RequiresIngredient("topping") Optional<FriesTopping> topping,
                              @RequiresIngredient("friedFries") String friedFries) {
        return new ToppingAdded("");
    }

    @Value
    public static class ToppingAdded {
        String friedFriesWithTopping;
    }
}
