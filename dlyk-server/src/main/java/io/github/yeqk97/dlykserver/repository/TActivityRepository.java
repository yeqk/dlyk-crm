package io.github.yeqk97.dlykserver.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import io.github.yeqk97.dlykserver.model.TActivity;

@Repository
public interface TActivityRepository extends JpaRepository<TActivity, Integer>,
    JpaSpecificationExecutor<TActivity>
{
    @EntityGraph(attributePaths = {"owner", "createBy", "editBy"})
    Page<TActivity> findAll(Specification<TActivity> spec, Pageable pageable);
}
