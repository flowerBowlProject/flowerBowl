package com.flowerbowl.web.service.implement;

import com.flowerbowl.web.domain.User;
import com.flowerbowl.web.dto.request.user.FindUserIdRequestDto;
import com.flowerbowl.web.dto.request.user.FindUserPwRequestDto;
import com.flowerbowl.web.dto.request.user.PatchProfileRequestDto;
import com.flowerbowl.web.dto.response.ResponseDto;
import com.flowerbowl.web.dto.response.user.*;
import com.flowerbowl.web.provider.EmailProvider;
import com.flowerbowl.web.repository.CommunityRepository;
import com.flowerbowl.web.repository.LessonRepository;
import com.flowerbowl.web.repository.RecipeRepository;
import com.flowerbowl.web.repository.UserRepository;
import com.flowerbowl.web.service.UserService;
import com.flowerbowl.web.util.EmailUtil;
import com.flowerbowl.web.util.PwUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final LessonRepository lessonRepository;
    private final CommunityRepository communityRepository;
    private final RecipeRepository recipeRepository;

    private final EmailProvider emailProvider;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public ResponseEntity<? super GetUserInfoResponseDto> getUserInfo(String userId) {

        User user = null;

        try {

            user = userRepository.findByUserId(userId);
//            if (user == null) return GetUserInfoResponseDto.notExistUser();


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
//            if (user == null) PatchProfileResponseDto.noExistUser();


            String userPw = dto.getUser_password();
            String encodePw = user.getUserPw();

//            프로필 수정 시 현재 비밀번호 일치 여부 확인하는 로직 다시 추가
            if (StringUtils.hasText(dto.getUser_password())) {
                boolean isMatch = passwordEncoder.matches(userPw, encodePw);
                if (!isMatch) return PatchProfileResponseDto.invalidPw();
            }

            // oauth2.0으로 로그인 시 닉네임, 이메일에 null 값이 들어온다
            if (StringUtils.hasText(dto.getNew_nickname())) {
                boolean isExistNickname = userRepository.existsByUserNickname(dto.getNew_nickname());
                if (isExistNickname) return PatchProfileResponseDto.duplicateNickname();
            }

            if (StringUtils.hasText(dto.getNew_email())) {
                boolean isExistEmail = userRepository.existsByUserEmail(dto.getNew_email());
                if (isExistEmail) return PatchProfileResponseDto.duplicateEmail();
            }

            if (StringUtils.hasText(dto.getNew_pw())) {
                String newPw = dto.getNew_pw();
                String newEncodePw = passwordEncoder.encode(newPw);
                user.setUserPw(newEncodePw);
            }
//            BeanUtils.copyProperties(dto, user); 나중에 사용해보자

            if (StringUtils.hasText(dto.getNew_email())) user.setUserEmail(dto.getNew_email());
            if (StringUtils.hasText(dto.getNew_phone())) user.setUserPhone(dto.getNew_phone());
            if (StringUtils.hasText(dto.getUser_file_oname())) user.setUserFileOname(dto.getUser_file_oname());
            if (StringUtils.hasText(dto.getUser_file_sname())) user.setUserFileSname(dto.getUser_file_sname());


            user.setUserPwChanged(false);
            userRepository.save(user);

            // save전 인데 여기에 하는게 맞나? 오류가 있으면 catch문에 걸리니까 상관 없을 거 같다
            if (StringUtils.hasText(dto.getNew_nickname())) {
                user.setUserNickname(dto.getNew_nickname());
                lessonRepository.updateLessonWriter(userId, dto.getNew_nickname());
                recipeRepository.updateRecipeWriter(userId, dto.getNew_nickname());
                communityRepository.updateCommunityWriter(userId, dto.getNew_nickname());
            }

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return PatchProfileResponseDto.success();
    }

    @Override
    public ResponseEntity<? super PatchWdResponseDto> patchWd(String userId) {

        try {
            int updateSuccessful = userRepository.dateWd(userId);

            if (updateSuccessful == 1) {
                recipeRepository.updateRecipeWriter(userId, "(Unknown)");
                lessonRepository.updateLessonWriter(userId, "(Unknown)");
                communityRepository.updateCommunityWriter(userId, "(Unknown)");
            }

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
//            String userNickname = dto.getUser_nickname();

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
            User user = userRepository.findPwByIdAndEmail(userId, userEmail);
            if (user == null) return FindUserPwResponseDto.informationMismatch();


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
