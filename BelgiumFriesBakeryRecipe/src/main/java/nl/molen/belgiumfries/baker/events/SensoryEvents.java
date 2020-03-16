package nl.molen.belgiumfries.baker.events;

import lombok.Value;
import nl.molen.belgiumfries.model.FriesTopping;
import nl.molen.belgiumfries.model.Snack;

import java.util.Optional;

public class SensoryEvents {

    @Value
    public static class FriesRequest implements SensoryEvent {
        Optional<FriesTopping> topping;
        Optional<Snack> snack;
    }

    public interface SensoryEvent {}
}
