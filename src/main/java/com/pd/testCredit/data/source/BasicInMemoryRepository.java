package com.pd.testCredit.data.source;

import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public interface BasicInMemoryRepository<DTO> {

    DTO getOne(@NotNull UUID id);

    DTO insert(@NotNull DTO dto);

    DTO update(@NotNull DTO dto);

}
