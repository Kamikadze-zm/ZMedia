package ru.kamikadze_zm.zmedia.jwtauth;

public class TokenProperties {

    private long accessTokenExpirationTime;
    private long refreshTokenExpirationTime;
    private String issuer;
    private String secretKey;

    public TokenProperties() {
    }

    public TokenProperties(long accessTokenExpirationTime, long refreshTokenExpirationTime, String issuer, String secretKey) {
        this.accessTokenExpirationTime = accessTokenExpirationTime;
        this.refreshTokenExpirationTime = refreshTokenExpirationTime;
        this.issuer = issuer;
        this.secretKey = secretKey;
    }

    public long getAccessTokenExpirationTime() {
        return accessTokenExpirationTime;
    }

    public void setAccessTokenExpirationTime(long accessTokenExpirationTime) {
        this.accessTokenExpirationTime = accessTokenExpirationTime;
    }

    public long getRefreshTokenExpirationTime() {
        return refreshTokenExpirationTime;
    }

    public void setRefreshTokenExpirationTime(long refreshTokenExpirationTime) {
        this.refreshTokenExpirationTime = refreshTokenExpirationTime;
    }

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }
}
