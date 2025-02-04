package io.github.yeqk97.dlykserver.repository.specification;

import io.github.yeqk97.dlykserver.model.TActivity;
import io.github.yeqk97.dlykserver.model.TUser;
import jakarta.persistence.criteria.Join;

import org.springframework.data.jpa.domain.Specification;

public class UserSpecification {
    private UserSpecification() {
    }

    public static Specification<TUser> filterByScope(TUser currentUser) {
        return (root, query, criteriaBuilder) -> {
            if (currentUser.isAdmin()) {
                return criteriaBuilder.conjunction(); // No filtering for admins, return all.
            }
            return criteriaBuilder.equal(root.get("id"), currentUser.getId());
        };
    }

    public static Specification<TActivity> filterActivityByScope(TUser currentUser) {
        return (root, query, criteriaBuilder) -> {
            if (currentUser.isAdmin()) {
                return criteriaBuilder.conjunction(); // No filtering for admins, return all.
            }
            Join<TActivity, TUser> ownerJoin = root.join("owner");
            return criteriaBuilder.equal(ownerJoin.get("id"), currentUser.getId());
        };
    }
}
