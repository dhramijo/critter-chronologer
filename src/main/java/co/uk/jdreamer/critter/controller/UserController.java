package co.uk.jdreamer.critter.controller;

import co.uk.jdreamer.critter.dto.CustomerDTO;
import co.uk.jdreamer.critter.dto.EmployeeDTO;
import co.uk.jdreamer.critter.dto.EmployeeRequestDTO;
import co.uk.jdreamer.critter.entity.Customer;
import co.uk.jdreamer.critter.entity.Employee;
import co.uk.jdreamer.critter.entity.Pet;
import co.uk.jdreamer.critter.service.CustomerService;
import co.uk.jdreamer.critter.service.EmployeeService;
import co.uk.jdreamer.critter.service.PetService;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Users.
 *
 * Includes requests for both customers and employees. Splitting this into separate user and customer controllers
 * would be fine too, though that is not part of the required scope for this class.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    private CustomerService customerService;
    private EmployeeService employeeService;
    private PetService petService;

    public UserController(CustomerService customerService, EmployeeService employeeService, PetService petService) {
        this.customerService = customerService;
        this.employeeService = employeeService;
        this.petService = petService;
    }

    @PostMapping("/customer")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO){
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerDTO,customer);
        return getCustomerDTO(customerService.save(customer));
    }

    @GetMapping("/customer")
    public List<CustomerDTO> getAllCustomers(){
        List<Customer> customers = customerService.getAllCustomers();
        return customers.stream().map(customer -> getCustomerDTO(customer)).collect(Collectors.toList());
    }

    @GetMapping("/customer/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable long petId){
        Customer customer = petService.findOwnerByPetId(petId);
        return getCustomerDTO(customer);
    }

    @PostMapping("/employee")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDTO,employee);
        return getEmployeeDTO(employeeService.save(employee));
    }

    @PostMapping("/employee/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable long employeeId) {
        return getEmployeeDTO(employeeService.findEmployeeById(employeeId));
    }

    @PutMapping("/employee/{employeeId}")
    public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) {
        employeeService.setEmployeeAvailability(daysAvailable,employeeId);
    }

    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeDTO) {
        List<Employee> employeesForService = employeeService.findEmployeesForService(employeeDTO);
        return employeesForService.stream().map(employee -> getEmployeeDTO(employee)).collect(Collectors.toList());
    }

    private CustomerDTO getCustomerDTO(Customer customer) {
        CustomerDTO customerDTO = new CustomerDTO();
        List<Long> petIds = new ArrayList<>();
        BeanUtils.copyProperties(customer, customerDTO);

        if (customer.getPets() != null) {
            petIds = customer.getPets().stream().map(pet -> pet.getId()).collect(Collectors.toList());
        }

        customerDTO.setPetIds(petIds);
        return customerDTO;
    }

    private EmployeeDTO getEmployeeDTO(Employee employee) {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        BeanUtils.copyProperties(employee, employeeDTO);
        return employeeDTO;
    }
}
