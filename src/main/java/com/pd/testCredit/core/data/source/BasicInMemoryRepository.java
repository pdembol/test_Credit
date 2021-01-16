package com.pd.testCredit.core.data.source;

import org.jetbrains.annotations.NotNull;
import java.util.Optional;
import java.util.UUID;

/**
 * Interface for local repo ( not using db in this project )
 */
public interface BasicInMemoryRepository<DTO> {

    Optional<DTO> getOne(@NotNull UUID id);

    DTO insert(@NotNull DTO dto);

    DTO update(@NotNull DTO dto);

}
