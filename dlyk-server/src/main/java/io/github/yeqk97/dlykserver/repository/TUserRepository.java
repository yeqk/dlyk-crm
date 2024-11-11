package io.github.yeqk97.dlykserver.repository;

import io.github.yeqk97.dlykserver.model.TUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TUserRepository extends JpaRepository<TUser, Integer>, JpaSpecificationExecutor<TUser> {
    @Query("SELECT u FROM TUser u JOIN FETCH u.roles LEFT JOIN FETCH u.createBy LEFT JOIN FETCH u.editBy WHERE u.loginAct = :loginAct")
    TUser findByLoginAct(final String loginAct);

    @Query("""
           SELECT u FROM TUser u
           LEFT JOIN FETCH u.createBy
           LEFT JOIN FETCH u.editBy
           WHERE u.id = :id
           """)
    Optional<TUser> findUserDetailById(Integer id);
}