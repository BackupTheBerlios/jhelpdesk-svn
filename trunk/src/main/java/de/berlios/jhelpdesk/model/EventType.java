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

/**
 * <p>
 * Typ zdarzenia związanego z <i>życiem</i> zgłoszenia w systemie.
 * </p>
 * <p>
 * TODO: wszystkie nazwy trzeba zaciągnąć z właściwych propertisow<br/>
 * TODO: uzupełnić dokumentację
 * </p>
 * @author jjhop
 *
 * @see Ticket
 * @see TicketEvent
 */
public enum EventType {

    /**
     * 
     */
    CREATEEVENT(1, "Zgłoszenie problemu."),

    /**
     *
     */
    ASSIGNEVENT(2, "Przypisanie problemu."),

    /**
     *
     */
    REASSIGNEVENT(3, "Zmiana przypisania problemu."),

    /**
     *
     */
    CLOSEEVENT(4, "Zamknięcie/rozwiązanie problemu."),

    /**
     *
     */
    REJECTEVENT(5, "Odrzucenie problemu."),

    /**
     * 
     */
    CATEGORYCHANGEEVENT(6, "Zmiana kategorii."),

    /**
     *
     */
    PRIORITYCHANGEEVENT(7, "Zmiana ważności."),

    /**
     *
     */
    STATUSCHANGEEVENT(8, "Zmiana statusu."),

    /**
     *
     */
    COMMENTADDEVENT(9, "Dodanie komentarza.");

    /**
     * Liczbowy identyfikator zdarzenia. Za jego pomocą można zapisywać je w
     * bazie danych.
     */
    private final int code;

    /**
     * Nazwa zdarzenia.
     */
    private final String typeName;

    /**
     * Konstruktor tworzący zdarzenie za pomocą podanego kodu liczbowego oraz
     * nazwy.
     * 
     * @param code kod liczbowy, identyfikator zdarzenia
     * @param typeName nazwa zdarzenia
     * 
     * @see #code
     * @see #typeName
     */
    private EventType(int code, String typeName) {
        this.code = code;
        this.typeName = typeName;
    }

    /**
     * Zwraca liczbową reprezentację typu zdarzenia. Na jej podstawie można
     * bezpiecznie odtworzyć type za pomocą {@link #fromInt(int)}.
     * 
     * @return liczbowa reprezentacja zdarzenia
     * 
     * @see #code
     */
    public int toInt() {
        return code;
    }

    /**
     * Zwraca nazwę zdarzenia. Nazwa jest ustalana podczas tworzenia obiektu.
     * 
     * @return nazwa zdarzenia
     *
     * @see #typeName
     */
    public String getTypeName() {
        return typeName;
    }

    /**
     * <p>
     * Odtwarza typ zdarzenia z podanego identyfikatora. Identyfikator musi mieć
     * wartość ze z góry ustalonego zbioru. Dla każdej inne wartości zgłasza
     * wyjątek RuntimeException.
     * </p>
     * <p>
     * W bazie danych spójność powinna być zachowana poprzez zdefiniowanie
     * domeny z odpowiednimi wartościami lub zastosowanie mechanizmu sprawdzanai
     * wartości przed zapisaniem zdarzenia za pomocą CHECK lub TRIGGERa.
     * </p>
     * 
     * @param code identyfikator zdarzenia
     * @return prawidłowy obiekt zdarzenia
     * @throws RuntimeExceptionjesli podana liczba jest spoza spodziewanego
     * zbioru
     * 
     * @see #code
     * @see #toInt()
     * @see TicketEvent
     */
    public static EventType fromInt(int code) {
        switch (code) {
            case 1:
                return CREATEEVENT;
            case 2:
                return ASSIGNEVENT;
            case 3:
                return REASSIGNEVENT;
            case 4:
                return CLOSEEVENT;
            case 5:
                return REJECTEVENT;
            case 6:
                return CATEGORYCHANGEEVENT;
            case 7:
                return PRIORITYCHANGEEVENT;
            case 8:
                return STATUSCHANGEEVENT;
            case 9:
                return COMMENTADDEVENT;
            default:
                throw new RuntimeException("Nieznany typ zdarzenia.");
        }
    }

    /**
     * Zwraca łańcuchową reprezentację typu zdarzenia.
     *
     * @return łańcuchowa reprezentacja typu zdarzenia
     *
     * @see #typeName
     */
    @Override
    public String toString() {
        return typeName;
    }
}
