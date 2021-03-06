package com.adiaz.controllers;

import com.adiaz.entities.Club;
import com.adiaz.entities.Team;
import com.adiaz.forms.TeamFilterForm;
import com.adiaz.forms.TeamForm;
import com.adiaz.forms.validators.TeamFormValidator;
import com.adiaz.services.ClubManager;
import com.adiaz.services.TeamManager;
import com.adiaz.utils.teamlist.TeamListEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

import static com.adiaz.utils.LocalSportsUtils.getActiveUser;

/**
 * Created by toni on 25/07/2017.
 */
@Controller
@RequestMapping("/team")
@SessionAttributes({"team_form_filter", "club_list"})
public class TeamController {

    private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(TeamController.class);

    @Autowired
    TeamManager teamManager;

    @Autowired
    TeamFormValidator teamFormValidator;

    @Autowired
    ClubManager clubManager;

    @ModelAttribute("club_list")
    public List<Club> addClubList() {
        List<Club> clubs;
        if (!getActiveUser().isAdmin()) {
            clubs = clubManager.queryByTownId(getActiveUser().getTownEntity().getId());
        } else {
            clubs = clubManager.queryAll();

        }
        return clubs;
    }

    @RequestMapping("/list")
    public ModelAndView list() {
        ModelAndView modelAndView = new ModelAndView("team_list");
        TeamFilterForm teamFilterForm = new TeamFilterForm();
        if (!getActiveUser().isAdmin()) {
            teamFilterForm.setIdTown(getActiveUser().getTownEntity().getId());
        }
        modelAndView.addObject("team_form_filter", teamFilterForm);
        return modelAndView;
    }

    @RequestMapping("/doFilter")
    public ModelAndView doFilter(
            @ModelAttribute("team_form_filter") TeamFilterForm filterForm,
            @RequestParam(value = "update_done", defaultValue = "false") boolean updateDone,
            @RequestParam(value = "add_done", defaultValue = "false") boolean addDone,
            @RequestParam(value = "delete_done", defaultValue = "false") boolean deleteDone,
            @RequestParam(value = "delete_undone", defaultValue = "false") boolean deleteUndone) {
        ModelAndView modelAndView = new ModelAndView("team_list");
        List<Team> teams = teamManager.queryByFilter(filterForm);
        List<TeamListEntity> teamsList = new ArrayList<>();
        for (Team team : teams) {
            TeamListEntity teamListEntity = new TeamListEntity(team, teamManager.isElegibleForDelete(team.getId()));
            teamsList.add(teamListEntity);
        }
        modelAndView.addObject("teamList", teamsList);
        modelAndView.addObject("add_done", addDone);
        modelAndView.addObject("update_done", updateDone);
        modelAndView.addObject("delete_done", deleteDone);
        modelAndView.addObject("delete_undone", deleteUndone);
        return modelAndView;
    }

    @RequestMapping("/add")
    public ModelAndView add() {
        ModelAndView modelAndView = new ModelAndView("team_add");
        modelAndView.addObject("my_form", new TeamForm());
        return modelAndView;
    }

    @RequestMapping("/doAdd")
    public ModelAndView doAdd(@ModelAttribute("my_form") TeamForm teamForm, BindingResult bindingResult) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        teamFormValidator.validate(teamForm, bindingResult);
        if (bindingResult.hasErrors()) {
            modelAndView.addObject("my_form", teamForm);
            modelAndView.setViewName("team_add");
        } else {
            if (!getActiveUser().isAdmin()) {
                teamForm.setIdTown(getActiveUser().getTownEntity().getId());
            }
            teamManager.add(teamForm);
            String viewName = "redirect:/team/doFilter?add_done=true";
            modelAndView.setViewName(viewName);
        }
        return modelAndView;
    }

    @RequestMapping("/update")
    public ModelAndView update(@RequestParam Long id) {
        ModelAndView modelAndView = new ModelAndView("team_update");
        boolean elegibleForDelete = teamManager.isElegibleForDelete(id);
        modelAndView.addObject("my_form", teamManager.queryById(id));
        modelAndView.addObject("elegibleForDelete", elegibleForDelete);
        return modelAndView;
    }

    @RequestMapping("/doUpdate")
    public ModelAndView doUpdate(@ModelAttribute("my_form") TeamForm teamForm, BindingResult bindingResult) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        teamFormValidator.validate(teamForm, bindingResult);
        if (bindingResult.hasErrors()) {
            modelAndView.addObject("my_form", teamForm);
            modelAndView.setViewName("team_update");
        } else {
            if (!getActiveUser().isAdmin()) {
                teamForm.setIdTown(getActiveUser().getTownEntity().getId());
            }
            teamManager.update(teamForm);
            String viewName = "redirect:/team/doFilter?update_done=true";
            modelAndView.setViewName(viewName);
        }
        return modelAndView;
    }

    @RequestMapping("/doDelete")
    public String doDelete(@RequestParam Long id) throws Exception {
        // TODO: 25/07/2017 validate user is admin or the town of the town is the same than the user.
        if (teamManager.isElegibleForDelete(id)) {
            teamManager.remove(id);
            return "redirect:/team/doFilter?delete_done=true";
        } else {
            return "redirect:/team/doFilter?delete_undone=true";
        }
    }

    @RequestMapping("/view")
    public ModelAndView view(@RequestParam Long id) {
        ModelAndView modelAndView = new ModelAndView("team_view");
        TeamForm teamForm = teamManager.queryById(id);
        modelAndView.addObject("my_form", teamForm);
        return modelAndView;
    }


}