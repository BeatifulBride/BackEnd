package com.memory.beautifulbride.repository.dress;

import com.memory.beautifulbride.dtos.dress.DressListPageDTO;
import com.memory.beautifulbride.dtos.tryon.TryOnDTO;
import org.springframework.data.jpa.repository.Modifying;

import java.util.List;

public interface DressInfoRepositoryDsl {

    /** 모든 드레스 리스트 가져오기 */
    List<DressListPageDTO> getAllDresses();

    /** 회사 페이지 | 해당 회사 드레스 상위 5개 리스트 반환 */
    List<DressListPageDTO> getCompanyTop5Dresses(String companyName);

    /** 모든 드레스중 상위 5개 리스트 반환 */
    List<DressListPageDTO> getTop5Dresses();

    /** 이건 도대체 뭐야? */
    String findByCompanyAndDressIndex(TryOnDTO tryOnDTO);

    /**
     * 테스트 용도로 만든 메서드이니 실적용 하지 말것 <br>
     * 테스트 용도 = 좋아요 많이 받은 드레스순 테스트를 위한것
     */
    @Modifying
    void updateDressMarkCountTest(int dressInfoIndex, long count);
}
