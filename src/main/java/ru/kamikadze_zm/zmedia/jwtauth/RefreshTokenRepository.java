package ru.kamikadze_zm.zmedia.jwtauth;

import javax.sql.DataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

public class RefreshTokenRepository extends JdbcDaoSupport {

    private static final Logger LOG = LogManager.getLogger(RefreshTokenRepository.class);

    private static final String SQL_SELECT_TOKEN_BY_ID = "select token from refresh_tokens where token_id = ?";
    private static final String SQL_INSERT_TOKEN = "insert into refresh_tokens (token_id, user_email, token) values(?,?,?)";
    private static final String SQL_DELETE_TOKEN_BY_ID_AND_EMAIL = "delete from refresh_tokens where token_id = ? AND user_email = ?";
    private static final String SQL_DELETE_USER_TOKENS = "delete from refresh_tokens where user_email = ?";

    public RefreshTokenRepository(DataSource dataSource) {
        super.setDataSource(dataSource);
    }

    public void saveToken(String tokenId, String userEmail, String token) {
        getJdbcTemplate().update(SQL_INSERT_TOKEN, tokenId, userEmail, token);
    }

    public String getToken(String tokenId) {
        try {
            return getJdbcTemplate().queryForObject(SQL_SELECT_TOKEN_BY_ID, String.class, tokenId);
        } catch (DataAccessException e) {
            LOG.error("Failed to load refresh token for id: " + tokenId, e);
        }
        return null;
    }

    public void deleteTokenByIdAndEmail(String tokenId, String userEmail) {
        getJdbcTemplate().update(SQL_DELETE_TOKEN_BY_ID_AND_EMAIL, tokenId, userEmail);
    }

    public void deleteUserTokens(String userEmail) {
        getJdbcTemplate().update(SQL_DELETE_USER_TOKENS, userEmail);
    }
}
