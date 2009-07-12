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

import de.berlios.jhelpdesk.model.Knowledge;

/**
 * Interfejs DAO dla obiektów Knowledge. Nieco rozszrzony ponad standardowe operacje CRUD.
 *
 * @author jjhop
 *
 * @see Knowledge
 * @see de.berlios.jhelpdesk.model.KnowledgeSection
 */
public interface KnowledgeDAO {

    /**
     * Zwraca obiekt Knowledge na podstawie dostarczonego identyfikatora lub
     * {@code null} jeśli obiekt o tym identyfikatorze nie istnieje w bazie danych.
     *
     * @param knowledgeId identyfikator oczekiwanego obiektu Knowledge
     *
     * @return obiekt Knowledge lub {@code null} jeśli nie zostanie znaleziony
     */
    Knowledge getById(Long knowledgeId);

    /**
     * Zapisuje lub uaktualnie podany obiekt Knowledge. Operacja wybierana jest na podstawie
     * wartości {@link Knowledge#knowledgeId} dostarczonego obiektu. Jeśli jest on równy
     * {@code null} to obiekt zapisywany jest jako nowy a identyfikator jest uzupełniany.
     * Jeśli obiekt posiada identyfikator ({@code knowledge.getKnowledgeId() != null}) to
     * w bazie danych uaktualniane są jego dane.
     * 
     * @param knowledge obiekt Knowledge do zapisania (lub uaktualnienia)
     */
    void saveOrUpdate(Knowledge knowledge);

    /**
     * Usuwa wskazany obiekt Knowledge.
     *
     * @param knowledge obiekt Knowledge do usunięcia
     */
    void delete(Knowledge knowledge);

    /**
     * Usuwa obiekt Knowledge o podanym identyfikatorze.
     *
     * @param knowledge obiekt Knowledge do usunięcia
     */
    void delete(Long knowledgeId);

    /**
     * Zwraca listę obiektów Knowledge z sekcji o podanym identyfikatorze. Zwrócona
     * lista jest zawsze prawidłowym obiektem. Jeśli nie zostaną znalezione żadne obiekty
     * to lista będzie pusta ale nigdy nie będzie {@code nullem}.
     * 
     * @param sectionId identyfikator sekcji, z której mają pochodzić obiekty Knowledge
     * @return lista obiektów Knowledge (może być pusta)
     */
    List<Knowledge> getForSection(Long sectionId);

    /**
     * Zwraca listę obiektów Knowledge o rozmiarze co najwyżej {@code howMuch}. Lista
     * zawiera obiekty Knowledge o najświeższej dacie dodania ({@link Knowledge#createDate});
     *
     * @param howMuch
     * @return lista obiektów Knowledge (może być pusta)
     *
     * @see Knowledge
     * @see Knowledge#getCreateDate() 
     */
    List<Knowledge> getLastAddedArticles(int howMuch);
}
