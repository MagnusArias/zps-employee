package pl.zps.azure.employee.repository;

import org.springframework.data.repository.CrudRepository;
import pl.zps.azure.employee.model.TestMessage;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface TestDao extends CrudRepository<TestMessage, Long> {
    public TestMessage findAllById(Long id);

    public List<TestMessage> findAll();
}
