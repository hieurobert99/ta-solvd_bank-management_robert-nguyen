import com.solvd.bankmanagement.bank.domain.*;
import com.solvd.bankmanagement.bank.persistence.impl.jdbc.*;
import com.solvd.bankmanagement.bank.service.impl.AccountServiceImpl;
import com.solvd.bankmanagement.bank.service.impl.BankServiceImpl;
import com.solvd.bankmanagement.bank.service.impl.CustomerServiceImpl;
import com.solvd.bankmanagement.bank.service.impl.EmployeeServiceImpl;
import org.testng.annotations.Test;

import java.math.BigDecimal;

import static org.testng.AssertJUnit.*;

public class BankDaoTest {
    @Test
    void testCRUDOperations() {
        AddressRepositoryJDBCImpl addressRepository = new AddressRepositoryJDBCImpl();
        AccountRepositoryJDBCImpl accountRepository = new AccountRepositoryJDBCImpl();
        BankRepositoryJDBCImpl bankRepository = new BankRepositoryJDBCImpl();
        CustomerRepositoryJDBCImpl customerRepository = new CustomerRepositoryJDBCImpl();
        EmployeeRepositoryJDBCImpl employeeRepository = new EmployeeRepositoryJDBCImpl();

        //Test CREATE
        Address address = new Address("Kurczaki 3", "Warsaw");
        addressRepository.create(address);
        assertEquals("Kurczaki 3", addressRepository.findById(address.getId()).getStreet());

        Bank bank = new Bank("MBank", 2L);
        bankRepository.create(bank);
        assertEquals("MBank", bankRepository.findById(bank.getId()).getName());

        Employee employee = new Employee("Robert", "Nguyen", 4L);
        employeeRepository.create(employee, bank.getId());
        assertEquals(Long.valueOf(4L), employeeRepository.findById(employee.getId()).getAddressId());

        Customer customer = new Customer("Adam", "Soczew", 8L);
        customerRepository.create(customer, bank.getId());
        assertEquals(bank.getId(), customerRepository.findById(customer.getId()).getBankId());

        Account account = new Account("Premium", "PLN", new BigDecimal(1000));
        accountRepository.create(account, customer.getId());
        assertEquals("Premium", accountRepository.findById(account.getId()).getType());


        //Test READ
        assertNotNull(addressRepository.findById(address.getId()));
        assertNotNull(bankRepository.findById(bank.getId()));
        assertNotNull(employeeRepository.findById(employee.getId()));
        assertNotNull(customerRepository.findById(customer.getId()));
        assertNotNull(accountRepository.findById(account.getId()));

        //Test UPDATE
        bank.setAddressId(5L);
        bankRepository.update(bank);
        assertEquals(Long.valueOf(5L), bankRepository.findById(bank.getId()).getAddressId());

        customer.setLastName("Lennon");
        customerRepository.update(customer);
        assertEquals("Lennon", customerRepository.findById(customer.getId()).getLastName());

        account.setBalance(BigDecimal.valueOf(2550.50));
        accountRepository.update(account);
        assertEquals(BigDecimal.valueOf(2550.50).setScale(2), accountRepository.findById(account.getId()).getBalance());

        employee.setFirstName("John");
        employeeRepository.update(employee);
        assertEquals("John", employeeRepository.findById(employee.getId()).getFirstName());

        //Test DELETE
        assertTrue(accountRepository.deleteById(account.getId()));
        assertNull(accountRepository.findById(account.getId()));

        assertTrue(customerRepository.deleteById(customer.getId()));
        assertNull(customerRepository.findById(customer.getId()));

        assertTrue(employeeRepository.deleteById(employee.getId()));
        assertNull(employeeRepository.findById(employee.getId()));

        assertTrue(bankRepository.deleteById(bank.getId()));
        assertNull(bankRepository.findById(bank.getId()));

        //Test Service
        AccountServiceImpl accountService = new AccountServiceImpl(accountRepository);
        CustomerServiceImpl customerService = new CustomerServiceImpl(customerRepository, accountService);
        EmployeeServiceImpl employeeService = new EmployeeServiceImpl(employeeRepository);
        BankServiceImpl bankService = new BankServiceImpl(bankRepository, employeeService, customerService);

        bankService.create(bank);
        employeeService.create(employee, bank.getId());
        customerService.create(customer, bank.getId());
        accountService.create(account, customer.getId());

        bankService.findById(bank.getId());
        employeeService.findById(employee.getId());
        customerService.findById(customer.getId());
        accountService.findById(account.getId());

        bank.setAddressId(4L);
        bankService.update(bank);

        employee.setFirstName("Peter");
        employeeService.update(employee);

        customer.setLastName("Aston");
        customerService.update(customer);

        account.setBalance(BigDecimal.valueOf(750.75).setScale(2));
        accountService.update(account);

        bankService.deleteById(bank.getId());


    }
}
