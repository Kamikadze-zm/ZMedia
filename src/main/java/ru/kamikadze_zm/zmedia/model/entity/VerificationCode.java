package ru.kamikadze_zm.zmedia.model.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "verification_codes")
public class VerificationCode implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected VerificationCodePK verificationCodePK;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 36)
    @Column(name = "code")
    private String code;
    @Basic(optional = false)
    @NotNull
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    public VerificationCode() {
    }

    public VerificationCode(VerificationCodePK verificationCodePK) {
        this.verificationCodePK = verificationCodePK;
    }

    public VerificationCode(String email, CodeType type) {
        this(new VerificationCodePK(email, type));
    }

    public VerificationCode(VerificationCodePK verificationCodePK, String code, Date createdAt) {
        this.verificationCodePK = verificationCodePK;
        this.code = code;
        this.createdAt = createdAt;
    }

    public VerificationCode(String email, CodeType type, String code, Date createdAt) {
        this(new VerificationCodePK(email, type), code, createdAt);
    }

    public VerificationCodePK getVerificationCodePK() {
        return verificationCodePK;
    }

    public void setVerificationCodePK(VerificationCodePK verificationCodePK) {
        this.verificationCodePK = verificationCodePK;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getEmail() {
        return verificationCodePK.getEmail();
    }

    public CodeType getType() {
        return verificationCodePK.getType();
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (verificationCodePK != null ? verificationCodePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof VerificationCode)) {
            return false;
        }
        VerificationCode other = (VerificationCode) object;
        if ((this.verificationCodePK == null && other.verificationCodePK != null) || (this.verificationCodePK != null && !this.verificationCodePK.equals(other.verificationCodePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "VerificationCode{" + "verificationCodePK=" + verificationCodePK + ", code=" + code + ", createdAt=" + createdAt + '}';
    }

    public static enum CodeType {
        EMAIL, PASSWORD;
    }
}
