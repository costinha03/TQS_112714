package tqs.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tqs.backend.entity.Menu;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {
}
