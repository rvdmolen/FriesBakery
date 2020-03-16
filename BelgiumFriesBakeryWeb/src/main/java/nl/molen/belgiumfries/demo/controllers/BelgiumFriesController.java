package nl.molen.belgiumfries.demo.controllers;

import lombok.AllArgsConstructor;
import nl.molen.belgiumfries.demo.services.BelgiumFriesService;
import nl.molen.belgiumfries.model.BelgiumFriesResponse;
import nl.molen.belgiumfries.model.FriesTopping;
import nl.molen.belgiumfries.model.Snack;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

@RestController
@AllArgsConstructor
public class BelgiumFriesController {

    @Autowired
    private final BelgiumFriesService belgiumFriesService;

    @GetMapping("/baker")
    public @ResponseBody
    CompletableFuture<ResponseEntity<BelgiumFriesResponse>> consumeFries(@RequestParam(value = "topping", required = false) FriesTopping topping,
                                                                         @RequestParam(value = "snack", required = false) Snack snack) {
        return belgiumFriesService.getFriesWithBaker(Optional.ofNullable(topping), Optional.ofNullable(snack))
            .thenApply(ResponseEntity::ok)
            .exceptionally(handleError);
    }

    private static Function<Throwable, ResponseEntity<BelgiumFriesResponse>> handleError =
        throwable -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
}
