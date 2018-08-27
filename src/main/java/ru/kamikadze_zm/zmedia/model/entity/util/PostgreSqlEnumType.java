package ru.kamikadze_zm.zmedia.model.entity.util;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.type.EnumType;

public class PostgreSqlEnumType extends EnumType {

    public static final String POSTGRESQL_ENUM_TYPE = "ru.kamikadze_zm.zmedia.model.entity.util.PostgreSqlEnumType";

    @Override
    public void nullSafeSet(PreparedStatement st, Object value, int index, SharedSessionContractImplementor session) throws HibernateException, SQLException {
        if (value == null) {
            st.setNull(index, Types.OTHER);
        } else {
            st.setObject(
                    index,
                    ((Enum) value).name(),
                    Types.OTHER
            );
        }
    }
}
