package nl.molen.belgiumfries.baker;

import com.ing.baker.runtime.javadsl.Baker;
import com.ing.baker.runtime.javadsl.EventInstance;
import com.ing.baker.runtime.javadsl.SensoryEventResult;
import lombok.extern.java.Log;
import nl.molen.belgiumfries.baker.events.SensoryEvents;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Log
@Service
public class BakerWrapper {

    private final Baker baker;
    private final CompletableFuture<String> friesRecipeId;

    @Autowired
    public BakerWrapper(Baker baker, CompletableFuture<String> friesRecipeId) {
        this.baker = baker;
        this.friesRecipeId = friesRecipeId;
    }

    @Async
    public CompletableFuture<SensoryEventResult> processRecipe(SensoryEvents.SensoryEvent sensoryEvent) {
        String traceId = UUID.randomUUID().toString();
        return this.friesRecipeId
            .thenCompose(recipe -> baker.bake(recipe, traceId))
            .thenCompose(ignore -> baker.fireEventAndResolveWhenCompleted(traceId, EventInstance.from(sensoryEvent)))
            .whenComplete((result, ex) -> {
                if (ex != null) printRecipe(traceId);
                printRecipe(traceId);
            });
    }

    private void printRecipe(String traceId) {
        baker.getVisualState(traceId)
            .whenComplete((recipe, ex) -> log.info(recipe));

    }
}
