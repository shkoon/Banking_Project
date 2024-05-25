package ma.enset.digitalbanking.repositories;

import ma.enset.digitalbanking.dtos.OperationDTO;
import ma.enset.digitalbanking.entities.Operation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OperationRepository extends JpaRepository<Operation, String> {
    List<Operation> findByBankAccountId(String id);
    Page<Operation> findByBankAccountId(String id, Pageable pageable);
}
