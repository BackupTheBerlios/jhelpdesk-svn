package de.berlios.jhelpdesk.web.ticket;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;

import de.berlios.jhelpdesk.dao.TicketCategoryDAO;
import de.berlios.jhelpdesk.dao.UserDAO;
import de.berlios.jhelpdesk.model.Ticket;
import de.berlios.jhelpdesk.model.TicketCategory;
import de.berlios.jhelpdesk.model.TicketPriority;
import de.berlios.jhelpdesk.model.TicketStatus;
import de.berlios.jhelpdesk.model.User;
import de.berlios.jhelpdesk.web.tools.TicketValidator;
import de.berlios.jhelpdesk.web.tools.UserEditor;

/**
 *
 * @author jjhop
 */
@Controller
@SessionAttributes({"hdticket", "notifier"})
public class TicketWizardFormControllerNew {

    @Autowired
    @Qualifier("jdbc")
    private UserDAO userDAO;

    @Autowired
    private TicketCategoryDAO ticketCategoryDAO;

    @Autowired
    private TicketValidator validator;

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    private String fileRepositoryPath = "c:\\helpdesk\\files\\";

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        NumberFormat nf = NumberFormat.getNumberInstance();
        binder.registerCustomEditor(Long.class, null, new CustomNumberEditor(Long.class, nf, true));
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
        binder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
        UserEditor userEditor = new UserEditor();
        userEditor.setUserDAO(this.userDAO);
        binder.registerCustomEditor(User.class, userEditor);
    }

    // TODO: do usunięcia, gdy całkowicie zaimplementujemy nowy kontroler
    @ModelAttribute("formURL")
    public String populateFormTarget() {
        return "/newTicketWizzard.html";
    }

    @ModelAttribute("categories")
    public List<TicketCategory> populateTicketCategories() {
        return ticketCategoryDAO.getAllCategoriesForView();
    }

    @ModelAttribute("statuses")
    public List<TicketStatus> populateStatuses() {
        return Arrays.asList(TicketStatus.getAllStatuses());
    }

    @ModelAttribute("priorities")
    public List<TicketPriority> populatePriorities() {
        return Arrays.asList(TicketPriority.values());
    }

    @RequestMapping(value = "/newTicketWizzard.html", method = RequestMethod.GET)
    public String prepareForm(ModelMap map) {
        map.addAttribute("hdticket", new Ticket());
        return "ticketWizard/step1";
    }

    @RequestMapping(value = "/newTicketWizzard.html", method = RequestMethod.POST)
    public String processSubmit(
                  HttpSession session,
                  @RequestParam(value = "checkLogin", required = false) String checkLogin,
                  @RequestParam(value = "_target1", required = false) String target1,
                  @RequestParam(value = "_target2", required = false) String target2,
                  @RequestParam(value = "_target3", required = false) String target3,
                  @RequestParam(value = "notifier", required = false) String notifierString,
                  @RequestParam(value = "notifier", required = false) User notifier,
                  @ModelAttribute("hdticket") Ticket ticket,
                  BindingResult result, SessionStatus status,
                  ModelMap map) {

        map.addAttribute("hdticket", ticket);
        
        // jesli tylko sprawdzanie człowieka to jesli taki jest - wrzucamy go do modelu jak "notifier"
        // jesli jest to pierwszy krok to sprawdzamy czlowieka i jesli jest ok to lecimy dalej
        // w przeciwnym wypadku wracamy na pierwszą stronę i informujemy o problemie
        // jesli to drugi krok to walidujemy
        if ("ok".equalsIgnoreCase(checkLogin) || StringUtils.isNotBlank(target1)) {
            if (!checkNotifier(notifier, notifierString, result)) {
                return "ticketWizard/step1";
            }
            if (notifier != null) {
                map.addAttribute("notifier", notifier);
            }
            if (StringUtils.isNotBlank(target1)) {
                return "ticketWizard/step2";
            }
            return "ticketWizard/step1";
        }
       
        // itd...

        return "ticketWizard/step2";
    }

    /**
     * Zwraca false jeśli nie udało sie poprawnie zweryfikowac użytkownika. Odpowiedni komunikat
     * walidacji jest w obiekcie {@code result}.
     * 
     * @param notifier
     * @param notifierString
     * @param result
     * @return
     */
    private boolean checkNotifier(User notifier, String notifierString, BindingResult result) {
        boolean res = true;
        if (notifierString == null || "".equalsIgnoreCase(notifierString)) {
            result.rejectValue("notifier", "ticket.notifier.notEmpty");
            res = false;
        } else if (notifier == null) {
            result.rejectValue("notifier", "ticket.notifier.notExists");
            res = false;
        }
        return res;
    }
}
