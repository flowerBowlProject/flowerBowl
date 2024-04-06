package com.flowerbowl.web.service.implement;

import com.flowerbowl.web.domain.EmailCertification;
import com.flowerbowl.web.domain.User;
import com.flowerbowl.web.dto.request.auth.*;
import com.flowerbowl.web.dto.response.ResponseDTO;
import com.flowerbowl.web.dto.response.auth.*;
import com.flowerbowl.web.provider.EmailProvider;
import com.flowerbowl.web.provider.JwtProvider;
import com.flowerbowl.web.repository.EmailCertificationRepository;
import com.flowerbowl.web.repository.UserRepository;
import com.flowerbowl.web.service.AuthService;
import com.flowerbowl.web.util.EmailUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImplement implements AuthService {

    private final EmailProvider emailProvider;
    private final JwtProvider jwtProvider;

    private final UserRepository userRepository;
    private final EmailCertificationRepository emailCertificationRepository;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public ResponseEntity<? super IdCheckResponseDTO> idCheck(IdCheckRequestDTO dto) {
        try {
            String userId = dto.getUser_id();
            boolean isExistId = userRepository.existsByUserId(userId);

            if (isExistId) {return IdCheckResponseDTO.duplicateId();}

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDTO.databaseError();
        }

        return IdCheckResponseDTO.success();
    }

    @Override
    public ResponseEntity<? super NicknameCheckResponseDTO> nickNameCheck(NicknameCheckRequestDTO dto) {

        try {
            String userNickname = dto.getUser_nickname();
            boolean isExistNickname = userRepository.existsByUserNickname(userNickname);

            if (isExistNickname){return NicknameCheckResponseDTO.duplicateNickname();}


        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDTO.databaseError();
        }

        return NicknameCheckResponseDTO.success();
    }

    @Override
    public ResponseEntity<? super EmailCertificationSendResponseDTO> emailCertificationSend(EmailCertificationSendRequestDTO dto) {

        try {

            String userId = dto.getUser_id();
            String userEmail = dto.getUser_email();

            boolean isExistEmail = userRepository.existsByUserEmail(userEmail);
            if (isExistEmail) {return EmailCertificationSendResponseDTO.duplicateEmail();}

            String certificationNum = EmailUtil.getRandomValue();

            boolean isSuccess = emailProvider.sendCertification(userEmail, certificationNum);
            if (!isSuccess) {return EmailCertificationSendResponseDTO.mailSendFail();}

            // 이메일 인증하고 회원가입 안한 사람에 데이터를 지우기 위해 및 이메일 인증 기능을 여러 번 사용하고 이메일 인증 시 오류 예방를 위해 로직을 추가했습니다.
            boolean isExistData = emailCertificationRepository.existsByEmail(userEmail);
            if (isExistData) {emailCertificationRepository.deleteByEmail(userEmail);}

            EmailCertification emailCertification = new EmailCertification(userId, userEmail, certificationNum);
            emailCertificationRepository.save(emailCertification);

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDTO.databaseError();
        }
        return EmailCertificationSendResponseDTO.success();
    }

    @Override
    public ResponseEntity<? super CheckCertificationResponseDTO> checkCertification(CheckCertificationRequestDTO dto) {

        try {

            String userEmail = dto.getUser_email();
            String certificationNum = dto.getCertification_num();

            EmailCertification emailCertification = emailCertificationRepository.findByEmail(userEmail);
            if (emailCertification == null) {return CheckCertificationResponseDTO.certificationFail();}

            // email이랑 인증 번호 확인
            boolean isMatch = emailCertification.getEmail().equals(userEmail) && emailCertification.getCertificationNum().equals(certificationNum);
            if (!isMatch) {return CheckCertificationResponseDTO.certificationFail();}


        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDTO.databaseError();
        }

        return CheckCertificationResponseDTO.success();
    }

    @Override
    public ResponseEntity<? super SignUpResponseDTO> signUp(SignUpRequestDTO dto) {

        try {

//            String userId = dto.getUser_id();
            String userPw = dto.getUser_password();
            String encodePw = passwordEncoder.encode(userPw);
            String userEmail = dto.getUser_email();
//            String userNickname = dto.getUser_nickname();

            // 중복된 아이디 검사
            // 중복된 닉네임 검사
            // 이메일 인증 여부 검사 이 3가지를 보내줘야 되는가?

            dto.setUser_password(encodePw);

            User user = new User(dto);
            userRepository.save(user);

            // 회원 가입이 완료 되면 이메일 인증 테이블에 있는 데이터를 지운다 왜냐하면 이제는 필요 없는 데이터
            emailCertificationRepository.deleteByEmail(userEmail);

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDTO.databaseError();
        }

        return SignUpResponseDTO.success();
    }

    @Override
    public ResponseEntity<? super SignInResponseDTO> signIn(SignInRequestDTO dto) {

        String token = null;
        Long user_no = 0L;


        try {

            String userId = dto.getUser_id();
            User user = userRepository.findByUserId(userId);
            if(user == null){return SignInResponseDTO.signInFail();}

            user_no = user.getUserNo();
            String userPw = dto.getUser_password();
            String encodePw = user.getUserPw();
            boolean isMatch = passwordEncoder.matches(userPw, encodePw);
            if (!isMatch){return SignInResponseDTO.signInFail();}

            token = jwtProvider.create(userId);

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDTO.databaseError();
        }
        return SignInResponseDTO.success(token, user_no);
    }

}
