package com.estsoft.oreumifancafe.repository.help;

import com.estsoft.oreumifancafe.domain.help.Help;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface HelpRepository extends JpaRepository<Help, Long> {
    List<Help> findByUserId(String userId);

    List<Help> findByUserIdAndHelpType(String userId, int helpType);
}
