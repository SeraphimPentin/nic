package nicstore.dto.product;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.ZonedDateTime;

@Getter
@Builder
@RequiredArgsConstructor
public class ResponseException {

    final private String message;
    final private ZonedDateTime timestamp;
}
