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

import java.util.Date;
import java.util.List;

import de.berlios.jhelpdesk.model.Role;
import de.berlios.jhelpdesk.model.User;

/**
 * Podstawowe DAO dla obiektów User. Wykonuje wszystkie istotne operacje bazodanowe
 * związane z użytkownikiem, czyli nieco ponad standardowy CRUD.
 * 
 * @author jjhop
 */
public interface UserDAO {

    /**
     * Zwraca listę użytkowników, których nazwisko zaczyna się na podaną literę.
     * Jeśli żaden użytkownik nie zostanie odnaleziony zwracana jest pusta lista.
     *
     * @param letter pierwsza litera nazwiska szukanych użytkowników
     * @return lista użytkowników, których nazwisko zaczyna się na podaną literę
     *
     * @see User
     * @see java.util.Collections#EMPTY_LIST
     */
    List<User> getAllUserWithLastNameStartsWithLetter(String letter);

    /**
     * Zwraca listę użytkowników w roli {@link Role#TICKETKILLER}, których nazwisko
     * zaczyna się na podaną literę.
     * 
     * @param letter pierwsza litera nazwiska szukanych użytkowników
     * @return lista użytkowników, których nazwisko zaczyna się na podaną literę
     *
     * @see User
     * @see java.util.Collections#EMPTY_LIST
     */
    List<User> getSavioursWithLastNameStartsWithLetter(String letter);

    /**
     * Zwraca wszystkich użytkowników w systemie. Lista zawsze jest jest różna od {@code null}
     * i zawiera przynajmniej jednego użytkownika.
     *
     * @return lista wszystkich użytkowników w systemie
     *
     * @see User
     */
    List<User> getAllUser();

    /**
     * Zwraca wszystkich użytkowników sytemu z podaną rolą. Jeśli nie zostanie znaleziony
     * żadne użytkownik zwrócona zostanie pusta lista.
     *
     * @param role rola, jaką mają mieć szukani użytkownicy
     * @return lista użytkowników systemu z podaną rolą
     *
     * @see User
     * @see java.util.Collections#EMPTY_LIST
     */
    List<User> getByRole(Role role);

    /**
     * @deprecated należy używać {@link #getByRole(Role role)}
     */
     List<User> getSaviours();

    /**
     * Zwraca użytkownika o podanym identyfikatorze lub {@code null} jeśli
     * nie zostanie odnaleziony.
     *
     * @param id identyfikator poszukiwanego użytkownika
     * @return obiekt użytkownika o podanym identyfikatorze
     *
     * @see User
     */
    User getById(Long id);

    /**
     * Zwraca użytkownika o podanym loginie lub {@code null} jeśli
     * nie zostanie odnaleziony.
     *
     * @param login login poszukiwanego użytkownika
     * @return obiekt użytkownika o podanym identyfikatorze
     */
    User getByLogin(String login);

    /**
     * Sprawdza czy w systemie istnieje użytkownik z pasującą do podanych
     * parą {@code login} oraz {@code hasło}. Zwraca {@code true} jeśli użytkownik
     * zostanie odnaleziony i hasło pasuje oraz {@code false} w każdym innym wypadku.
     * 
     * @param login login użytkownika
     * @param passw hasło użytkownika
     * @return {@code true} jeśli odnajdzie użytkownika z podanym hasłem oraz {@code false}
     *     w każdym innym wypadku
     */
    boolean authenticate(String login, String passw);

    /**
     * Loguje użytkownika w systemie. Zapisuje informacje o czasie logowania.
     *
     * @param login login uzytkownika (użytkownik o tym loginie musi istnieć w systemie)
     * @param date data logowanie
     */
    void loginUser(String login, Date date);

    /**
     * Zapisuje dane nowego użytkownika lub uaktualnie dane istniejącego. Wybór akcji odbywa się
     * na podstawie wartości identyfikatora ({@link User#getUserId()}. Jeśli jest różny od {@code null}
     * to uaktualnie dane użytkownika o tym właśnie identyfikatorze w przeciwym wypadku zapisuje
     * dane nowego użytkownika. Identyfikator w obiekcie zostaje uaktualniony o wartość nadaną w bazie.
     *
     * @param user obiekt użytkownika do zapisania lub uaktualnienia
     */
    void saveOrUpdate(User user);
}
