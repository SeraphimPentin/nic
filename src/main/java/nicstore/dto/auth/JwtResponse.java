package nicstore.dto.auth;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponse {

	private String token;
	private String type = "Bearer ";
	private Long id;
	private String firstName;
	private String lastName;
	private String email;
	private List<String> roles;

}
