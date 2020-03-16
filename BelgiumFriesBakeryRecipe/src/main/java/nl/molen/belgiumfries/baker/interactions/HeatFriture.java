package nl.molen.belgiumfries.baker.interactions;

import com.ing.baker.recipe.annotations.FiresEvent;
import com.ing.baker.recipe.javadsl.Interaction;
import lombok.Value;
import org.springframework.stereotype.Component;

@Component
public class HeatFriture implements Interaction {

    @FiresEvent(oneOf = {FritureHeated.class})
    public FritureHeated apply() {
        return new FritureHeated();
    }

    @Value
    public static class FritureHeated {
    }

}
