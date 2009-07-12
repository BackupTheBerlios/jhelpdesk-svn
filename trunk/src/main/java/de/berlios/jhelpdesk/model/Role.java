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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <p>
 * Klasa modelująca role użytkowników w systemie. Dla wygody programisty zawiera
 * statyczne wartości dla wszystkich ról oraz metody pozwalające na wygodne
 * używanie jej obiektów (np. zapisywanie ról w bazie za pomocą liczb i odtwarzenie
 * tychże z zapisanych liczba - szczegóły w dokumentacji poszczególnych metod).
 * </p>
 * <p>
 * TODO: należy lokalizowac nazwy ról i umiescic je w plikach role.properties
 * </p>
 * 
 * @author jjhop
 */
public class Role {

    /**
     * Liczba, za pomocą której rola może być przedstawiana w bazie danych.
     */
    private final int code;

    /**
     * Nazwa roli.
     */
    private final String roleName;

    /**
     * Lista wszystkich dostępnych ról.
     */
    private final static List<Role> roles;
    
    /**
     * Rola CLIENT. Użytkownik w tej roli może przede wszystkim zgłaszać błędy. Może także
     * w ograniczony sposób korzystać z pozostałych części systemu, nie może jednak edytować
     * nic poza częścią swoich danych osobowych.
     */
    public static final Role CLIENT = new Role(1, "Użytkownik");

    /**
     * Rola BUGKILLER. Uzytkownik w tej roli może zgłaszać problemy w swoim imieniu, oraz
     * w imieniu innych użytkowników. Może także w pewnym zakresie edytować zgłoszenia
     * (np. przypisywać je do rozwiązania przez siebie ale nie przez innych użytkowników).
     * Może także edytować wiele innych elementów systemu.
     */
    public static final Role BUGKILLER = new Role(10, "Pracownik helpdesku");

    /**
     * Rola MANAGER. Użytkownik z nieograniczonymi uprawnieniami w systemie. Może zmieniać role
     * innych użytkowników, przypisywać zgłoszenia do rozwiązania przez dowolnego użytkownika
     * z rolą BUGKILLER. W pełni może edytować wszystkie obiekty w systemie.
     */
    public static final Role MANAGER = new Role(100, "Helpdesk manager");


    static {
        // początkowa inicjalizacja listy wszystkich ról w systemie
        roles = new ArrayList<Role>();
        roles.add(CLIENT);
        roles.add(BUGKILLER);
        roles.add(MANAGER);
    }

    /**
     * Zwraca listę wszystkich ról w systemie. Zwrócona lista jest niemodyfikowalna.
     * 
     * @return lista (niemodyfikowalna) wszystkich ról w systemie
     *
     * @see #roles
     * @see Collections#unmodifiableList(java.util.List)
     */
    public static List<Role> getRoles() {
        return Collections.unmodifiableList(roles);
    }

    /**
     * <p>
     * Odtwarza rolę z podanego identyfikatora. Identyfikator musi mieć wartość jedną z trzech:
     * 1, 10 lub 100. Dla każdej innej wartości zgłasza wyjątk RuntimeException.
     * </p>
     * <p>
     * W bazie danych spójność powinna być zachowywana poprzez zdefiniowanie domeny z podanymi
     * wartościami lub zastosowanie mechanizmu sprawdzania wartości przed zapisaniem użytkownika
     * za pomocą CHECK lub TRIGGERa.
     * </p>
     * @param code identyfikator roli
     * @return prawidłowy obiekt Role
     * @throws RuntimeException jeśli podane liczba ma wartość inną niż 1, 10, lub 100
     *
     * @see #code
     * @see #getRoleCode()
     * @see User
     */
    public static Role fromInt(int code) {
        switch (code) {
            case 1:
                return CLIENT;
            case 10:
                return BUGKILLER;
            case 100:
                return MANAGER;
            default:
                throw new RuntimeException("Nieznana rola.");
        }
    }

    /**
     * Tworzy rolę na podstawie podanego identyfikatora i nazwy.
     * 
     * @param code identyfikator roli
     * @param roleName nazwa roli
     */
    private Role(int code, String roleName) {
        this.code = code;
        this.roleName = roleName;
    }

    /**
     * Zwraca liczbową reprezenację roli. Zwrócona liczba jest identyfikatorem roli.
     * Na jej podstawie można bezpiczenie odtworzyć role za pomocą {@link #fromInt(code)}.
     *
     * @return liczbowa reprezentacja roli
     *
     * @see #code
     * @see #getRoleCode()
     */
    public int toInt() {
        return this.getRoleCode();
    }

    /**
     * Zwraca nazwę roli. Nazwa jest ustalana podczas tworzenia roli.
     *
     * TODO: i18n dla nazw
     *
     * @return nazwa roli
     * 
     * @see #roleName
     */
    public String getRoleName() {
        return roleName;
    }

    /**
     * Zwraca identyfikator roli. Na podstawie identyfikatora można bezpiecznie odtwarzać 
     * role za pomocą {@link #fromInt(code)}.
     *
     * @return identyfikator roli
     *
     * @see #code
     * @see #toInt()
     */
    public int getRoleCode() {
        return code;
    }
}
