package com.memory.beautifulbride.repository.tryon;

import com.memory.beautifulbride.entitys.dress.TryOnImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TryOnRepository extends JpaRepository<TryOnImage, Integer>, TryOnRepositoryDsl {
}
