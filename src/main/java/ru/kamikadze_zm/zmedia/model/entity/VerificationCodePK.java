package ru.kamikadze_zm.zmedia.model.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.annotations.Type;
import ru.kamikadze_zm.zmedia.model.entity.VerificationCode.CodeType;
import ru.kamikadze_zm.zmedia.model.entity.util.PostgreSqlEnumType;

@Embeddable
public class VerificationCodePK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "email")
    private String email;
    @Basic(optional = false)
    @Column(name = "type")
    @Type(type = PostgreSqlEnumType.POSTGRESQL_ENUM_TYPE)
    @Enumerated(EnumType.STRING)
    private CodeType type;

    public VerificationCodePK() {
    }

    public VerificationCodePK(String email, CodeType type) {
        this.email = email;
        this.type = type;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public CodeType getType() {
        return type;
    }

    public void setType(CodeType type) {
        this.type = type;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (email != null ? email.hashCode() : 0);
        hash += (type != null ? type.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof VerificationCodePK)) {
            return false;
        }
        VerificationCodePK other = (VerificationCodePK) object;
        if ((this.email == null && other.email != null) || (this.email != null && !this.email.equals(other.email))) {
            return false;
        }
        if ((this.type == null && other.type != null) || (this.type != null && !this.type.equals(other.type))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "VerificationCodePK{" + "email=" + email + ", type=" + type + '}';
    }
}
