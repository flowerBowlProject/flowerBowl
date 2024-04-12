package com.flowerbowl.web.service.implement;

import com.flowerbowl.web.domain.User;
import com.flowerbowl.web.dto.request.user.FindUserIdRequestDto;
import com.flowerbowl.web.dto.request.user.FindUserPwRequestDto;
import com.flowerbowl.web.dto.request.user.PatchProfileRequestDto;
import com.flowerbowl.web.dto.response.ResponseDto;
import com.flowerbowl.web.dto.response.user.*;
import com.flowerbowl.web.provider.EmailProvider;
import com.flowerbowl.web.repository.UserRepository;
import com.flowerbowl.web.service.UserService;
import com.flowerbowl.web.util.EmailUtil;
import com.flowerbowl.web.util.PwUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final EmailProvider emailProvider;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public ResponseEntity<? super GetUserInfoResponseDto> getUserInfo(String userId) {

        User user = null;

        try {

            user = userRepository.findByUserId(userId);
            if (user == null) {
                return GetUserInfoResponseDto.notExistUser();
            }

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return GetUserInfoResponseDto.success(user);
    }

    @Override
    public ResponseEntity<? super PatchProfileResponseDto> patchUserProfile(
            PatchProfileRequestDto dto, String userId) {

        try {

            User user = userRepository.findByUserId(userId);
            if (user == null) PatchProfileResponseDto.noExistUser();


            String userPw = dto.getUser_password();
            String encodePw = user.getUserPw();
            boolean isMatch = passwordEncoder.matches(userPw, encodePw);
            if (!isMatch) return PatchProfileResponseDto.invalidPw();

            boolean isExistNickname = userRepository.existsByUserNickname(dto.getNew_nickname());
            if (isExistNickname) return PatchProfileResponseDto.duplicateNickname();

            boolean isExistEmail = userRepository.existsByUserNickname(dto.getNew_email());
            if (isExistEmail) return PatchProfileResponseDto.duplicateEmail();

            String newPw = dto.getNew_pw();
            String newEncodePw = passwordEncoder.encode(newPw);

            user.setUserEmail(dto.getNew_email());
            user.setUserNickname(dto.getNew_nickname());
            user.setUserPw(newEncodePw);
            user.setUserPhone(dto.getNew_phone());
            user.setUserFileOname(dto.getUser_file_oanme());
            user.setUserFileSname(dto.getUser_file_sname());
            userRepository.save(user);

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return PatchProfileResponseDto.success();
    }

    @Override
    public ResponseEntity<? super PatchWdResponseDto> patchWd(String userId) {

        try {
            userRepository.dateWd(userId);

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return PatchWdResponseDto.success();
    }

    @Override
    public ResponseEntity<? super FindUserIdResponseDto> findId(FindUserIdRequestDto dto) {

        String userId = null;

        try {
            String userEmail = dto.getUser_email();
            String userNickname = dto.getUser_nickname();

            User byUserEmail = userRepository.findByUserEmail(userEmail);
            if (byUserEmail == null) return FindUserIdResponseDto.findIdFail();

            userId = byUserEmail.getUserId();



        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return FindUserIdResponseDto.success(userId);
    }

    @Override
    public ResponseEntity<? super FindUserPwResponseDto> findPw(FindUserPwRequestDto dto) {

        try {

            String userEmail = dto.getUser_email();
            String userId = dto.getUser_id();
            User originPw = userRepository.findPwByIdAndEmail(userId, userEmail);
            if (originPw == null) return FindUserPwResponseDto.informationMismatch();

            String randomPw = PwUtil.getRandomPassword();
            emailProvider.sendCertification(userEmail, randomPw);
            String encodePw = passwordEncoder.encode(randomPw);
            userRepository.updatePw(encodePw, userEmail);

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return FindUserPwResponseDto.success();
    }

}
