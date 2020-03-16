package nl.molen.belgiumfries.baker.interactions;

import com.ing.baker.recipe.annotations.FiresEvent;
import com.ing.baker.recipe.annotations.RequiresIngredient;
import com.ing.baker.recipe.javadsl.Interaction;
import lombok.Value;
import nl.molen.belgiumfries.model.Snack;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ProcessOrderToCustomer implements Interaction {

    @FiresEvent(oneOf = {ProcessOrderCompleted.class})
    public ProcessOrderCompleted apply(@RequiresIngredient("friedSnack") Optional<Snack> friedSnack,
                                       @RequiresIngredient("friedFriesWithTopping") String friedFriesWithTopping) {
        return new ProcessOrderCompleted("");
    }

    @Value
    public static class ProcessOrderCompleted {
        String mealForCustomer;
    }

}
