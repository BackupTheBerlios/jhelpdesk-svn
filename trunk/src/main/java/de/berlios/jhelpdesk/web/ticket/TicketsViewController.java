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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
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

import de.berlios.jhelpdesk.dao.TicketCategoryDAO;
import de.berlios.jhelpdesk.dao.TicketDAO;
import de.berlios.jhelpdesk.dao.UserDAO;
import de.berlios.jhelpdesk.model.Role;
import de.berlios.jhelpdesk.model.TicketPriority;
import de.berlios.jhelpdesk.model.TicketStatus;
import de.berlios.jhelpdesk.web.commons.PagingParamsEncoder;
import de.berlios.jhelpdesk.web.form.ShowTicketsFilterForm;

public class TicketsViewController extends SimpleFormController {

	private static Log log = LogFactory.getLog(TicketsViewController.class);
	private static int PAGE_SIZE = 25;

    @Autowired
	private TicketDAO ticketDao;

    @Autowired
	private TicketCategoryDAO ticketCategoryDAO;

    @Autowired
	private UserDAO userDAO;

	private SimpleDateFormat dateFormat;

	private ShowTicketsFilterForm filterForm;
	private Map<String, Object> refData;

	@Override
	protected ModelAndView showForm(HttpServletRequest request, HttpServletResponse arg1, BindException arg2)
			throws Exception {
		log.info("showForm()->start");
		if (request.getParameter("format") != null && request.getParameter("format").equalsIgnoreCase("pdf")) {
			ModelAndView pdfMaV = new ModelAndView("full-list-pdf");
			pdfMaV.addObject("tickets", ticketDao.getTicketsWithFilter(filterForm, Integer.MAX_VALUE, 0));
			return pdfMaV;
		}
		return super.showForm(request, arg1, arg2);
	}

	@Override
	protected void initBinder(HttpServletRequest req, ServletRequestDataBinder binder) {
		log.info("initBinder()->start");
		dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
		log.info(" initBinder()->end");
	}

	/**
	 * tutaj chyba trzeba zapakowa� do view zestaw wszystkich interesujacych zgłoszeń przynajmniej na poczatek bo potem
	 * w onSubmit bedziemy robic to jeszcze raz
	 */
	@Override
	@SuppressWarnings("unchecked")
	protected Map referenceData(HttpServletRequest request) throws ServletException {
		log.info("referenceData()->start");
        try {
            if (refData == null) {
                refData = new HashMap();
                refData.put("categories", ticketCategoryDAO.getAllCategoriesForView());
                refData.put("priorities", TicketPriority.values());
                refData.put("statuses", TicketStatus.getAllStatuses());
                refData.put("users", userDAO.getAllUser());
                refData.put("saviours", userDAO.getByRole(Role.TICKETKILLER));
                if (filterForm != null) {
                    PagingParamsEncoder enc = new PagingParamsEncoder("ticketsIterator", "b_status", request, PAGE_SIZE);
                    refData.put("ticketsListSize", ticketDao.countTicketsWithFilter(filterForm));
                    refData.put("tickets", ticketDao.getTicketsWithFilter(filterForm, PAGE_SIZE, enc.getOffset()));
                    log.debug("getTicketsWithFilter()");
                } else {
                    refData.put("tickets", ticketDao.getAllTickets());
                    log.debug("getAllTicketsAsBean()");
                }
            }
            return refData;
        } catch(Exception ex) {
            throw new ServletException(ex);
        }
	}

	/**
	 *
	*/
	@Override
	protected void onBind(HttpServletRequest req, Object command) {
		log.info("onBind()->start ");
		filterForm = (ShowTicketsFilterForm) command;

		filterForm.setTicketCategoryDAO(ticketCategoryDAO);
		filterForm.setUserDAO(userDAO);

		prepareDate(req);
		filterForm.setStatusesFromRequest(req);
		filterForm.setPrioritiesFromRequest(req);
		filterForm.setCategoriesFromRequest(req);
		filterForm.setSavioursFromRequest(req);
		filterForm.setNotifyiersFromRequest(req);
		log.info("onBind()->end ");
	}

	/**
	 * 
	*/
	@Override
	protected Object formBackingObject(HttpServletRequest req) throws ServletException {
		log.info("formBackingObject()->start");
		if (filterForm == null) {
			filterForm = new ShowTicketsFilterForm();
			filterForm.setTicketCategoryDAO(ticketCategoryDAO);
			filterForm.setUserDAO(userDAO);
		}
		prepareDate(req);
		filterForm.setStatusesFromRequest(req);
		filterForm.setPrioritiesFromRequest(req);
		filterForm.setCategoriesFromRequest(req);
		filterForm.setSavioursFromRequest(req);
		filterForm.setNotifyiersFromRequest(req);
		return filterForm;
	}

	/**
	 * 
	*/
	@Override
	@SuppressWarnings("unchecked")
	protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse res, Object command,
			BindException errors) throws Exception {
		log.info("onSubmit()->start");

		ModelAndView mav = new ModelAndView("ticketsList");

		if (refData == null) {
			refData = new HashMap();
			refData.put("categories", ticketCategoryDAO.getAllCategoriesForView());
			refData.put("priorities", TicketPriority.values());
			refData.put("statuses", TicketStatus.getAllStatuses());
			refData.put("users", userDAO.getAllUser());
			refData.put("saviours", userDAO.getByRole(Role.TICKETKILLER));
			if (filterForm != null) {
				PagingParamsEncoder enc = new PagingParamsEncoder("ticketsIterator", "b_status", request, PAGE_SIZE);
				refData.put("ticketsListSize", ticketDao.countTicketsWithFilter(filterForm));
				refData.put("tickets", ticketDao.getTicketsWithFilter(filterForm, PAGE_SIZE, enc.getOffset()));
			} else
				refData.put("tickets", ticketDao.getAllTickets());
		}
		refData.put("filterForm", command);
		mav.addAllObjects(refData);
		return mav;
	}

	/**
	 * 
	 * @param req
	 */
	private void prepareDate(HttpServletRequest req) {
		String[] startDate_ = req.getParameterValues("startDate");
		if (startDate_ != null) {
			filterForm.setStartDate(startDate_[startDate_.length - 1]);
		} else {
			filterForm.setStartDate(null);
		}

		String[] endDate_ = req.getParameterValues("endDate");
		if (endDate_ != null) {
			filterForm.setEndDate(endDate_[endDate_.length - 1]);
		} else {
			filterForm.setEndDate(null);
		}
	}
}
