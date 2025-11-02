package com.company.hr.service.impl;

import com.company.hr.entity.EmployeeSequence;
import com.company.hr.mapper.EmployeeSequenceMapper;
import com.company.hr.service.EmployeeSequenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;

@Service
public class EmployeeSequenceServiceImpl implements EmployeeSequenceService {

    @Autowired
    private EmployeeSequenceMapper employeeSequenceMapper;

    @Override
    public EmployeeSequence findByOrgAndYear(Long orgId, Integer year) {
        return employeeSequenceMapper.findByOrgAndYear(orgId, year);
    }

    @Override
    public void save(EmployeeSequence sequence) {
        employeeSequenceMapper.insert(sequence);
    }

    @Override
    public void update(EmployeeSequence sequence) {
        employeeSequenceMapper.update(sequence);
    }

    @Override
    public String generateEmployeeId(Long orgId) {
        int currentYear = LocalDate.now().getYear();
        int sequence = getNextSequence(orgId, currentYear);
        return String.format("%d%06d%02d", currentYear, orgId, sequence);
    }

    @Override
    public int getNextSequence(Long orgId, Integer year) {
        EmployeeSequence sequence = employeeSequenceMapper.findByOrgAndYear(orgId, year);
        if (sequence == null) {
            sequence = new EmployeeSequence();
            sequence.setOrgId(orgId);
            sequence.setSequenceYear(year);
            sequence.setCurrentValue(1);
            employeeSequenceMapper.insert(sequence);
            return 1;
        } else {
            employeeSequenceMapper.incrementValue(sequence.getSeqId());
            return sequence.getCurrentValue() + 1;
        }
    }
}