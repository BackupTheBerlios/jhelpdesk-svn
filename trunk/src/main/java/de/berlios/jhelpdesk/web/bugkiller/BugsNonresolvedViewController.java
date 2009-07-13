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
package de.berlios.jhelpdesk.web.bugkiller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.mvc.SimpleFormController;

import de.berlios.jhelpdesk.dao.BugCategoryDAO;
import de.berlios.jhelpdesk.dao.BugDAO;
import de.berlios.jhelpdesk.dao.BugPriorityDAO;
import de.berlios.jhelpdesk.dao.BugStatusDAO;
import de.berlios.jhelpdesk.dao.UserDAO;
import de.berlios.jhelpdesk.model.BugStatus;
import de.berlios.jhelpdesk.web.commons.PagingParamsEncoder;
import de.berlios.jhelpdesk.web.form.ShowBugsFilterForm;

public class BugsNonresolvedViewController extends SimpleFormController {

	private static Log log = LogFactory.getLog(BugsNonresolvedViewController.class);
	private static int PAGE_SIZE = 25;

    @Autowired
	private BugDAO bugDao;

    @Autowired
	private BugCategoryDAO bugCategoryDAO;

    @Autowired
	private BugPriorityDAO bugPriorityDAO;

    @Autowired
	private BugStatusDAO bugStatusDAO;

    @Autowired
	private UserDAO userDAO;

	private SimpleDateFormat dateFormat;
	private ShowBugsFilterForm filterForm;
	private Map<String, Object> refData;

	// TODO: do zrobienia caly kontroler
    @Override
	protected void initBinder(HttpServletRequest req, ServletRequestDataBinder binder) {
		log.debug("initBinder()->start");
		dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
		log.debug("initBinder()->end");
	}

	@SuppressWarnings("unchecked")
    @Override
	protected Map referenceData(HttpServletRequest request) throws ServletException {
		if (refData == null) {
			refData = new HashMap();
			refData.put("categories", bugCategoryDAO.getAllCategoriesForView());
			refData.put("priorities", bugPriorityDAO.getAllPriorities());
			refData.put("statuses", bugStatusDAO.getAllStatuses());
			refData.put("users", userDAO.getAllUser());
			refData.put("saviours", userDAO.getSaviours());
			if (filterForm != null) {
				PagingParamsEncoder enc = new PagingParamsEncoder("bugsIterator", "b_status", request, PAGE_SIZE);
				refData.put("bugsListSize", bugDao.countBugsWithFilter(filterForm));
				refData.put("bugs", bugDao.getBugsWithFilter(filterForm, PAGE_SIZE, enc.getOffset()));
				log.debug("getBugsWithFilter()");
			} else {
				refData.put("bugs", bugDao.getAllBugs());
				log.debug("getAllBugsAsBean()");
			}
		}
		return refData;
	}

	/**
	 *
	*/
	@Override
	protected void onBind(HttpServletRequest req, Object command) {
		log.info("onBind()->start ");
		filterForm = (ShowBugsFilterForm) command;

		filterForm.setBugCategoryDAO(bugCategoryDAO);
		filterForm.setBugPriorityDAO(bugPriorityDAO);
		filterForm.setBugStatusDAO(bugStatusDAO);
		filterForm.setUserDAO(userDAO);

		prepareDate(req);
		List<BugStatus> statuses = new ArrayList<BugStatus>();
		statuses.add(BugStatus.ATTACHED);
		statuses.add(BugStatus.NOTIFIED);
		filterForm.setStatuses(statuses);
		filterForm.setPrioritiesFromRequest(req);
		filterForm.setCategoriesFromRequest(req);
		filterForm.setSavioursFromRequest(req);
		filterForm.setNotifyiersFromRequest(req);
		log.info("onBind()->end ");
	}

	@Override
	protected Object formBackingObject(HttpServletRequest request) throws Exception {
		if (filterForm == null) {
			filterForm = new ShowBugsFilterForm();
			filterForm.setBugCategoryDAO(bugCategoryDAO);
			filterForm.setBugPriorityDAO(bugPriorityDAO);
			filterForm.setUserDAO(userDAO);
		}
		filterForm.setPrioritiesFromRequest(request);
		filterForm.setCategoriesFromRequest(request);
		filterForm.setNotifyiersFromRequest(request);
		return filterForm;
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
