package nl.molen.belgiumfries.demo.services;

import nl.molen.belgiumfries.baker.BakerWrapper;
import nl.molen.belgiumfries.baker.events.SensoryEvents;
import nl.molen.belgiumfries.baker.interactions.ProcessOrderToCustomer;
import nl.molen.belgiumfries.model.BelgiumFriesResponse;
import nl.molen.belgiumfries.model.FriesTopping;
import nl.molen.belgiumfries.model.Snack;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
public class BelgiumFriesService {

    private final BakerWrapper bakerWrapper;
    private final List<String> happyEvents = Collections.singletonList(
        ProcessOrderToCustomer.ProcessOrderCompleted.class.getSimpleName()
    );

    public BelgiumFriesService(BakerWrapper bakerWrapper) {
        this.bakerWrapper = bakerWrapper;
    }

    public CompletableFuture<BelgiumFriesResponse> getFriesWithBaker(Optional<FriesTopping> topping, Optional<Snack> snack) {
        SensoryEvents.FriesRequest friesRequest = new SensoryEvents.FriesRequest(topping, snack);
        return bakerWrapper.processRecipe(friesRequest)
            .thenApply(result -> buildResponse(result.getEventNames()));
    }

    private BelgiumFriesResponse buildResponse(List<String> eventNames) {
        if (eventNames.stream().anyMatch(happyEvents::contains)) {
            return new BelgiumFriesResponse("OK");
        }
        return new BelgiumFriesResponse("ERROR");
    }
}
