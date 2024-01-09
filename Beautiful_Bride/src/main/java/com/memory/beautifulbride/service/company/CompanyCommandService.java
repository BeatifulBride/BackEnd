package com.memory.beautifulbride.service.company;


import com.memory.beautifulbride.dtos.company.CompanyInfoModifyDTO;
import com.memory.beautifulbride.entitys.logindata.LoginData;
import com.memory.beautifulbride.repository.company.CompanyRepository;
import com.memory.beautifulbride.repository.logindata.LoginDataRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Log4j2
public class CompanyCommandService {

    private final CompanyRepository companyRepository;
    private final LoginDataRepository loginDataRepository;


    public ResponseEntity<String> modifyCompanyInfo(UserDetails userDetails, CompanyInfoModifyDTO dto) {
        try {
            loginDataRepository.findByLoginId(userDetails.getUsername())
                    .filter(LoginData::isCompanyMember)
                    .orElseThrow(RuntimeException::new);
        } catch (RuntimeException e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("업체 회원이 아닙니다.");
        }

        try {
            companyRepository.modifyCompanyInfo(userDetails.getUsername(), dto.getCompanyName(), dto.getCompanyAddress(), dto.getCompanyPhone());
            return ResponseEntity.ok("업체 정보를 수정 하는데 성공 하였습니다.");
        } catch (DataAccessException e) {
            log.error("업체 정보를 수정하는데 실패했습니다. {}", e.getMessage());
            return ResponseEntity.unprocessableEntity().body("업체 정보를 수정하는데 실패했습니다.");
        }
    }
}
