package com.solvd.bankmanagement.bank.persistence.impl.mybatis;

import com.solvd.bankmanagement.bank.domain.Customer;
import com.solvd.bankmanagement.bank.persistence.CustomerRepository;
import com.solvd.bankmanagement.bank.persistence.PersistenceConfig;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class CustomerRepositoryMybatisImpl implements CustomerRepository {
    @Override
    public void create(Customer customer, Long bankId) {
        try (SqlSession sqlSession = PersistenceConfig.getSessionFactory().openSession(true)){
            CustomerRepository customerRepository = sqlSession.getMapper(CustomerRepository.class);
            customerRepository.create(customer, bankId);
        }
    }

    @Override
    public void update(Customer customer) {
        try (SqlSession sqlSession = PersistenceConfig.getSessionFactory().openSession(true)){
            CustomerRepository customerRepository = sqlSession.getMapper(CustomerRepository.class);
            customerRepository.update(customer);
        }
    }

    @Override
    public Customer findById(Long id) {
        try (SqlSession sqlSession = PersistenceConfig.getSessionFactory().openSession(true)){
            CustomerRepository customerRepository = sqlSession.getMapper(CustomerRepository.class);
            return customerRepository.findById(id);
        }
    }

    @Override
    public boolean deleteById(Long id) {
        try (SqlSession sqlSession = PersistenceConfig.getSessionFactory().openSession(true)){
            CustomerRepository customerRepository = sqlSession.getMapper(CustomerRepository.class);
            return customerRepository.deleteById(id);
        }
    }

    @Override
    public List<Customer> findAllByBankId(Long bankId) {
        return null;
    }
}
