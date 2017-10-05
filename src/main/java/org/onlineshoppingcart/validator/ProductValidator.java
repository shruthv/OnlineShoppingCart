package org.onlineshoppingcart.validator;
 
import org.onlineshoppingcart.dao.ProductDAO;
import org.onlineshoppingcart.entity.Product;
import org.onlineshoppingcart.model.ProductInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
 
@Component
public class ProductValidator implements Validator {
 
    @Autowired
    private ProductDAO productDAO;
 
    @Override
    public boolean supports(Class<?> clazz) {
        return clazz == ProductInfo.class;
    }
 
    @Override
    public void validate(Object target, Errors errors) {
        ProductInfo productInfo = (ProductInfo) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "code", "NotEmpty.productForm.code");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "NotEmpty.productForm.name");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "memory", "NotEmpty.productForm.memory");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "price", "NotEmpty.productForm.price");
 
        String code = productInfo.getCode();
        if (code != null && code.length() > 0) {
            if (code.matches("\\s+")) {
                errors.rejectValue("code", "Pattern.productForm.code");
            } else if(productInfo.isNewProduct()) {
                Product product = productDAO.findProduct(code);
                if (product != null) {
                    errors.rejectValue("code", "Duplicate.productForm.code");
                }
            }
        }
    }
 
}