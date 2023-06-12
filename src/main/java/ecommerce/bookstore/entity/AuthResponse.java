package ecommerce.bookstore.entity;

public class AuthResponse {

    private Long userId;
    private String accessToken;

    private String refreshToken;

    public AuthResponse() {
    }

    public AuthResponse(String accessToken, Long userId) {
        this.accessToken = accessToken;
        this.userId = userId;
    }

    public Long getUsername() {
        return userId;
    }

    public void setUsername(Long userId) {
        this.userId = userId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
