package ru.kamikadze_zm.zmedia.model.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.annotations.Type;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.kamikadze_zm.zmedia.model.entity.util.PostgreSqlEnumType;

@Entity
@Table(name = "users")
public class User implements Serializable, UserDetails {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "email")
    private String email;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "name")
    private String name;
    @Size(max = 60)
    @Column(name = "password")
    private String password;
    @Size(max = 100)
    @Column(name = "avatar")
    private String avatar;
    @Column(name = "enabled")
    private Boolean enabled;
    @Column(name = "role")
    @Type(type = PostgreSqlEnumType.POSTGRESQL_ENUM_TYPE)
    @Enumerated(EnumType.STRING)
    private Role role;
    @Column(name = "confirmed")
    private Boolean confirmed;
    @OneToMany(mappedBy = "author")
    private Collection<Film> films;
    @OneToMany(mappedBy = "author")
    private Collection<FilmComment> filmComments;
    @OneToMany(mappedBy = "author")
    private Collection<TvSeries> tvSeries;
    @OneToMany(mappedBy = "author")
    private Collection<TvSeriesComment> tvSeriesComments;

    public User() {
    }

    public User(String email) {
        this.email = email.toLowerCase();
    }

    public User(String email, String name) {
        this(email);
        this.name = name;
        this.enabled = true;
    }

    public User(String email, String name, String password, String avatar) {
        this(email, name);
        this.password = password;
        this.avatar = avatar;
    }

    public User(String email, String name, String avatar, Role role, Boolean confirmed) {
        this(email, name);
        this.avatar = avatar;
        this.role = role;
        this.confirmed = confirmed;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email.toLowerCase();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Boolean getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(Boolean confirmed) {
        this.confirmed = confirmed;
    }

    public boolean isConfirmed() {
        return this.confirmed != null && this.confirmed == true;
    }

    public Collection<FilmComment> getFilmComments() {
        return filmComments;
    }

    public void setFilmComments(Collection<FilmComment> filmComments) {
        this.filmComments = filmComments;
    }

    public Collection<Film> getFilms() {
        return films;
    }

    public void setFilms(Collection<Film> films) {
        this.films = films;
    }

    public Collection<TvSeries> getTvSeries() {
        return tvSeries;
    }

    public void setTvSeries(Collection<TvSeries> tvSeries) {
        this.tvSeries = tvSeries;
    }

    public Collection<TvSeriesComment> getTvSeriesComments() {
        return tvSeriesComments;
    }

    public void setTvSeriesComments(Collection<TvSeriesComment> tvSeriesComments) {
        this.tvSeriesComments = tvSeriesComments;
    }

    public boolean isAdmin() {
        return role != null && role == Role.ADMIN;
    }

    public boolean isModer() {
        return role != null && role == Role.MODER;
    }

    public boolean isAdminOrModer() {
        return role != null && (role == Role.ADMIN || role == Role.MODER);
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (role == null) {
            return Collections.emptySet();
        }
        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority(role.toString()));
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (email != null ? email.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof User)) {
            return false;
        }
        User other = (User) object;
        if ((this.email == null && other.email != null) || (this.email != null && !this.email.equals(other.email))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "User{"
                + "email=" + email
                + ", name=" + name
                + ", avatar=" + avatar
                + ", enabled=" + enabled
                + ", role=" + role
                + ", confirmed=" + confirmed + '}';
    }

    public static enum Role {
        ADMIN("Администратор"),
        MODER("Модератор"),
        UPLOADER("Загрзучик файлов");

        private final String roleName;

        private Role(String roleName) {
            this.roleName = roleName;
        }

        public String getRoleName() {
            return roleName;
        }
    }
}
