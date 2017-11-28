package pl.zps.azure.employee.repository;


import org.springframework.data.repository.CrudRepository;
import pl.zps.azure.employee.model.Department;

import java.util.List;

public interface DepartmentRepository extends CrudRepository<Department, Long> {

    List<Department> findAllByName(String name);
}
