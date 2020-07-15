package com.hello.example.service;

import com.hello.example.dao.EmployeeDAO;
import com.hello.example.model.EmployeeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class EmployeeManagerImp implements EmployeeManager{

    @Autowired
    EmployeeDAO dao;

    @Override
    public List<EmployeeVO> getAllEmployees() {
        return dao.getAllEmployees();
    }
}
