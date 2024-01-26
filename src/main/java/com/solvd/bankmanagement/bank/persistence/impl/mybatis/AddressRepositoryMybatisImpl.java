package com.solvd.bankmanagement.bank.persistence.impl.mybatis;

import com.solvd.bankmanagement.bank.domain.Address;
import com.solvd.bankmanagement.bank.persistence.AddressRepository;
import com.solvd.bankmanagement.bank.persistence.PersistenceConfig;
import org.apache.ibatis.session.SqlSession;

public class AddressRepositoryMybatisImpl implements AddressRepository {
    @Override
    public void create(Address address) {
        try (SqlSession sqlSession = PersistenceConfig.getSessionFactory().openSession(true)){
            AddressRepository addressRepository = sqlSession.getMapper(AddressRepository.class);
            addressRepository.create(address);
        }
    }

    @Override
    public Address findById(Long id) {
        try (SqlSession sqlSession = PersistenceConfig.getSessionFactory().openSession(true)){
            AddressRepository addressRepository = sqlSession.getMapper(AddressRepository.class);
            return addressRepository.findById(id);
        }
    }
}
