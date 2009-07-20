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
import de.berlios.jhelpdesk.web.form.ShowTicketsFilterForm;
import de.berlios.jhelpdesk.web.form.ShowTicketsNotifiedByMeFilterForm;

public class TicketsNotifiedByMeViewController extends SimpleFormController {
	
	private static Log log = LogFactory.getLog(TicketsNotifiedByMeViewController.class);
	private static int PAGE_SIZE = 25;

	private SimpleDateFormat dateFormat;
	private ShowTicketsNotifiedByMeFilterForm filterForm;

    @Autowired
    private TicketDAO ticketDao;

    @Autowired
    private TicketCategoryDAO ticketCategoryDAO;

    @Autowired
    private UserDAO userDAO;

	private Map<String, Object> refData;

    @Override
	protected void initBinder(HttpServletRequest req, ServletRequestDataBinder binder) {
		log.info("initBinder()->start");
		dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
		log.info(" initBinder()->end");
	}

	/**
	 *
	 */
    @Override
	protected void onBind(HttpServletRequest req, Object command) {
		log.info("onBind()->start ");
		filterForm = (ShowTicketsNotifiedByMeFilterForm) command;

		filterForm.setTicketCategoryDAO(ticketCategoryDAO);
		filterForm.setUserDAO(userDAO);

		// prepareDate( req );

		filterForm.setStatusesFromRequest(req);
		filterForm.setPrioritiesFromRequest(req);
		filterForm.setCategoriesFromRequest(req);
		filterForm.setSavioursFromRequest(req);

		log.info("onBind()->end ");
	}

	/**
	 * tutaj chyba trzeba zapakowa� do view zestaw wszystkich interesujacych zg�osze� przynajmniej na poczatek bo potem
	 * w onSubmit bedziemy robic to jeszcze raz
	 */
	@SuppressWarnings("unchecked")
    @Override
	protected Map referenceData(HttpServletRequest request) throws ServletException {
        try {
            if (refData == null) {
                refData = new HashMap();
                refData.put("categories", ticketCategoryDAO.getAllCategoriesForView());
                refData.put("priorities", TicketPriority.values());
                refData.put("statuses", TicketStatus.getAllStatuses());
                refData.put("users", userDAO.getAllUser());
                refData.put("saviours", userDAO.getSaviours());

                if (filterForm != null) {
                    ShowTicketsFilterForm ff = new ShowTicketsFilterForm();
                    ff.setCategories(filterForm.getCategories());
                    if (filterForm.getStartDate() != null)
                        ff.setStartDate(dateFormat.format(filterForm.getStartDate()));
                    if (filterForm.getEndDate() != null)
                        ff.setEndDate(dateFormat.format(filterForm.getEndDate()));
                    List<User> n = new ArrayList<User>();
                    n.add((User) request.getSession().getAttribute("user"));
                    ff.setNotifyiers(n);
                    ff.setPriorities(filterForm.getPriorities());
                    ff.setSaviours(filterForm.getSaviours());
                    ff.setStatuses(filterForm.getStatuses());
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

	/**
	 * 
	 */
	@SuppressWarnings("unchecked")
    @Override
	protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse res, Object command,
			BindException errors) throws Exception {
		log.info("onSubmit()->start");
		ModelAndView mav = new ModelAndView("ticketsNotifiedByMeList");
		ShowTicketsFilterForm ff = new ShowTicketsFilterForm();
		if (refData == null) {
			refData = new HashMap();
			refData.put("categories", ticketCategoryDAO.getAllCategoriesForView());
			refData.put("priorities", TicketPriority.values());
			refData.put("statuses", TicketStatus.getAllStatuses());
			refData.put("users", userDAO.getAllUser());
			refData.put("saviours", userDAO.getSaviours());
			if (filterForm != null) {
				// refData.put( "tickets", ticketDao.getTicketsWithFilter( filterForm ) );
				ff.setCategories(filterForm.getCategories());
				if (filterForm.getStartDate() != null)
					ff.setStartDate(dateFormat.format(filterForm.getStartDate()));
				if (filterForm.getEndDate() != null)
					ff.setEndDate(dateFormat.format(filterForm.getEndDate()));
				List<User> n = new ArrayList<User>();
				n.add((User) request.getSession().getAttribute("user"));
				ff.setNotifyiers(n);
				ff.setPriorities(filterForm.getPriorities());
				// ff.setSaviours( filterForm.getSaviours() );
				ff.setStatuses(filterForm.getStatuses());
				PagingParamsEncoder enc = new PagingParamsEncoder("ticketsIterator", "p_id", request, PAGE_SIZE);
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

	/**
	 * 
	 */
    @Override
	protected Object formBackingObject(HttpServletRequest req) throws ServletException {
		if (filterForm == null) {
			filterForm = new ShowTicketsNotifiedByMeFilterForm();
			filterForm.setTicketCategoryDAO(ticketCategoryDAO);
			filterForm.setUserDAO(userDAO);
		}
		// prepareDate( req );
		filterForm.setStatusesFromRequest(req);
		filterForm.setPrioritiesFromRequest(req);
		filterForm.setCategoriesFromRequest(req);
		filterForm.setSavioursFromRequest(req);
		return filterForm;
	}

}
