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
package de.berlios.jhelpdesk.web.ticketkiller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import de.berlios.jhelpdesk.dao.DataAccessException;
import de.berlios.jhelpdesk.dao.TicketCategoryDAO;
import de.berlios.jhelpdesk.dao.TicketDAO;
import de.berlios.jhelpdesk.dao.UserDAO;
import de.berlios.jhelpdesk.model.TicketPriority;
import de.berlios.jhelpdesk.model.TicketStatus;
import de.berlios.jhelpdesk.model.User;
import de.berlios.jhelpdesk.web.commons.PagingParamsEncoder;
import de.berlios.jhelpdesk.web.form.ShowTicketsNewFilterForm;
import de.berlios.jhelpdesk.web.form.ShowTicketsFilterForm;

public class TicketsNewViewController extends SimpleFormController {

	private static Log log = LogFactory.getLog(TicketsNewViewController.class);
	private static int PAGE_SIZE = 25;
	private Map<String, Object> refData;
	private SimpleDateFormat dateFormat;
	private ShowTicketsNewFilterForm filterForm;

    @Autowired
	private TicketDAO ticketDao;

    @Autowired
	private TicketCategoryDAO ticketCategoryDAO;

    @Autowired
	private UserDAO userDAO;

    @Override
	protected void initBinder(HttpServletRequest req, ServletRequestDataBinder binder) {
		log.info("initBinder()->start");
		log.info("w new userId => " + ((User) (req.getSession()).getAttribute("user")).getUserId());
		dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
		log.info(" initBinder()->end");
	}

	@SuppressWarnings("unchecked")
    @Override
	protected Map referenceData(HttpServletRequest request) throws ServletException {
        try {
            if (refData == null) {
                refData = new HashMap();
                refData.put("categories", ticketCategoryDAO.getAllCategoriesForView());
                refData.put("priorities", TicketPriority.values());
                refData.put("users", userDAO.getAllUser());
                refData.put("tickets", ticketDao.getTicketsByStatus(TicketStatus.NOTIFIED));

                if (filterForm != null) {
                    ShowTicketsFilterForm ff = new ShowTicketsFilterForm();
                    ff.setCategories(filterForm.getCategories());
                    if (filterForm.getStartDate() != null)
                        ff.setStartDate(dateFormat.format(filterForm.getStartDate()));
                    if (filterForm.getEndDate() != null)
                        ff.setEndDate(dateFormat.format(filterForm.getEndDate()));
                    ff.setPriorities(filterForm.getPriorities());
                    List<TicketStatus> st = new ArrayList<TicketStatus>();
                    st.add(TicketStatus.NOTIFIED);
                    ff.setStatuses(st);
                    PagingParamsEncoder enc = new PagingParamsEncoder("ticketsIterator", "p_id", request, PAGE_SIZE);
                    refData.put("ticketsListSize", ticketDao.countTicketsWithFilter(ff));
                    refData.put("tickets", ticketDao.getTicketsWithFilter(ff, PAGE_SIZE, enc.getOffset()));
                } else {
                    refData.put("tickets", ticketDao.getAllTickets());
                }
            }
            return refData;
        } catch(DataAccessException ex) {
            throw new ServletException(ex);
        }
	}

    @Override
	protected void onBind(HttpServletRequest req, Object command) {
		filterForm = (ShowTicketsNewFilterForm) command;

		filterForm.setTicketCategoryDAO(ticketCategoryDAO);
		filterForm.setUserDAO(userDAO);
		// prepareDate( req );
		filterForm.setPrioritiesFromRequest(req);
		filterForm.setCategoriesFromRequest(req);
		filterForm.setNotifyiersFromRequest(req);
	}

    @Override
	protected Object formBackingObject(HttpServletRequest req) throws ServletException {
		if (filterForm == null) {
			filterForm = new ShowTicketsNewFilterForm();
			filterForm.setTicketCategoryDAO(ticketCategoryDAO);
			filterForm.setUserDAO(userDAO);
		}
		// prepareDate(req);
		filterForm.setPrioritiesFromRequest(req);
		filterForm.setCategoriesFromRequest(req);
		filterForm.setNotifyiersFromRequest(req);
		return filterForm;
	}

	@SuppressWarnings("unchecked")
    @Override
	protected ModelAndView onSubmit(HttpServletRequest req, HttpServletResponse res, Object command,
			BindException errors) throws Exception {
		ModelAndView mav = new ModelAndView("ticketsNewList");

		ShowTicketsFilterForm ff = new ShowTicketsFilterForm();
		if (refData == null) {
			refData = new HashMap();
			refData.put("categories", ticketCategoryDAO.getAllCategoriesForView());
			refData.put("priorities", TicketPriority.values());
			refData.put("users", userDAO.getAllUser());
			if (filterForm != null) {
				ff.setCategories(filterForm.getCategories());
				if (filterForm.getStartDate() != null)
					ff.setStartDate(dateFormat.format(filterForm.getStartDate()));
				if (filterForm.getEndDate() != null)
					ff.setEndDate(dateFormat.format(filterForm.getEndDate()));
				ff.setPriorities(filterForm.getPriorities());
				ff.setNotifyiers(filterForm.getNotifyiers());
				List<TicketStatus> st = new ArrayList<TicketStatus>();
				st.add(TicketStatus.NOTIFIED);
				ff.setStatuses(st);
				PagingParamsEncoder enc = new PagingParamsEncoder("ticketsIterator", "p_id", req, PAGE_SIZE);
				refData.put("ticketsListSize", ticketDao.countTicketsWithFilter(ff));
				refData.put("tickets", ticketDao.getTicketsWithFilter(ff, PAGE_SIZE, enc.getOffset()));
			} else {
				refData.put("tickets", ticketDao.getAllTickets());
			}
		}
		refData.put("filterForm", command);
		mav.addAllObjects(refData);

		return mav;
	}

}
