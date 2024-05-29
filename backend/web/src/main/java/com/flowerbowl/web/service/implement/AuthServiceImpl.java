package com.flowerbowl.web.service.implement;

import com.flowerbowl.web.domain.EmailCertification;
import com.flowerbowl.web.domain.User;
import com.flowerbowl.web.dto.request.auth.*;
import com.flowerbowl.web.dto.response.ResponseDto;
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
public class AuthServiceImpl implements AuthService {

    private final EmailProvider emailProvider;
    private final JwtProvider jwtProvider;

    private final UserRepository userRepository;
    private final EmailCertificationRepository emailCertificationRepository;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public ResponseEntity<? super IdCheckResponseDto> idCheck(IdCheckRequestDto dto) {
        try {
            String userId = dto.getUser_id();
            boolean isExistId = userRepository.existsByUserId(userId);

            if (isExistId) return IdCheckResponseDto.duplicateId();


        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return IdCheckResponseDto.success();
    }

    @Override
    public ResponseEntity<? super NicknameCheckResponseDto> nickNameCheck(NicknameCheckRequestDto dto) {

        try {

            String userNickname = dto.getUser_nickname();
            boolean isExistNickname = userRepository.existsByUserNickname(userNickname);
            if (isExistNickname) return NicknameCheckResponseDto.duplicateNickname();


        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return NicknameCheckResponseDto.success();
    }

    @Override
    public ResponseEntity<? super EmailCertificationSendResponseDto> emailCertificationSend(EmailCertificationSendRequestDto dto) {

        try {

            String userId = dto.getUser_id();
            String userEmail = dto.getUser_email();

            boolean isExistEmail = userRepository.existsByUserEmail(userEmail);
            if (isExistEmail) return EmailCertificationSendResponseDto.duplicateEmail();


            String certificationNum = EmailUtil.getRandomValue();

            boolean isSuccess = emailProvider.sendCertification(userEmail, certificationNum);
            if (!isSuccess) return EmailCertificationSendResponseDto.mailSendFail();


            // 이메일 인증하고 회원가입 안한 사람에 데이터를 지우기 위해 및 이메일 인증 기능을 여러 번 사용하고 이메일 인증 시 오류 예방를 위해 로직을 추가했습니다.
            boolean isExistData = emailCertificationRepository.existsByEmail(userEmail);
            if (isExistData) emailCertificationRepository.deleteByEmail(userEmail);


            EmailCertification emailCertification = new EmailCertification(userId, userEmail, certificationNum);
            emailCertificationRepository.save(emailCertification);

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return EmailCertificationSendResponseDto.success();
    }

    @Override
    public ResponseEntity<? super CheckCertificationResponseDto> checkCertification(CheckCertificationRequestDto dto) {

        try {

            String userEmail = dto.getUser_email();
            String certificationNum = dto.getCertification_num();

            EmailCertification emailCertification = emailCertificationRepository.findByEmail(userEmail);
            if (emailCertification == null) {
                return CheckCertificationResponseDto.certificationFail();
            }

            // email이랑 인증 번호 확인
            boolean isMatch = emailCertification.getEmail().equals(userEmail) && emailCertification.getCertificationNum().equals(certificationNum);
            if (!isMatch) {
                return CheckCertificationResponseDto.certificationFail();
            }


        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return CheckCertificationResponseDto.success();
    }

    @Override
    public ResponseEntity<? super SignUpResponseDto> signUp(SignUpRequestDto dto) {

        try {

            String userId = dto.getUser_id();
            String userPw = dto.getUser_password();
            String encodePw = passwordEncoder.encode(userPw);
            String userEmail = dto.getUser_email();
            String userNickname = dto.getUser_nickname();
            String userPhone = dto.getUser_phone();

            boolean isMatchUserId = userRepository.existsByUserId(userId);
            log.info("userId={}", isMatchUserId);
            if (isMatchUserId) return SignUpResponseDto.duplicatedId();

            boolean isMatchNickname = userRepository.existsByUserNickname(userNickname);
            log.info("isMatchNickname={}", isMatchNickname);
            if (isMatchNickname) return SignUpResponseDto.duplicatedNickname();

            boolean isMatchPhone = userRepository.existsByUserPhone(userPhone);
            log.info("isMatchPhone={}", isMatchPhone);
            if (isMatchPhone) return SignUpResponseDto.duplicatedIdPhone();

            boolean isMatchEmail = userRepository.existsByUserEmail(userEmail);
            log.info("isMatchEmail={}", isMatchEmail);
            if (isMatchEmail) return SignUpResponseDto.duplicatedEmail();

            dto.setUser_password(encodePw);

            User user = new User(dto);
            userRepository.save(user);

            // 회원 가입이 완료 되면 이메일 인증 테이블에 있는 데이터를 지운다 왜냐하면 이제는 필요 없는 데이터
            emailCertificationRepository.deleteByEmail(userEmail);

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return SignUpResponseDto.success();
    }

    @Override
    public ResponseEntity<? super SignInResponseDto> signIn(SignInRequestDto dto) {

        String token = null;
        Long user_no = 0L;


        try {

            String userId = dto.getUser_id();
            log.info("userId={}", dto.getUser_id());
            User user = userRepository.findByUserId(userId);
            if (user == null) return SignInResponseDto.signInFail();

            user_no = user.getUserNo();
            String userPw = dto.getUser_password();
            String encodePw = user.getUserPw();
            boolean isMatch = passwordEncoder.matches(userPw, encodePw);
            if (!isMatch) return SignInResponseDto.signInFail();

            Boolean userWdStatus = user.getUserWdStatus();
            if (userWdStatus) return SignInResponseDto.withdrawalUser();

            token = jwtProvider.create(userId);

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return SignInResponseDto.success(token, user_no);
    }

}
