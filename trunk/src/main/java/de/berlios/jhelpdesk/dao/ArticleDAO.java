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

import de.berlios.jhelpdesk.model.Article;

/**
 * Interfejs DAO dla obiektów Article. Nieco rozszerzony ponad standardowe
 * operacje CRUD.
 * 
 * @author jjhop
 * 
 * @see Article
 * @see de.berlios.jhelpdesk.model.ArticleCategory
 */
public interface ArticleDAO {

    /**
     * Zwraca obiekt Article na podstawie dostarczonego identyfikatora lub
     * {@code null} jeśli obiekt o tym identyfikatorze nie istnieje w bazie
     * danych.
     * 
     * @param articleId identyfikator oczekiwanego obiektu Article
     * 
     * @return obiekt Article lub {@code null} jeśli nie zostanie znaleziony
     */
    Article getById(Long articleId);

    /**
     * Zapisuje lub uaktualnie podany obiekt Article. Operacja wybierana jest na
     * podstawie wartości {@link Article#articleId} dostarczonego obiektu.
     * Jeśli jest on równy {@code null} to obiekt zapisywany jest jako nowy a
     * identyfikator jest uzupełniany. Jeśli obiekt posiada identyfikator (
     * {@code article.getArticleId() != null}) to w bazie danych
     * uaktualniane są jego dane.
     * 
     * @param article obiekt Article do zapisania (lub uaktualnienia)
     */
    void saveOrUpdate(Article article);

    /**
     * Usuwa wskazany obiekt Article.
     * 
     * @param article obiekt Article do usunięcia
     */
    void delete(Article article);

    /**
     * Usuwa obiekt Article o podanym identyfikatorze.
     * 
     * @param articleId id obiektu Article do usunięcia
     */
    void delete(Long articleId);

    /**
     * Zwraca listę obiektów Article z sekcji o podanym identyfikatorze.
     * Zwrócona lista jest zawsze prawidłowym obiektem. Jeśli nie zostaną
     * znalezione żadne obiekty to lista będzie pusta ale nigdy nie będzie
     * {@code nullem}.
     * 
     * @param categoryId identyfikator sekcji, z której mają pochodzić obiekty
     * Article
     * @return lista obiektów Article (może być pusta)
     */
    List<Article> getForSection(Long categoryId);

    /**
     * Zwraca listę obiektów Article o rozmiarze co najwyżej {@code howMuch}.
     * Lista zawiera obiekty Article o najświeższej dacie dodania (
     * {@link Article#createDate});
     * 
     * @param howMuch
     * @return lista obiektów Article (może być pusta)
     * 
     * @see Article
     * @see Article#getCreateDate()
     */
    List<Article> getLastArticles(int howMuch);
}
