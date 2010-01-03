/*
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, version 3 of the License.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 *
 * Copyright: (C) 2006 jHelpdesk Developers Team
 */
package de.berlios.jhelpdesk.web.tools;

import java.beans.PropertyEditorSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.berlios.jhelpdesk.dao.ArticleCategoryDAO;
import de.berlios.jhelpdesk.model.ArticleCategory;

/**
 *
 * @author jjhop
 */
@Component
public class ArticleCategoryEditor extends PropertyEditorSupport {

    @Autowired
    private ArticleCategoryDAO articleCategoryDAO;

    @Override
    public String getAsText() {
        Object value = getValue();
        if (value != null) {
            return String.valueOf(((ArticleCategory)value).getArticleCategoryId());
        } else {
            return null;
        }
    }

    @Override
    public void setAsText(String text) {
        Long articleCategoryId = Long.valueOf(text);
        ArticleCategory articleCategory = articleCategoryDAO.getById(articleCategoryId);
        setValue(articleCategory);
    }
}
