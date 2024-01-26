package com.solvd.bankmanagement.bank.persistence.impl.mybatis;

import com.solvd.bankmanagement.bank.domain.Bank;
import com.solvd.bankmanagement.bank.persistence.BankRepository;
import com.solvd.bankmanagement.bank.persistence.PersistenceConfig;
import org.apache.ibatis.session.SqlSession;

public class BankRepositoryMybatisImpl implements BankRepository {
    @Override
    public void create(Bank bank) {
        try (SqlSession sqlSession = PersistenceConfig.getSessionFactory().openSession(true)){
            BankRepository bankRepository = sqlSession.getMapper(BankRepository.class);
            bankRepository.create(bank);
        }
    }

    @Override
    public void update(Bank bank) {
        try (SqlSession sqlSession = PersistenceConfig.getSessionFactory().openSession(true)){
            BankRepository bankRepository = sqlSession.getMapper(BankRepository.class);
            bankRepository.update(bank);
        }
    }

    @Override
    public Bank findById(Long id) {
        try (SqlSession sqlSession = PersistenceConfig.getSessionFactory().openSession(true)){
            BankRepository bankRepository = sqlSession.getMapper(BankRepository.class);
            return bankRepository.findById(id);
        }
    }

    @Override
    public boolean deleteById(Long id) {
        try (SqlSession sqlSession = PersistenceConfig.getSessionFactory().openSession(true)){
            BankRepository bankRepository = sqlSession.getMapper(BankRepository.class);
            return bankRepository.deleteById(id);
        }
    }
}
