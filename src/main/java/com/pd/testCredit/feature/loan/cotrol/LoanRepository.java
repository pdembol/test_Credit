package com.pd.testCredit.feature.loan.cotrol;

import com.pd.testCredit.feature.loan.entity.LoanDetails;
import com.pd.testCredit.core.data.source.BasicInMemoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@Scope("application")
public class LoanRepository implements BasicInMemoryRepository<LoanDetails> {

    List<LoanDetails> storedObjects = new ArrayList<>();

    @Override
    public Optional<LoanDetails> getOne(@NotNull UUID id) {
        int idx = findIndexById(id);
        if (-1 != idx) {
            return Optional.ofNullable(storedObjects.get(idx));
        } else {
            log.error("loan not found");
            return Optional.empty();
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
        if (-1 != idx) {
            storedObjects.set(idx, dto);
            return dto;
        } else {
            log.error("loan not found");
            return null;
        }
    }

    public void clear() {
        storedObjects.clear();
    }


    private int findIndexById(UUID id) {
        for (int i = 0; i < storedObjects.size(); i++) {
            if (storedObjects.get(i).getId().equals(id)) {
                return i;
            }
        }
        return -1;
    }
}