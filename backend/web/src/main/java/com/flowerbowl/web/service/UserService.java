package com.flowerbowl.web.service;

import com.flowerbowl.web.dto.request.user.PatchProfileRequestDTO;
import com.flowerbowl.web.dto.response.user.GetUserInfoResponseDTO;
import com.flowerbowl.web.dto.response.user.PatchProfileResponseDTO;
import org.springframework.http.ResponseEntity;

public interface UserService {

    /**
     * @return 유저 정보
     */
    ResponseEntity<? super GetUserInfoResponseDTO> getUserInfo(String userId);


    /**
     * 
     * @return 유저 정보 수정
     */
    ResponseEntity<? super PatchProfileResponseDTO> putUserProfile(PatchProfileRequestDTO dto, String userId);
}
