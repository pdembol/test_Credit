package com.pd.testCredit.features.loan;

import com.pd.testCredit.data.model.LoanDetails;
import com.pd.testCredit.data.source.BasicInMemoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@Scope("application")
public class LoanRepository implements BasicInMemoryRepository<LoanDetails> {

    List<LoanDetails> storedObjects = new ArrayList<>();

    @Override
    public LoanDetails getOne(@NotNull UUID id) {
        int idx = findIndexById(id);
        if(-1 != idx){
            return storedObjects.get(idx);
        } else {
            log.error("loan not found");
            return null;
        }
    }

    @Override
    public LoanDetails insert(@NotNull LoanDetails dto) {
        storedObjects.add(dto);
        return dto;
    }

    @Override
    public LoanDetails update(@NotNull LoanDetails dto) {
        int idx = findIndexById(dto.getId());
        if(-1 != idx){
            storedObjects.set(idx,dto);
            return dto;
        } else {
            log.error("loan not found");
            return null;
        }
    }


    private int findIndexById(UUID id) {
        for (int i = 0; i < storedObjects.size(); i++) {
            if(storedObjects.get(i).getId().equals(id)){
                return i;
            }
        }
        return -1;
    }
}