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

import de.berlios.jhelpdesk.model.KnowledgeSection;

/**
 * Interfejs DAO dla obiektów KnowledgeSection. Nieco rozszrzony ponad standardowe operacje CRUD.
 *
 * @author jjhop
 *
 * @see KnowledgeSection
 */
public interface KnowledgeSectionDAO {

    /**
     * Zwraca listę wszystkich dostępnych sekcji. Zwracana lista zawsze
     * jest prawidłowym obiektem - nigdy {@code null}. Jeśli nie ma dostępnych
     * żadnych sekcji zwracana jest pusta lista.
     *
     * @return lista obiektów KnowledgeSection
     *
     * @see java.util.Collections#EMPTY_LIST
     */
    public List<KnowledgeSection> getAllSections();

    /**
     * Zwraca listę obiektów KnowledgeSection, przy czym każdy z nich
     * ma uzupełnione tylko podstawowe dane (id, nazwa, ilość artykułow) bez
     * wypełnionych list artykułów. Zwracana list zawsze jest prawidłowym obiektem,
     * nigdy nie jest to {@code null}. Jeśli nie ma dostępnych żadnych sekcji zwracana
     * jest pusta lista.
     *
     * @return lista obiektów KnowledgeSection
     *
     * @see java.util.Collections#EMPTY_LIST
     */
    public List<KnowledgeSection> getAllShortSections();

    /**
     * Zwraca obiekt KnowledgeSection na podstawie dostarczonego identyfikatora
     * lub {@code ull} jeśli w bazie nie ma obiektu z takim identyfikatorem.
     *
     * @param sectionId identyfikator oczekiwanego obiektu KnowledgeSection
     * @return obiekt KnowledgeSection lub {@code null} jeśli nie zostanie znaleziony
     */
    public KnowledgeSection getById(Long sectionId);

    /**
     * Usuwa wskazany obiekt KnowledgeSection na podstawie dostarczonego identyfikatora.
     *
     * @param sectionId identyfikator obiektu KnowledgeSection do usunięcia
     * @param sectionId
     */
    public void delete(Long sectionId);

    /**
     * Przenosi sekcję o podanym identyfikatorze o jedno miejsce w górę
     * w kolejności wyświetlania.
     * 
     * @param sectionId identyfikator sekcji do przeniesienia
     *
     * @see #moveDown(java.lang.Long) 
     */
    public void moveUp(Long sectionId);

    /**
     * Przenosi sekcję o podanym identyfikatorze o jedno miejsce w dół
     * w kolejności wyświetlania.
     * 
     * @param sectionId identyfikator sekcji do przeniesienia
     * 
     * @see #moveUp(java.lang.Long)
     */
    public void moveDown(Long sectionId);

    /**
     * Zapisuje lub uaktualnia podany obiekt KnowledgeSection. Operacja do wykonania
     * wybierana jest na podstawie wartości {@link KnowledgeSection#knowledgeSectionId}
     * dostarczonego obiektu. Jeśli jest on równy {@code null} to obiekt zapisywany jest
     * jako nowy a identyfikator jest uzupełniany. Jeśli dostarczony obiekt posiada już
     * identyfikator({@code section.getKnowledgeSectionId() != null}) to w bazie danych
     * uaktualniane są jego dane.
     *
     * @param section obiekt KnowledgeSection do zapisania (lub uaktualnienia)
     *
     * @see KnowledgeSection
     */
    public void saveOrUpdate(KnowledgeSection section);
}
