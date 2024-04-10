package com.flowerbowl.web.dto.response.mypage;

import com.flowerbowl.web.dto.object.PurchaserList;
import com.flowerbowl.web.dto.response.ResponseDto;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Getter
public class GetPurchasersResponseDto extends ResponseDto {

    private List<PurchaserList> purchasers;

    public GetPurchasersResponseDto(List<PurchaserList> purchasers) {
        super();
        this.purchasers = purchasers;
    }

    public static ResponseEntity<? super GetPurchasersResponseDto> success(List<PurchaserList> purchasers) {
        GetPurchasersResponseDto body = new GetPurchasersResponseDto(purchasers);
        return ResponseEntity.status(HttpStatus.OK).body(body);
    }
}
