package com.grapefruitade.honeypost.domain.auth.exception;

import com.grapefruitade.honeypost.global.error.CustomException;
import com.grapefruitade.honeypost.global.error.ErrorCode;


public class TokenNotValidException extends CustomException {

   public TokenNotValidException(){
      super(ErrorCode.TOKEN_NOT_VALID);
   }

}
