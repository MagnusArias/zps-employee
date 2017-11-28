package pl.zps.azure.employee.repository;

import org.springframework.data.repository.CrudRepository;
import pl.zps.azure.employee.model.Employee;
import pl.zps.azure.employee.model.TestMessage;

public interface EmployeeRepository extends CrudRepository<Employee, Long> {
}
