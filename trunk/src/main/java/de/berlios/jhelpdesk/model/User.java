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

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.PostLoad;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Transient;

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
@Entity
@Table(name = "users")
@NamedQueries({
    @NamedQuery(name = "User.byLoginAndHashedPassoword", query = "SELECT u FROM User u WHERE u.login=?1 AND u.hashedPassword=?2"),
    @NamedQuery(name = "User.byLogin", query = "SELECT u FROM User u WHERE u.login=?1"),
    @NamedQuery(name = "User.byEmail", query = "SELECT u FROM User u WHERE u.email=?1"),
    @NamedQuery(name = "User.byLoginFetchFilters", query = "SELECT u FROM User u LEFT JOIN FETCH u.filters WHERE u.login=?1"),
    @NamedQuery(name = "User.allOrderByLastName", query = "SELECT u FROM User u ORDER by u.lastName ASC"),
    @NamedQuery(name = "User.allByRoleOrderByLastName", query = "SELECT u FROM User u WHERE u.roleAsInt=?1 ORDER by u.lastName ASC")
})
public class User implements Serializable {

    /**
     * Identyfikator bazodanowy użytkownika.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    /**
     * Login użytkownika. Musi być unikalny w obrębie systemu.
     */
    @Column(name = "login", unique = true, updatable = false, nullable = false)
    private String login;
    
    /**
     * Zahaszowane hasło użytkownika.
     */
    @Column(name = "passw")
    private String hashedPassword;

    /**
     * Imię użytkownika.
     */
    @Column(name = "first_name")
    private String firstName;

    /**
     * Nazwisko użytkownika.
     */
    @Column(name = "last_name")
    private String lastName;

    /**
     * Rola użytkownika.
     *
     * @see #roleAsInt
     * @see #populateRoleDB()
     * @see #populateRoleTransient()
     */
    @Transient
    private Role userRole;

    /**
     * Pole służące do utrwalania w bazie roli użytkownik.
     * Niedostępne za pomocą żadnych metod i używane tylko przez JPA.
     *
     * @see #populateRoleDB()
     * @see #populateRoleTransient()
     */
    @Basic
    @Column(name = "app_role")
    @SuppressWarnings("unused")
    private int roleAsInt;

    /**
     * Email użytkownika. Email nie może być pusty i jest unikalny w obrębie aplikacji.
     */
    @Column(name = "email")
    private String email;

    /**
     * Numer kontaktowy użytkownika.
     */
    @Column(name = "phone")
    private String phone;

    /**
     * Numer telefonu komórkowego użytkownika.
     */
    @Column(name = "mobile")
    private String mobile;

    /**
     * Przechowuje informację o tym, czy użytkownika może się logować.
     */
    @Column(name = "is_active")
    private boolean isActive;

    /**
     * Przechowuje kolekcję artykułów, których autorem jest użytkownik.
     */
    @OneToMany(mappedBy = "author")
    private Set<Article> articles;

    /**
     * Przechowuje kolekcję filtrów użytkownika.
     */
    @OneToMany(mappedBy = "owner")
    private Set<TicketFilter> filters;

    /**
     * Pusty konstuktor. Umieszczony dlatego, że w klasie jest także inny konstruktor.
     */
    public User() {
        this.articles = new HashSet<Article>();
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
        this();
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
        this.roleAsInt = this.userRole.toInt();
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
     * Zwraca kolekcję artykułów, których autorem jest użytkownik.
     *
     * @return kolekcja artykułów, których autorem jest użytkownik
     */
    public Set<Article> getArticles() {
        return articles;
    }

    /**
     * 
     * @param articles
     */
    public void setArticles(Set<Article> articles) {
        this.articles = articles;
    }

    /**
     * Zwraca kolekcję filtrów użytkownika.
     *
     * @return kolekcja filtrów użytkownika
     */
    public Set<TicketFilter> getFilters() {
        return filters;
    }

    public void setFilters(Set<TicketFilter> filters) {
        this.filters = filters;
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

    /**
     * Zwraca preferowaną przez użytkownika skórkę aplikacji.
     *
     * @return preferowana skórka aplikacji
     */
    public String getPreferedTheme() {
        return "hd_blue_theme";
    }

    public Integer getPreferedTicketsListSize() {
        return 20; // TODO: wartość z bazy danych
    }

    @PrePersist
    protected void populateRoleDB() {
        this.roleAsInt = this.userRole.toInt();
    }

    @PostLoad
    protected void populateRoleTransient() {
        this.userRole = Role.fromInt(this.roleAsInt);
    }

    /**
     * Zwraca łańcuchową reprezentację użytkownika. Jest to string postaci "imię nazwisko".
     *
     * @return łańcuchowa reprezentacja uzytkownika
     */
    @Override
    public String toString() {
        return new StringBuilder().append(firstName).append(" ").append(lastName).toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        if (this.userId == null) {
            return false;
        }
        User u = (User) obj;
        return this.userId.equals(u.userId);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + (null == this.userId ? 0 : this.userId.hashCode());
        hash = 31 * hash + (null == this.login ? 0 : this.login.hashCode());
        hash = 31 * hash + (null == this.firstName ? 0 : this.firstName.hashCode());
        hash = 31 * hash + (null == this.lastName ? 0 : this.lastName.hashCode());
        hash = 31 * hash + (null == this.email ? 0 : this.email.hashCode());
        return hash;
    }
}
