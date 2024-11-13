package com.estsoft.oreumifancafe.repository.help;

import com.estsoft.oreumifancafe.domain.help.Help;
import com.estsoft.oreumifancafe.domain.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface HelpRepository extends JpaRepository<Help, Long> {
    List<Help> findByUserId(String userId);

    List<Help> findByUserIdAndHelpType(String userId, int helpType);

    Help findByHelpType(int helpType);

    Page<Help> findHelpByUserIdAndHelpTypeOrderByIdDesc(String userId, int helpType, Pageable pageable);
}
