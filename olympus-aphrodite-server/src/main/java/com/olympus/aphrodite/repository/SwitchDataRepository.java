package com.olympus.aphrodite.repository;

import com.olympus.aphrodite.data.SwitchFieldInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author eddie.lys
 * @since 2023/1/12
 */
@Repository
public interface SwitchDataRepository extends JpaRepository<SwitchFieldInfo, Integer> {
}
