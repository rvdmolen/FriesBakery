package nl.molen.belgiumfries.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Value;

@Value
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class BelgiumFriesResponse {
    String result;
}
