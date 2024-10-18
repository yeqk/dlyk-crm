package io.github.yeqk97.dlykserver.repository;

import io.github.yeqk97.dlykserver.model.TUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TUserRepository extends JpaRepository<TUser, Integer> {
    TUser findByLoginAct(final String loginAct);
}