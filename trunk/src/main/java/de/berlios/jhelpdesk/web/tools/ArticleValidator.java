package de.berlios.jhelpdesk.web.tools;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import de.berlios.jhelpdesk.model.Article;

@Component
public class ArticleValidator implements Validator {
    
    public boolean supports(Class<?> clazz) {
        return Article.class.equals(clazz);
    }

    public void validate(Object target, Errors errors) {
        Article article = (Article) target;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "title", "errors.kbase.articleTitleEmpty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lead", "errors.kbase.articleLeadEmpty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "body", "errors.kbase.articleBodyEmpty");
        if (article.getCategory() == null) {
            errors.rejectValue("category", "errors.kbase.articleCategoryNull");
        }
    }
}

