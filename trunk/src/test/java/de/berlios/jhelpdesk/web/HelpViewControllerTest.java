package de.berlios.jhelpdesk.web;

import java.lang.reflect.Field;
import java.util.Collections;

import org.easymock.EasyMock;
import org.junit.Test;
import static org.junit.Assert.*;

import org.springframework.ui.ModelMap;

import de.berlios.jhelpdesk.dao.ArticleCategoryDAO;
import de.berlios.jhelpdesk.dao.ArticleDAO;
import de.berlios.jhelpdesk.model.Article;
import de.berlios.jhelpdesk.model.ArticleCategory;

/**
 *
 * @author jjhop
 */
public class HelpViewControllerTest {

    /**
     * Test of indexView method, of class HelpViewController.
     */
    @Test
    public void testIndexView() {
        HelpViewController instance = new HelpViewController();
        String expResult = "help/index";
        String result = instance.indexView();
        assertEquals(expResult, result);
    }

    /**
     * Test of aboutView method, of class HelpViewController.
     */
    @Test
    public void testAboutView() {
        HelpViewController instance = new HelpViewController();
        String expResult = "help/about";
        String result = instance.aboutView();
        assertEquals(expResult, result);
    }

    /**
     * Test of knowledgeBaseView method, of class HelpViewController.
     */
    @Test
    public void testKnowledgeBaseView() throws Exception {
        ModelMap map = new ModelMap();
        HelpViewController controllerInstance = new HelpViewController();
        ArticleCategoryDAO articleCategoryDAOMock = EasyMock.createMock(ArticleCategoryDAO.class);
        EasyMock.expect(articleCategoryDAOMock.getAllCategories())
                .andReturn(Collections.<ArticleCategory>emptyList());
        EasyMock.replay(articleCategoryDAOMock);

        Field f = HelpViewController.class.getDeclaredField("articleCategoryDAO");
        f.setAccessible(true);
        f.set(controllerInstance, articleCategoryDAOMock);

        String expResult = "help/base";
        String result = controllerInstance.knowledgeBaseView(map);
        assertEquals(expResult, result);
        assertNotNull(map.get("categories"));
    }

    /**
     * Test of knowledgeBaseItemView method, of class HelpViewController.
     */
    @Test
    public void testKnowledgeBaseItemView() throws Exception {
        Long id = 1L;
        ModelMap map = new ModelMap();
        HelpViewController controllerInstance = new HelpViewController();

        Article toReturn = new Article();
        toReturn.setArticleId(id);
        toReturn.setTitle("Test article");

        ArticleDAO articleDAOMock = EasyMock.createMock(ArticleDAO.class);
        EasyMock.expect(articleDAOMock.getById(id)).andReturn(toReturn);
        EasyMock.replay(articleDAOMock);

        Field f = HelpViewController.class.getDeclaredField("articleDAO");
        f.setAccessible(true);
        f.set(controllerInstance, articleDAOMock);

        String expResult = "help/base/one";
        String result = controllerInstance.knowledgeBaseItemView(id, map);
        assertEquals(expResult, result);
        assertNotNull(map.get("article"));
        assertEquals(1L, ((Article)map.get("article")).getArticleId().longValue());
    }

}