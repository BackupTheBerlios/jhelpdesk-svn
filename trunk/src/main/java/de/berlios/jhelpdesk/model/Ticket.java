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
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.PostLoad;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * Klasa będąca osią aplikacji. Modeluje zgłoszenie problemu do działu wsparcia w rzeczywistym świecie.
 * I jako takie ma związane ze sobą podstawowe informacje takie jak osoba, która problem zgłosiła,
 * datę zgłoszenia itd.
 * </p>
 * <p>
 * Klasa funkcjonuje jako <i>bean</i>, ale większość danych nie powinna ulegać zmianie po pierwszym zapisaniu
 * zgłoszenia w bazie danych. Przy polach, które nie powinny być zmieniane zostało to ujete w komentarzu przy
 * metodzie {@code setXXX(Object data)}. Oczywiście metody te są używane także podczas wiązania danych
 * z formularza z odpowiednimi polami obiektu.
 * </p>
 *
 * @author jjhop
 */
@Entity
@Table(name = "ticket")
@SequenceGenerator(name = "ticket_sequence", sequenceName = "ticket_id_seq", allocationSize = 1)
@NamedQueries({
    @NamedQuery(name = "Ticket.orderByCreateDateDESC", query = "FROM Ticket t ORDER BY t.createDate DESC"),
    @NamedQuery(name = "Ticket.byStatusOrderByCreateDateDESC", query = "FROM Ticket t WHERE t.ticketStatusAsInt=? ORDER BY t.createDate DESC"),
//    @NamedQuery(name = "Ticket.byLogin", query = "SELECT u FROM User u WHERE u.login=?"),
//    @NamedQuery(name = "Ticket.allOrderByLastName", query = "SELECT u FROM User u ORDER by u.lastName ASC"),
    @NamedQuery(name = "Ticket.allByCategory", query = "FROM Ticket t WHERE t.ticketCategory=? ORDER BY t.createDate DESC"),
    @NamedQuery(name = "Ticket.allByPriority", query = "FROM Ticket t WHERE t.ticketPriorityAsInt=? ORDER BY t.createDate DESC"),
    @NamedQuery(name = "Ticket.allByStatus", query = "FROM Ticket t WHERE t.ticketStatusAsInt=? ORDER BY t.createDate DESC"),
    @NamedQuery(name = "Ticket.allByNotifier", query = "FROM Ticket t WHERE t.notifier=? ORDER BY t.createDate DESC")
})
public class Ticket implements Serializable {

    /**
     * Identyfikator zgłoszenia w bazie danych.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="ticket_sequence")
    @Column(name = "ticket_id")
    private Long ticketId;

    /**
     * Data utworzenia zgłoszenia.
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_date")
    private Date createDate;

    /**
     * Użytkownik, który zgłosił problem.
     */
    @ManyToOne(optional = false)
    @JoinColumn(name = "notifyier", referencedColumnName = "user_id")
    private User notifier;

    /**
     * Użytkownik, który rozwiązuje problem. Może być {@code null} jeśli zgłoszenie jeszcze
     * nie zostało do nikogo przypisane.
     */
    @ManyToOne(optional = true)
    @JoinColumn(name = "saviour", referencedColumnName = "user_id")
    private User saviour;

    /**
     * Użytkownik, który wprowadził zgłoszenie. Zgłoszenie może być wprowadzone do systemu
     * na kilka sposobów. Zawsze jednak akt zgłoszenie związany jest z jakimś użytkownikiem.
     */
    @ManyToOne(optional = false)
    @JoinColumn(name = "inputer", referencedColumnName = "user_id")
    private User inputer;

    /**
     * Krótki opis zgłoszenia. Max 256 znaków.
     */
    @Column(name = "subject", length = 256)
    private String subject;

    /**
     * Dokładny opis zgłoszenia. Max 8192 znaki.
     */
    @Column(name = "description", length = 8192)
    private String description;

    /**
     * Szczegółowy opis zgłoszenia. Jeśli problem jest powtarzalny i można opisać 
     * sposób w jaki go wywołać, to właśnie tutaj będzie on zapisany.
     */
    @Column(name = "step_by_step")
    private String stepByStep;

    /**
     * Aktualny status zgłoszenia.
     *
     * @see TicketStatus
     * @see #populateTicketEnumsDB()
     * @see #populateTicketEnumsTransient()
     */
    @Transient
    private TicketStatus ticketStatus;

    @Basic
    @Column(name = "ticket_status")
    @SuppressWarnings("unused")
    private int ticketStatusAsInt;

    /**
     * Ważność problemy, którego dotyczy zgłoszenie.
     *
     * @see TicketPriority
     * @see #populateTicketEnumsDB()
     * @see #populateTicketEnumsTransient() 
     */
    @Transient
    private TicketPriority ticketPriority;

    @Basic
    @Column(name = "ticket_priority")
    @SuppressWarnings("unused")
    private int ticketPriorityAsInt;

    /**
     * Kategoria, do której zakwalifikowano zgłoszenie.
     *
     * @see TicketCategory
     */
    @ManyToOne
    @JoinColumn(name = "ticket_category")
    private TicketCategory ticketCategory;
    
    /**
     * Kolekcja komentarzy dotyczących zgłoszenia.
     *
     * @see TicketComment
     */
    @OneToMany(mappedBy = "ticket", cascade = CascadeType.ALL)
    private Set<TicketComment> comments;
    
    /**
     * Kolekcja zdarzeń związanych ze zgłoszeniem. Powstanie zgłoszenia zawsze jest
     * pierwszym zdarzeniem w kolekcji.
     */
    @OneToMany(mappedBy = "ticket")
    private Set<TicketEvent> events;

    /**
     * Kolekcja plików dołączonych do zgłoszenia.
     * 
     * TODO: obsłużyć zapisywanie i usuwanie tych plików
     */
    @OneToMany(mappedBy = "ticket")
    private List<AdditionalFile> addFilesList;

    /**
     * Artykuły w bazie wiedzy, które wiążą się treścią
     * z tym zgłoszeniem.
     */
    @ManyToMany(mappedBy="associatedTickets")
    private Set<Article> articles;
    
    /**
     * Zmienna przechowująca plik po dodaniu do zgłoszenia. Wykorzystywana jest 
     * tylko podczas obsługi wysłania formularza o wystąpieniu problemu.<br/>
     *
     * <font color="red">TODO: wywalić, obsluga uploadu obsługiwana oddzielną encją.</font>
     *
     * @deprecated
     */
    @Transient
    private MultipartFile uploadedFile;

    /**
     * Podstawowy konstruktor. Inicjalizuje kolekcje obiektu.
     */
    public Ticket() {
        this.comments = new HashSet<TicketComment>();
        this.addFilesList = new ArrayList<AdditionalFile>();
        this.articles = new HashSet<Article>();
        this.events = new HashSet<TicketEvent>();
        this.ticketStatus = TicketStatus.NOTIFIED;
        this.createDate = new Date();
    }

    /**
     * Zwraca identyfikator zgłoszenia (klucz podstawowy w bazie danych).
     *
     * @return identyfikator zgłoszenia
     */
    public Long getTicketId() {
        return ticketId;
    }

    /**
     * Ustawia identyfikator zgłoszenia. Metoda powinna być wykorzystywana
     * tylko podczas przepisywania danych o zgłoszeniu z bazy danych.
     *
     * @param ticketId identyfikator zgłoszenia
     */
    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
    }

    /**
     * Zwraca informację o aktualnej kategorii, do której zakwalifikowano zgłoszenie.
     *
     * @return aktualna kategoria, do której zakwalifikowano problem
     */
    public TicketCategory getTicketCategory() {
        return ticketCategory;
    }

    /**
     * Zmienia aktualną kwalifikację kategorii dla zgłoszenia.
     *
     * @param ticketCategory nowa kategoria dla zgłoszenia
     */
    public void setTicketCategory(TicketCategory ticketCategory) {
        this.ticketCategory = ticketCategory;
    }

    /**
     * Zwraca czas utworzenia zgłoszenia.
     *
     * @return czas utworzenia zgłoszenia
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * Ustawia czas utworzenia zgłoszenia. Metoda powinna być wykorzystywana 
     * tylko podczas przepisywania danych o zgłoszeniu z bazy danych.
     *
     * @param ticketCreateDate czas utworzenia zgłoszenia
     */
    public void setCreateDate(Date ticketCreateDate) {
        this.createDate = ticketCreateDate;
    }

    /**
     * Zwraca aktualną informację o ważności zgłoszenia. Ważność może ulegać zmianie
     * w trakcie życia zgłoszenia i informacje o zmianach będą odnotowane jako zdarzenia.
     *
     * @return aktualna informację o ważności zgłoszenia
     *
     * @see TicketPriority
     * @see TicketEvent
     */
    public TicketPriority getTicketPriority() {
        return ticketPriority;
    }

    /**
     * Zmienia aktualną ważność zgłoszenia.
     *
     * @param ticketPriority nowa ważność dla zgłoszenia
     *
     * @see #ticketPriority
     * @see #getTicketPriority()
     * @see TicketPriority
     * @see TicketEvent
     */
    public void setTicketPriority(TicketPriority ticketPriority) {
        this.ticketPriority = ticketPriority;
    }

    /**
     * Zwraca informację o aktualnym statusie zgłoszenia. Status zmienia się w trakcie
     * życia zgłoszenia i informacje o zmianach będą odnotowane jako zdarzenia.
     * 
     * @return informacja o aktualnym statusie zgłoszenia
     * @see #ticketStatus
     * @see TicketStatus
     * @see TicketEvent
     */
    public TicketStatus getTicketStatus() {
        return ticketStatus;
    }

    /**
     * Zmienia aktualny status zgłoszenia.
     * <p>TODO: status powinien zmieniać się według jakiegoś schematu.
     * Patrz komentarz w {@link TicketStatus}
     * </p>
     * @param ticketStatus nowy status zgłoszenia.
     * 
     * @see #ticketStatus
     * @see #getTicketStatus() 
     * @see TicketStatus
     */
    public void setTicketStatus(TicketStatus ticketStatus) {
        this.ticketStatus = ticketStatus;
    }

    /**
     * Zwraca krótki opis zgłoszenia. Opis zawiera co najwyżej 256 znaków.
     *
     * @return krótki opis zgłoszenia
     *
     * @see #subject
     */
    public String getSubject() {
        return subject;
    }

    /**
     * Ustawia krótki opis zgłoszenia. Metoda powinna być wykorzystywana
     * tylko podczas przepisywania danych o zgłoszeniu z bazy danych.
     * 
     * @param subject nowa wartość krótkiego opisu zgłoszenia
     *
     * @see #subject
     * @see #getSubject()
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * Zwraca dokładny opis zgłoszenia. Opis zawiera co najwyżsej 8192 znaki.
     * 
     * @return dokładny opis zgłoszenia
     *
     * @see #description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Ustawia dokładny opis zgłoszenia. Metoda powinna być wykorzystywana
     * tylko podczas przepisywania danych o zgłoszeniu z bazy danych.
     * 
     * @param description nowa wartość dokładnego opisu zgłoszenia
     *
     * @see #description
     * @see #getDescription()
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Zwraca szczegółowy opis zgłoszenia. Jeśli problem jest powtarzalny i można 
     * opisać sposób w jaki go wywołać, to właśnie tutaj będzie on zapisany.
     * 
     * @return szczegółowy opis zgłoszenia
     *
     * @see #stepByStep
     */
    public String getStepByStep() {
        return stepByStep;
    }

    /**
     * Ustawia szczegółowy opis zgłoszenia. Metoda powinna być wykorzystywana
     * tylko podczas przepisywania danych o zgłoszeniu z bazy danych.
     * 
     * @param stepByStep dokładny opis zgłoszenia
     *
     * @see #stepByStep
     * @see #getStepByStep()
     */
    public void setStepByStep(String stepByStep) {
        this.stepByStep = stepByStep;
    }

    /**
     * Zwraca użytkownika, który aktualnie zajmuje się rozwiązaniem problemu.
     *
     * @return użytkownik, który aktualnie zajmuje się rozwiązaniem problemu
     */
    public User getSaviour() {
        return saviour;
    }

    /**
     * Ustawia użytkownika, który będzie dalej zajmował się rozwiązaniem problemu.
     * 
     * @param saviour użytkownik, który dalej będzie zajmował się problemem
     *
     * @see #saviour
     * @see #getSaviour() 
     * @see User
     * @see Role
     */
    public void setSaviour(User newSaviour) {
        this.saviour = newSaviour;
    }

    /**
     * Zwraca użytkownika, który wprowadził zgłoszenie do bazy.
     * 
     * @return użytkownik,ktory zgłosił problem
     *
     * @see #inputer
     * @see User
     * @see Role
     */
    public User getInputer() {
        return inputer;
    }

    /**
     * Ustawia użytkownika, który wprowadził zgłoszenie do bazy. Metoda powinna być
     * wykorzystywana tylko podczas przepisywania danych o zgłoszeniu z bazy danych.
     *
     * @param inputer użytkownik, który wprowadził zgłoszenie do bazy
     *
     * @see #inputer
     * @see #getInputer()
     * @see User
     * @see Role
     */
    public void setInputer(User inputer) {
        this.inputer = inputer;
    }

    /**
     * Zwraca użytkownika, który powiadomił o problemie. Jeśli użytkownik samodzielnie
     * wprowadza za pomocą interfejsu www zgłoszenie, to wartość zwraca przez tę metodę,
     * jest taka sama jak dla metody {@link #getInputer()}.
     * 
     * @return użytkownik, który powiadomił o problemie
     *
     * @see #notifier
     * @see User
     * @see Role
     */
    public User getNotifier() {
        return notifier;
    }

    /**
     * Ustawia użytkownika, który powiadomił o problemie. Metoda powinna być
     * wykorzystywana tylko podczas przepisywania danych o zgłoszeniu z bazy danych.
     * 
     * @param notifier użytkownik, który powiadomił o problemie
     *
     * @see #notifier
     * @see #getNotifier() 
     * @see User
     * @see Role
     */
    public void setNotifier(User notifier) {
        this.notifier = notifier;
    }

    /**
     * Zwraca wszystie komentarze dotyczące zgłoszenia.
     *
     * @return kolekcja komentarzy dotyczących zgłoszenia
     *
     * @see #comments
     * @see TicketComment
     */
    public Set<TicketComment> getComments() {
        return comments;
    }

    /**
     * Ustawia kolekcję komentarzy dotyczących zgłoszenia. Metoda powinna być
     * wykorzystywana tylko podczas przepisywania danych o zgłoszeniu z bazy danych.
     * 
     * @param comments kolekcja komentarzy do zgłoszenia
     *
     * @see #comments
     * @see #getComments()
     * @see TicketComment
     */
    public void setComments(Set<TicketComment> comments) {
        this.comments = comments;
    }

    /**
     * Zwraca kolekcję zdarzeń związanych ze zgłoszeniem.
     *
     * @return kolekcja zdarzeń związanych ze zgłoszeniem
     *
     * @see #events
     * @see TicketEvent
     */
    public Set<TicketEvent> getEvents() {
        return events;
    }

    /**
     * Ustawia kolekcję zdarzeń związanych ze zgłoszeniem. Metoda powinna być
     * wykorzystywana tylko podczas przepisywania danych o zgłoszeniu z bazy danych.
     * 
     * @param events kolekcja zdarzeń związanych ze zgłoszeniem
     *
     * @see #events
     * @see #getEvents()
     * @see TicketEvent
     */
    public void setEvents(Set<TicketEvent> events) {
        this.events = events;
    }

    /**
     * Zwraca kolekcję plików dołączonych do zgłoszenia.
     * 
     * @param addFilesList kolekcja plików dołączonych do zgłoszenia
     * 
     * @see #addFilesList
     * @see AdditionalFile
     */
    public List<AdditionalFile> getAddFilesList() {
        return addFilesList;
    }

    /**
     * Ustawia kolekcję plików dołączonych do zgłoszenia. Metoda powinna być
     * wykorzystywana tylko podczas przepisywania danych o zgłoszeniu z bazy danych.
     *
     * @param addFilesList kolekcja plików związanych ze zgłoszeniem
     *
     * @see #addFilesList
     * @see #getAddFilesList()
     * @see AdditionalFile
     */
    public void setAddFilesList(List<AdditionalFile> addFilesList) {
        this.addFilesList = addFilesList;
    }

    /**
     *
     * @return
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
     * Zwraca wysłany na serwer plik w postaci obiektu {@code MultipartFile} podczas obsługi żądania.
     * Metoda zwraca jakąś wartość tylko podczas obsługi żądania i tylko wtedy, gdy został wysłany jakiś plik.
     * W każdej innej sytuacji Zwraca {@code null}. Dostęp do plików dołączonych do zgłoszenia odbywa się
     * za pomocą metody {@link #getAddFilesList()}.<br/>
     * 
     * <font color="red">TODO: wywalić, obsluga uploadu obsługiwana oddzielną encją.</font>
     *
     * @deprecated
     *
     * @return wysłany na serwer plik
     *
     * @see #uploadedFile
     * @see MultipartFile
     * @see #getAddFilesList() 
     */
    public MultipartFile getUploadedFile() {
        return uploadedFile;
    }

    /**
     * Ustawia <i>uploadowany</i> plik w obiekcie zgłoszenia.<br/>
     *
     * <font color="red">TODO: wywalić, obsluga uploadu obsługiwana oddzielną encją.</font>
     *
     * @deprecated
     *
     * @param uploadedFile plik do przechowania
     */
    public void setUploadedFile(MultipartFile uploadedFile) {
        this.uploadedFile = uploadedFile;
    }

    public void addComment(TicketComment comm) {
        this.comments.add(comm);
    }

    @PrePersist
    protected void populateTicketEnumsDB() {
        this.ticketStatusAsInt = this.ticketStatus.toInt();
        this.ticketPriorityAsInt = this.ticketPriority.toInt();
    }

    @PostLoad
    protected void populateTicketEnumsTransient() {
        this.ticketStatus = TicketStatus.fromInt(this.ticketStatusAsInt);
        this.ticketPriority = TicketPriority.fromInt(this.ticketPriorityAsInt);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ID:       ").append(this.ticketId).append("\n");
        sb.append("CREATED:  ").append(this.createDate).append("\n");
        sb.append("SUBJECT:  ").append(this.subject).append("\n");
        sb.append("PRIORITY: ").append(this.ticketPriority).append("\n");
        sb.append("STATUS:   ").append(this.ticketStatus).append("\n");
        return sb.toString();
    }
}
