package com.flowerbowl.web.service.implement;

import com.flowerbowl.web.domain.User;
import com.flowerbowl.web.dto.request.user.PatchProfileRequestDTO;
import com.flowerbowl.web.dto.response.ResponseDTO;
import com.flowerbowl.web.dto.response.user.GetUserInfoResponseDTO;
import com.flowerbowl.web.dto.response.user.PatchProfileResponseDTO;
import com.flowerbowl.web.repository.UserRepository;
import com.flowerbowl.web.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public ResponseEntity<? super GetUserInfoResponseDTO> getUserInfo(String userId) {

        User user = null;

        try {

            user = userRepository.findByUserId(userId);
            if(user == null){return GetUserInfoResponseDTO.notExistUser();}

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDTO.databaseError();
        }

        return GetUserInfoResponseDTO.success(user);
    }

    @Override
    public ResponseEntity<? super PatchProfileResponseDTO> putUserProfile(
            PatchProfileRequestDTO dto, String userId) {

        try {

            User user = userRepository.findByUserId(userId);
            if (user == null) {
                PatchProfileResponseDTO.noExistUser();}

            String userPw = dto.getUser_password();
            String encodePw = user.getUserPw();
            boolean isMatch = passwordEncoder.matches(userPw, encodePw);
            if(!isMatch){return PatchProfileResponseDTO.invalidPw();}

            boolean isExistNickname = userRepository.existsByUserNickname(dto.getNew_nickname());
            if (isExistNickname){return PatchProfileResponseDTO.duplicateNickname();}

            boolean isExistEmail = userRepository.existsByUserNickname(dto.getNew_email());
            if (isExistEmail){return PatchProfileResponseDTO.duplicateEmail();}

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
            return ResponseDTO.databaseError();
        }

        return PatchProfileResponseDTO.success();
    }
}
