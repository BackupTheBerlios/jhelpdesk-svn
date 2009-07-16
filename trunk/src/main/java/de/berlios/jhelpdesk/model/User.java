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
package de.berlios.jhelpdesk.model;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * Reprezentacja użytkownika w systemie. Przechowuje wszystkie istotne informacje związane z użytkownikem
 * i udostępnia je aplikacji.
 *
 * <p>
 * Klasa funkcjonuje jako <i>bean</i>, ale część danych nie powinna ulegać zmianie po pierwszym zapisaniu
 * w bazie danych. Przy polach, które nie powinny być zmieniane zostało to ujete w komentarzu przy
 * metodzie {@code setXXX(Object data)}. Oczywiście metody te są używane także podczas wiązania danych
 * z formularza z odpowiednimi polami obiektu.
 * </p>
 *
 * @author jjhop
 */
public class User {

    /**
     * Identyfikator bazodanowy użytkownika.
     */
    private Long userId;

    /**
     * Login użytkownika. Musi być unikalny w obrębie systemu.
     */
    private String login;
    
    /**
     * Zahaszowane hasło użytkownika.
     */
    private String hashedPassword;

    /**
     * Imię użytkownika.
     */
    private String firstName;

    /**
     * Nazwisko użytkownika.
     */
    private String lastName;

    /**
     * Rola użytkownika.
     */
    private Role userRole;

    /**
     * Email użytkownika. Email nie może być pusty i jest unikalny w obrębie aplikacji.
     */
    private String email;

    /**
     * Numer kontaktowy użytkownika.
     */
    private String phone;

    /**
     * Numer telefonu komórkowego użytkownika.
     */
    private String mobile;

    /**
     * Przechowuje informację o tym, czy użytkownika może się logować.
     */
    private boolean isActive;

    /**
     * Pusty konstuktor. Umieszczony dlatego, że w klasie jest także inny konstruktor.
     */
    public User() {
    }

    /**
     * Konstruktor inicjalizujący kilka niezbędnych zmiennych w obiekcie.
     *
     * @param userId identyfikator użytkownika
     * @param login login użytkownika
     * @param firstName imię użytkownika
     * @param lastName nazwisko użytkownika
     */
    public User(Long userId, String login, String firstName, String lastName) {
        this.userId = userId;
        this.login = login;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    /**
     * Zwraca bazodanowy identyfikator uzytkownika.
     *
     * @return bazodanowy identyfikator uzytkownika
     *
     * @see #userId
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * Ustawia bazodanowy identyfikator użytkownika. Metod powinna być wykorzystywana tylko
     * i wyłącznie przez mechanizm przepisywania danych z bazy.
     *
     * @param userId identyfikator do ustawienia
     *
     * @see #userId
     * @see #getUserId() 
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * Zwraca login użytkownika. Login jest unikalny w obrębie systemu.
     *
     * @return login użytkownika
     *
     * @see #login
     */
    public String getLogin() {
        return login;
    }

    /**
     * Ustawia login użytkownika. Po pierwszym ustawieniu i zapisaniu w bazie danych
     * login nie mogę podlegać zmianom. Od tego czasu metoda powinna być wykorzystywana
     * tylko przez mechanizm przepisywania danych z bazy.
     * 
     * @param login login użytkownika do zapisania
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * Zwraca hasło użytkownika. Hasło jest skrótem wybranej przez użytkownika wartości.
     *
     * @return hasło użytkownika w postaci skrótu
     */
    public String getPassword() {
        return hashedPassword;
    }

    /**
     * Ustawia hasło użytkownika.
     *
     * @param password hasło użytkownika
     */
    // TODO: dolożyć szczyptę soli do hasła
    public void setPassword(String password) {
        this.hashedPassword = DigestUtils.shaHex(password);
    }

    /**
     * Zwraca systemową rolę użytkownika.
     * 
     * @return systemowa rola użytkownika
     *
     * @see #userRole
     * @see Role
     */
    public Role getUserRole() {
        return userRole;
    }

    /**
     * Ustawia nową rolę systemową dla użytkownika.
     * 
     * @param userRole rsystemowa rola, którą przydzielamy użytkownikowi
     *
     * @see #userRole
     * @see #getUserRole() 
     * @see Role
     */
    public void setUserRole(Role userRole) {
        this.userRole = userRole;
    }

    /**
     * Zwraca email użytkownika. Email jest unikalny w obrębie aplikacji.
     *
     * @return email użytkownika ({@code email != null})
     *
     * @see #email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Ustawia email dla użytkownika.
     * 
     * @param email email dla użytkownika.
     *
     * @see #email
     * @see #getEmail() 
     */
    public void setEmail(String email) {
        assert email != null : "Email nie może być pusty";
        this.email = email;
    }

    /**
     * Zwraca imię użytkownika.
     *
     * @return imię użytkownika
     * 
     * @see #firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Ustawia imię dla użytkownika.
     * 
     * @param firstName imię użytkownika
     *
     * @see #firstName
     * @see #getFirstName() 
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Zwraca nazwisko użytkownika.
     *
     * @return nazwisko użytkownika
     *
     * @see #lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Ustawia nazwisko użytkownika.
     * 
     * @param lastName nazwisko użytkownika
     *
     * @see #lastName
     * @see #getLastName() 
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Informuje czy użytkownik może się logować.
     *
     * @return {@code true} jeśli użytkowni możę się logować i {@code false} w przeciwnym wypadku
     *
     * @see #isActive
     */
    public boolean isActive() {
        return isActive;
    }

    /**
     * Ustawia status użytkownika. Jeśli użytkownika nie ma prawa do logowania to status
     * jest {@code false}.
     *
     * @param isActive status logowania dla użytkownika
     *
     * @see #isActive
     * @see #isActive() 
     */
    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    /**
     * Zwraca numer telefonu komórkowego użytkownika.
     *
     * @return numer telefonu komórkowego użytkownika
     *
     * @see #mobile
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * Ustawia numer telefonu komórkowego dla użytkownika.
     * 
     * @param mobile The mobile to set.
     *
     * @see #mobile
     * @see #getMobile() 
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /**
     * Zwraca numer telefonu użytkownika.
     *
     * @return numer telefonu użytkownika
     *
     * @see #phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Ustawia numer telefonu użytkownika.
     *
     * @param phone numer telefonu użytkownika
     *
     * @see #phone
     * @see #getPhone() 
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Zwraca łańcuchową reprezentację użytkownika. Jest to string postaci "imię nazwisko".
     * 
     * @return łańcuchowa reprezentacja uzytkownika
     */
    @Override
    public String toString() {
        return new StringBuilder("").append(firstName).append(" ").append(lastName).toString();
    }

    /**
     * Zwraca łańcuch składający się z imienia i nazwiska użytkownika.
     *
     * @return łańcuch składający się z imienia i nazwiska użytkownika
     *
     * @see #firstName
     * @see #lastName
     * @see #toString() 
     */
    public String getFullName() {
        return toString();
    }
}
