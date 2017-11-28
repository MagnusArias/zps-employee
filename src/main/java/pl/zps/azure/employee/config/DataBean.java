package pl.zps.azure.employee.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.zps.azure.employee.model.Department;
import pl.zps.azure.employee.model.Employee;
import pl.zps.azure.employee.repository.DepartmentRepository;
import pl.zps.azure.employee.repository.EmployeeRepository;
import pl.zps.azure.employee.service.TableService;

import javax.annotation.PostConstruct;
@Component
public class DataBean {

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    DepartmentRepository departmentRepository;

    @PostConstruct
    public void init() {
        Department department = new Department("Dzial IT");
        if(departmentRepository.findAllByName(department.getName()).size() == 0) {
            department = departmentRepository.save(department);
            employeeRepository.save(new Employee(department.getId(), "Edward", "Kowalski"));
        }
    }
}
