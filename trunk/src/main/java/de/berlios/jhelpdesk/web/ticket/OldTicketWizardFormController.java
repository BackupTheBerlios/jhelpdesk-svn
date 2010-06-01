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
package de.berlios.jhelpdesk.web.ticket;

import java.io.File;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractWizardFormController;
import org.springframework.web.servlet.view.RedirectView;

import de.berlios.jhelpdesk.dao.TicketCategoryDAO;
import de.berlios.jhelpdesk.dao.TicketDAO;
import de.berlios.jhelpdesk.dao.UserDAO;
import de.berlios.jhelpdesk.model.AdditionalFile;
import de.berlios.jhelpdesk.model.Role;
import de.berlios.jhelpdesk.model.Ticket;
import de.berlios.jhelpdesk.model.TicketCategory;
import de.berlios.jhelpdesk.model.TicketPriority;
import de.berlios.jhelpdesk.model.TicketStatus;
import de.berlios.jhelpdesk.model.User;
import de.berlios.jhelpdesk.web.tools.UserEditor;

@Controller
@RequestMapping("/newTicket.html")
public class OldTicketWizardFormController extends AbstractWizardFormController {

    @Autowired
    private TicketDAO ticketDaoJpa;

    @Autowired
    private TicketCategoryDAO ticketCategoryDAO;
    
    @Autowired
    private UserDAO userDAO;

    @Autowired
    private UserEditor userEditor;

    private String fileRepositoryPath = "c:\\helpdesk\\files\\";

    public OldTicketWizardFormController() {
        setCommandClass(Ticket.class);
        setCommandName("hdticket");
        setPages(
            new String[]{
                "ticketWizard/step1",
                "ticketWizard/step2",
                "ticketWizard/step3",
                "ticketWizard/step4",
                "ticketWizard/summary"
            }
        );
    }

    @Override
    protected int getTargetPage(HttpServletRequest request, int currentPage) {
        return super.getTargetPage(request, currentPage);
    }

    @Override
    protected void initBinder(HttpServletRequest req, ServletRequestDataBinder binder) {
        NumberFormat nf = NumberFormat.getNumberInstance();
        binder.registerCustomEditor(Long.class, null, new CustomNumberEditor(Long.class, nf, true));
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
        binder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
        binder.registerCustomEditor(User.class, userEditor);
    }

    @Override
    protected void validatePage(Object command, Errors errors, int page, boolean finish) {
        Ticket ticket = (Ticket) command;
        setAllowDirtyBack(true);
        if (page > 0) {
            if (ticket.getSubject() == null || ticket.getSubject().trim().length() < 1) {
                errors.rejectValue("subject", "hdticket.subject.empty");
            }
            if (ticket.getDescription() == null || ticket.getDescription().trim().length() < 1) {
                errors.rejectValue("description", "hdticket.description.empty");
            }
        }
    }

    @Override
    protected void onBindAndValidate(HttpServletRequest request, Object command,
            BindException bindErrors, int page) throws Exception {
        Ticket ticket = (Ticket) command;
        if ((request.getParameter("_checkLogin") != null) && (ticket.getNotifier() == null)) {
            bindErrors.rejectValue("notifier", "hdticket.notifier.notExists",
                    new Object[]{request.getParameter("notifier")}, "defaultMessage");
        } else if (ticket.getNotifier() == null) {
            bindErrors.rejectValue("notifier", "hdticket.notifier.notEmpty");
        }

        if ((ticket.getUploadedFile() != null) && (!ticket.getUploadedFile().isEmpty())
                && (ticket.getUploadedFile().getOriginalFilename().length() > 0)) {

            MultipartFile file = ticket.getUploadedFile();
            AdditionalFile addFile = new AdditionalFile();
            addFile.setContentType(file.getContentType());
            addFile.setFileDate(file.getBytes());
            addFile.setFileSize(file.getSize());
            addFile.setOriginalFileName(file.getOriginalFilename());

            ticket.getAddFilesList().add(addFile);
            System.out.println("");
            System.out.println("Filename => " + file.getName() +
                               "OriginalFilename => " + file.getOriginalFilename() +
                               "ContentType => " + file.getContentType() +
                               "Size => " + file.getSize());
            System.out.println("Files num => " + ticket.getAddFilesList().size());
        }
    }

    @Override
    protected ModelAndView processFinish(HttpServletRequest request, HttpServletResponse response,
            Object command, BindException errors) throws Exception {

        File repository = new File(fileRepositoryPath);

        Ticket ticket = (Ticket) command;
        // najpierw zapisujemy zgłoszenie w bazie danych
        ticket.setCreateDate(new Date(System.currentTimeMillis()));
        ticket.setTicketPriority(TicketPriority.fromInt(1));
        ticket.setTicketStatus(TicketStatus.NOTIFIED);
        ticket.setInputer((User) request.getSession().getAttribute("user"));

        ticket.setTicketCategory(new TicketCategory(1, null)); // TODO: jakoś to słabo wygląda...

        ticketDaoJpa.save(ticket);
        File thisTicketRepository =
                new File(
                new StringBuffer(repository.getAbsolutePath()).append(File.separatorChar).append(ticket.getTicketId()).toString());
        if (thisTicketRepository.mkdir()) {
            for (AdditionalFile addFile : ticket.getAddFilesList()) {
                FileCopyUtils.copy(
                        addFile.getFileData(),
                        new File(
                            new StringBuffer(thisTicketRepository.getAbsolutePath())
                                .append(File.separatorChar)
                                .append(addFile.getOriginalFileName()).toString()));
            }
        }
        return new ModelAndView(new RedirectView("/tickets/" + ticket.getTicketId() + "/details.html", true));
    }

    protected Map referenceData(HttpServletRequest request) throws ServletException {
        Map refData = new HashMap();
        refData.put("categories", ticketCategoryDAO.getAllCategoriesForView());
        refData.put("priorities", TicketPriority.values());
        refData.put("statuses", TicketStatus.getAllStatuses());
        refData.put("users", userDAO.getAllUsers());
        refData.put("saviours", userDAO.getByRole(Role.TICKETKILLER));
        return refData;
    }
}
