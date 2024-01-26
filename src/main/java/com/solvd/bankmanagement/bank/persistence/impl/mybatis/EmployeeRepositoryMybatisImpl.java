package com.solvd.bankmanagement.bank.persistence.impl.mybatis;

import com.solvd.bankmanagement.bank.domain.Employee;
import com.solvd.bankmanagement.bank.persistence.EmployeeRepository;
import com.solvd.bankmanagement.bank.persistence.PersistenceConfig;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class EmployeeRepositoryMybatisImpl implements EmployeeRepository {
    @Override
    public void create(Employee employee, Long bankId) {
        try (SqlSession sqlSession = PersistenceConfig.getSessionFactory().openSession(true)){
            EmployeeRepository employeeRepository = sqlSession.getMapper(EmployeeRepository.class);
            employeeRepository.create(employee, bankId);
        }
    }

    @Override
    public void update(Employee employee) {
        try (SqlSession sqlSession = PersistenceConfig.getSessionFactory().openSession(true)){
            EmployeeRepository employeeRepository = sqlSession.getMapper(EmployeeRepository.class);
            employeeRepository.update(employee);
        }
    }

    @Override
    public Employee findById(Long id) {
        try (SqlSession sqlSession = PersistenceConfig.getSessionFactory().openSession(true)){
            EmployeeRepository employeeRepository = sqlSession.getMapper(EmployeeRepository.class);
            return employeeRepository.findById(id);
        }
    }

    @Override
    public boolean deleteById(Long id) {
        try (SqlSession sqlSession = PersistenceConfig.getSessionFactory().openSession(true)){
            EmployeeRepository employeeRepository = sqlSession.getMapper(EmployeeRepository.class);
            return employeeRepository.deleteById(id);
        }
    }

    @Override
    public List<Employee> findAllByBankId(Long bankId) {
        return null;
    }
}
