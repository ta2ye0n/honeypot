package com.grapefruitade.honeypost.global.security.exception;

import com.grapefruitade.honeypost.global.error.CustomException;
import com.grapefruitade.honeypost.global.error.ErrorCode;


public class TokenNotValidException extends CustomException {

   public TokenNotValidException(){
      super(ErrorCode.TOKEN_NOT_VALID);
   }

}
