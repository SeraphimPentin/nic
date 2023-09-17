package nicstore.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@RequiredArgsConstructor
public enum Role implements GrantedAuthority {
	ROLE_ADMIN("ROLE_ADMIN"),
	ROLE_USER("ROLE_USER");

	private String role;

	@Override
	public String getAuthority() {
		return role;
	}

	Role(String role) {
		this.role = role;
	}

	public String getRole() {
		return role;
	}
}