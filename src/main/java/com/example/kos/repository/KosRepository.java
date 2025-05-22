package com.example.kos.repository;

// import java.util.List;
import java.util.Optional;

// import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.kos.models.Kos;

@Repository
public interface KosRepository extends JpaRepository<Kos, Integer> {
    Optional<Kos> findById(Long id);

    Kos deleteById(Long id);

    // List<Kos> findAll(Sort sort);

}
