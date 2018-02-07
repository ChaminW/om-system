package com.sysco.app.validation;

import com.sysco.app.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class ItemsValidator implements ConstraintValidator<ItemsConstraint, List<String>> {

    @Autowired
    ItemRepository itemRepository;

    @Override
    public void initialize(ItemsConstraint constraintAnnotation) {

    }

    @Override
    public boolean isValid(List<String> itemIds, ConstraintValidatorContext constraintValidatorContext) {
        for(String itemId: itemIds) {
            if(itemRepository.findItemById(itemId) == null) {
                return false;
            }
        }
        return true;
    }
}
