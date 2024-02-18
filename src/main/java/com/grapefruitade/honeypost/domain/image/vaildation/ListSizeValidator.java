package com.grapefruitade.honeypost.domain.image.vaildation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ListSizeValidator implements ConstraintValidator<IsValidListSize, List<?>> {

    private int maxSize;

    @Override
    public boolean isValid(List<?> itemList, ConstraintValidatorContext cxt) {
        if (itemList == null || itemList.size() <= maxSize) {
            return true;
        } else {
            cxt.disableDefaultConstraintViolation();
            cxt.buildConstraintViolationWithTemplate(
                            String.format("이미지는 최대 %s개 까지 업로드 가능 합니다.", maxSize))
                    .addConstraintViolation();
            return false;
        }
    }

    @Override
    public void initialize(IsValidListSize constraintAnnotation) {
        this.maxSize = constraintAnnotation.max();
    }
}
