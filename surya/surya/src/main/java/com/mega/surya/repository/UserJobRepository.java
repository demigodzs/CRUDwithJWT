package com.mega.surya.repository;

import com.mega.surya.models.UserDetail;
import com.mega.surya.models.UserJob;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserJobRepository extends JpaRepository<UserJob, Long> {
    List<UserJob> findByUserId(Long userId);
}
