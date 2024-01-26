package com.solvd.bankmanagement.bank.persistence.impl.mybatis;

import com.solvd.bankmanagement.bank.domain.Account;
import com.solvd.bankmanagement.bank.persistence.AccountRepository;
import com.solvd.bankmanagement.bank.persistence.PersistenceConfig;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class AccountRepositoryMybatisImpl implements AccountRepository {
    @Override
    public void create(Account account, Long customerId) {
        try (SqlSession sqlSession = PersistenceConfig.getSessionFactory().openSession(true)){
            AccountRepository accountRepository = sqlSession.getMapper(AccountRepository.class);
            accountRepository.create(account, customerId);
        }
    }

    @Override
    public void update(Account account) {
        try (SqlSession sqlSession = PersistenceConfig.getSessionFactory().openSession(true)){
            AccountRepository accountRepository = sqlSession.getMapper(AccountRepository.class);
            accountRepository.update(account);
        }
    }

    @Override
    public Account findById(Long id) {
        try (SqlSession sqlSession = PersistenceConfig.getSessionFactory().openSession(true)){
            AccountRepository accountRepository = sqlSession.getMapper(AccountRepository.class);
            return accountRepository.findById(id);
        }
    }

    @Override
    public boolean deleteById(Long id) {
        try (SqlSession sqlSession = PersistenceConfig.getSessionFactory().openSession(true)){
            AccountRepository accountRepository = sqlSession.getMapper(AccountRepository.class);
            return accountRepository.deleteById(id);
        }
    }

    @Override
    public List<Account> findAllByCustomerId(Long customerId) {
        return null;
    }
}
