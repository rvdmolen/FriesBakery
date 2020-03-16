package nl.molen.belgiumfries.baker.interactions;

import com.ing.baker.recipe.annotations.FiresEvent;
import com.ing.baker.recipe.javadsl.Interaction;
import lombok.Value;
import org.springframework.stereotype.Component;

@Component
public class SlicePotato implements Interaction {

    @FiresEvent(oneOf = {PotatoSliced.class})
    public PotatoSliced apply() {
        return new PotatoSliced("slicePotato");
    }

    @Value
    public static class PotatoSliced {
        String slicedPotato;
    }

}
