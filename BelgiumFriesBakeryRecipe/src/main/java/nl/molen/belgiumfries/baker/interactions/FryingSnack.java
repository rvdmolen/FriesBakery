package nl.molen.belgiumfries.baker.interactions;

import com.ing.baker.recipe.annotations.FiresEvent;
import com.ing.baker.recipe.annotations.RequiresIngredient;
import com.ing.baker.recipe.javadsl.Interaction;
import lombok.Value;
import nl.molen.belgiumfries.model.Snack;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class FryingSnack implements Interaction {

    @FiresEvent(oneOf = {SnackFried.class})
    public SnackFried apply(@RequiresIngredient("snack") Optional<Snack> snack) {
        return new SnackFried(snack);
    }

    @Value
    public static class SnackFried {
        Optional<Snack> friedSnack;
    }

}
