package ru.kamikadze_zm.zmedia.repository;

import java.io.Serializable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface Repository<E, Id extends Serializable> extends JpaRepository<E, Id> {

}
