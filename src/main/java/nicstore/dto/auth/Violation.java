package nicstore.dto.auth;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.ZonedDateTime;

@Getter
@RequiredArgsConstructor
public class Violation {

    private final String fieldName;
    private final String message;
    private final ZonedDateTime timestamp;

}