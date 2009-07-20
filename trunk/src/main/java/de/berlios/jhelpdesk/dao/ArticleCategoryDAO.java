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
package de.berlios.jhelpdesk.dao;

import java.util.List;

import de.berlios.jhelpdesk.model.ArticleCategory;

/**
 * Interfejs DAO dla obiektów ArticleCategory. Nieco rozszrzony ponad
 * standardowe operacje CRUD.
 * 
 * @author jjhop
 * 
 * @see ArticleCategory
 */
public interface ArticleCategoryDAO {

    /**
     * Zwraca listę wszystkich dostępnych kategorii. Zwracana lista zawsze jest
     * prawidłowym obiektem - nigdy {@code null}. Jeśli nie ma dostępnych
     * żadnych kategorii zwracana jest pusta lista.
     * 
     * @return lista obiektów ArticleCategory
     * 
     * @see java.util.Collections#EMPTY_LIST
     */
    public List<ArticleCategory> getAllCategories();

    /**
     * Zwraca listę obiektów ArticleCategory, przy czym każdy z nich ma
     * uzupełnione tylko podstawowe dane (id, nazwa, ilość artykułow) bez
     * wypełnionych list artykułów. Zwracana list zawsze jest prawidłowym
     * obiektem, nigdy nie jest to {@code null}. Jeśli nie ma dostępnych żadnych
     * kategorii zwracana jest pusta lista.
     * 
     * @return lista obiektów ArticleCategory
     * 
     * @see java.util.Collections#EMPTY_LIST
     */
    public List<ArticleCategory> getAllShortSections();

    /**
     * Zwraca obiekt ArticleCategory na podstawie dostarczonego identyfikatora
     * lub {@code ull} jeśli w bazie nie ma obiektu z takim identyfikatorem.
     * 
     * @param categoryId identyfikator oczekiwanego obiektu ArticleCategory
     * @return obiekt ArticleCategory lub {@code null} jeśli nie zostanie
     * znaleziony
     */
    public ArticleCategory getById(Long categoryId);

    /**
     * Usuwa wskazany obiekt ArticleCategory na podstawie dostarczonego
     * identyfikatora.
     * 
     * @param categoryId identyfikator obiektu ArticleCategory do usunięcia
     * @param categoryId
     */
    public void delete(Long categoryId);

    /**
     * Przenosi kategorię o podanym identyfikatorze o jedno miejsce w górę w
     * kolejności wyświetlania.
     * 
     * @param categoryId identyfikator kategorii do przeniesienia
     * 
     * @see #moveDown(java.lang.Long)
     */
    public void moveUp(Long categoryId);

    /**
     * Przenosi kategorię o podanym identyfikatorze o jedno miejsce w dół w
     * kolejności wyświetlania.
     * 
     * @param categoryId identyfikator kategorii do przeniesienia
     * 
     * @see #moveUp(java.lang.Long)
     */
    public void moveDown(Long categoryId);

    /**
     * Zapisuje lub uaktualnia podany obiekt ArticleCategory. Operacja do
     * wykonania wybierana jest na podstawie wartości
     * {@link ArticleCategory#articleCategoryId} dostarczonego obiektu. Jeśli
     * jest on równy {@code null} to obiekt zapisywany jest jako nowy a
     * identyfikator jest uzupełniany. Jeśli dostarczony obiekt posiada już
     * identyfikator({@code category.getArticleCategoryId() != null}) to w bazie
     * danych uaktualniane są jego dane.
     * 
     * @param category obiekt ArticleCategory do zapisania (lub uaktualnienia)
     * 
     * @see ArticleCategory
     */
    public void saveOrUpdate(ArticleCategory category);
}
