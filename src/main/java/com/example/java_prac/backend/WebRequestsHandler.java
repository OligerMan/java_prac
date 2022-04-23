package com.example.java_prac.backend;

import com.example.java_prac.dao.DAOFactory;
import com.example.java_prac.dao.implementations.EmpPositionDAOImpl;
import com.example.java_prac.dao.implementations.EmployeeDAOImpl;
import com.example.java_prac.dao.implementations.UnitDAOImpl;
import com.example.java_prac.dao.interfaces.EmpPositionDAO;
import com.example.java_prac.dao.interfaces.EmployeeDAO;
import com.example.java_prac.dao.interfaces.UnitDAO;
import com.example.java_prac.entities.EmpPositionEntity;
import com.example.java_prac.entities.EmployeeEntity;
import com.example.java_prac.entities.UnitEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.Timestamp;
import java.util.List;

@Controller
class WebRequestsHandler {
    private final EmployeeDAO employeeDAO = DAOFactory.getInstance().getEmployeeDAO();
    private final UnitDAO unitDAO = DAOFactory.getInstance().getUnitDAO();
    private final EmpPositionDAO empPositionDAO = DAOFactory.getInstance().getEmpPositionDAO();

    @GetMapping("/main")
    public String getMainPage() {
        return "main";
    }

    @GetMapping("/divisions")
    public String getDivisionsPage() {
        return "divisions";
    }

    @GetMapping("/division_data")
    public String getDivisionDataPage() {
        return "division_data";
    }

    @GetMapping("/employees")
    public String getEmployeesPage() {
        return "employees";
    }

    @GetMapping("/employee_data")
    public String getEmployeeDataPage() { return "employee_data"; }


    @GetMapping("/update_emp_data")
    @ResponseBody
    public String updateEmployeeData(
            @RequestParam(required = false) String emp_id,
            @RequestParam(required = false) String emp_name,
            @RequestParam(required = false) String home_adr
    ) {
        EmployeeDAOImpl.Filter filter = new EmployeeDAOImpl.Filter();
        filter.setId(Long.parseLong(emp_id));
        List<EmployeeEntity> employee_list = employeeDAO.getEmployeeListByFilter(filter);
        if (employee_list.size() == 1) {
            EmployeeEntity emp = employee_list.get(0);
            emp.setEmpName(emp_name);
            emp.setHomeAddress(home_adr);
            employeeDAO.updateEmployee(emp);
        }

        return "OK";
    }

    @GetMapping("/add_division")
    @ResponseBody
    public String addDivision(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String dir_info
    ) {
        dir_info = dir_info.substring(4);
        int dir_id = Integer.parseInt(dir_info.substring(0, dir_info.indexOf(' ')));
        UnitEntity unit1 = new UnitEntity(0, dir_id, -1, name);
        unitDAO.addUnit(unit1);

        return "OK";
    }

    @GetMapping("/add_employee")
    @ResponseBody
    public String addEmployee(
            @RequestParam(required = false) String name
    ) {
        EmployeeEntity emp = new EmployeeEntity(0, "", name, -1);
        employeeDAO.addEmployee(emp);
        return "OK";
    }


    @GetMapping("/update_div_data")
    @ResponseBody
    public String updateDivisionData(
            @RequestParam(required = false) String div_id,
            @RequestParam(required = false) String div_name,
            @RequestParam(required = false) String emp_info,
            @RequestParam(required = false) String higher_info
    ) {
        emp_info = emp_info.substring(4);
        Integer emp_id = Integer.parseInt(emp_info.substring(0, emp_info.indexOf(' ')));
        higher_info = higher_info.substring(4);
        Integer higher_id = Integer.parseInt(higher_info.substring(0, higher_info.indexOf(' ')));

        UnitDAOImpl.Filter filter = new UnitDAOImpl.Filter();
        filter.setId(Long.parseLong(div_id));
        List<UnitEntity> employee_list = unitDAO.getUnitListByFilter(filter);
        if (employee_list.size() == 1) {
            UnitEntity emp = employee_list.get(0);
            emp.setDirectorId(emp_id);
            emp.setHigherUnitId(higher_id);
            emp.setUnitName(div_name);
            unitDAO.updateUnit(emp);
        }

        return "OK";
    }

    @GetMapping("/delete_emp_data")
    @ResponseBody
    public String deleteEmployeeData(
            @RequestParam(required = false) String emp_id
    ) {
        EmpPositionDAOImpl.Filter ep_filter = new EmpPositionDAOImpl.Filter();
        ep_filter.setEmpId(Long.parseLong(emp_id));
        List<EmpPositionEntity> emp_position_list = empPositionDAO.getEmpPositionListByFilter(ep_filter);
        for (EmpPositionEntity ep : emp_position_list) {
            empPositionDAO.deleteEmpPosition(ep);
        }

        UnitDAOImpl.Filter unit_filter = new UnitDAOImpl.Filter();
        unit_filter.setDirectorId(Long.parseLong(emp_id));
        List<UnitEntity> unit_list = unitDAO.getUnitListByFilter(unit_filter);
        for (UnitEntity u : unit_list) {
            ep_filter = new EmpPositionDAOImpl.Filter();
            ep_filter.setUnitId(u.getUnitId());
            emp_position_list = empPositionDAO.getEmpPositionListByFilter(ep_filter);
            for (EmpPositionEntity ep : emp_position_list) {
                empPositionDAO.deleteEmpPosition(ep);
            }

            unitDAO.deleteUnit(u);
        }

        EmployeeDAOImpl.Filter filter = new EmployeeDAOImpl.Filter();
        filter.setId(Long.parseLong(emp_id));
        List<EmployeeEntity> employee_list = employeeDAO.getEmployeeListByFilter(filter);
        if (employee_list.size() == 1) {
            employeeDAO.deleteEmployee(employee_list.get(0));
        }

        return "OK";
    }

    @GetMapping("/delete_div_data")
    @ResponseBody
    public String deleteDivisionData(
            @RequestParam(required = false) String div_id
    ) {

        EmpPositionDAOImpl.Filter ep_filter = new EmpPositionDAOImpl.Filter();
        ep_filter.setUnitId(Long.parseLong(div_id));
        List<EmpPositionEntity> ep_list = empPositionDAO.getEmpPositionListByFilter(ep_filter);
        for (EmpPositionEntity ep : ep_list) {
            empPositionDAO.deleteEmpPosition(ep);
        }

        UnitDAOImpl.Filter filter = new UnitDAOImpl.Filter();
        filter.setId(Long.parseLong(div_id));
        List<UnitEntity> employee_list = unitDAO.getUnitListByFilter(filter);
        if (employee_list.size() == 1) {
            unitDAO.deleteUnit(employee_list.get(0));
        }

        return "OK";
    }


    @GetMapping("/get_div_select_list")
    @ResponseBody
    public String getDivisionList() {
        UnitDAOImpl.Filter filter = new UnitDAOImpl.Filter();
        List<UnitEntity> unit_list = unitDAO.getUnitListByFilter(filter);

        StringBuilder output = new StringBuilder();
        for (UnitEntity unit : unit_list) {
            output.append("<option>")
                    .append("ID: ")
                    .append(unit.getUnitId())
                    .append(" ")
                    .append(unit.getUnitName())
                    .append("</option>\n");
        }
        return output.toString();
    }


    @GetMapping("/get_emp_select_list")
    @ResponseBody
    public String getEmployeeList() {
        EmployeeDAOImpl.Filter filter = new EmployeeDAOImpl.Filter();
        List<EmployeeEntity> unit_list = employeeDAO.getEmployeeListByFilter(filter);

        StringBuilder output = new StringBuilder();
        for (EmployeeEntity unit : unit_list) {
            output.append("<option>")
                    .append("ID: ")
                    .append(unit.getEmpId())
                    .append(" ")
                    .append(unit.getEmpName())
                    .append("</option>\n");
        }
        return output.toString();
    }


    @GetMapping("/add_emp_position")
    @ResponseBody
    public String addEmployeePosition(
            @RequestParam(required = false) String emp_id,
            @RequestParam(required = false) String pos_name,
            @RequestParam(required = false) String pos_desc,
            @RequestParam(required = false) String div_info
    ) {
        EmpPositionDAOImpl.Filter ep_filter = new EmpPositionDAOImpl.Filter();
        long cur_time = System.currentTimeMillis();
        ep_filter.setEmpId(Integer.parseInt(emp_id));
        List<EmpPositionEntity> ep_list = empPositionDAO.getEmpPositionListByFilter(ep_filter);
        for (EmpPositionEntity ep : ep_list) {
            if (ep.getStartTimestamp().equals(ep.getEndTimestamp())) {
                ep.setEndTimestamp(new Timestamp(cur_time));
                empPositionDAO.updateEmpPosition(ep);
            }
        }

        div_info = div_info.substring(4);
        Integer unit_id = Integer.parseInt(div_info.substring(0, div_info.indexOf(' ')));
        EmpPositionEntity emp_pos = new EmpPositionEntity
                (
                        0,
                        unit_id,
                        Integer.parseInt(emp_id),
                        pos_name,
                        pos_desc,
                        new Timestamp(cur_time),
                        new Timestamp(cur_time)
                );
        empPositionDAO.addEmpPosition(emp_pos);


        EmployeeDAOImpl.Filter emp_filter = new EmployeeDAOImpl.Filter();
        emp_filter.setId(Integer.parseInt(emp_id));
        List<EmployeeEntity> emp_list = employeeDAO.getEmployeeListByFilter(emp_filter);
        for (EmployeeEntity emp : emp_list) {
            emp.setCurrentPositionId(emp_pos.getPosId());
            employeeDAO.updateEmployee(emp);
        }
        return "OK";
    }

    @GetMapping("get_div_data")
    @ResponseBody
    public String getDivisionData(
            @RequestParam(required = false) String id){
        UnitDAOImpl.Filter filter = new UnitDAOImpl.Filter();
        if (id != null) {
            filter.setId(Long.parseLong(id));
        }
        List<UnitEntity> unit_list = unitDAO.getUnitListByFilter(filter);
        if (unit_list.size() != 1) {
            return "<h1>ID is incorrect</h1>";
        }
        UnitEntity unit = unit_list.get(0);
        StringBuilder output = new StringBuilder();
        output.append("<h2>Информация о подразделении</h2>\n");
        output.append("<table>\n");
        output.append("<tr><th></th><th></th></tr>\n");
        output.append("<tr><td>").append("ID").append("</td><td>").append(unit.getUnitId()).append("</td></tr>\n");
        output.append("<tr><td>").append("Название").append("</td><td>").append("<input id=\"div_name_input\" type=\"text\" size=\"25\" value=\"").append(unit.getUnitName()).append("\"></td></tr>\n");

        output.append("<tr><td>").append("ID руководителя").append("</td><td>").append(unit.getDirectorId()).append("</td></tr>\n");
        EmployeeDAOImpl.Filter emp_filter = new EmployeeDAOImpl.Filter();
        if (unit.getDirectorId() != -1) {
            emp_filter.setId(unit.getDirectorId());
            EmployeeEntity emp = employeeDAO.getEmployeeListByFilter(emp_filter).get(0);
            output.append("<tr><td>").append("Имя руководителя").append("</td><td><a href=\"employee_data?employee_id=").append(unit.getDirectorId()).append("\">").append(emp.getEmpName()).append("</a></td></tr>\n");
        }
        output.append("<tr><td>Выберите нового руководителя</td><td><select size=\"1\" id=\"employee_list\">").append(getEmployeeList()).append("</select></td></tr>\n");

        output.append("<tr><td>").append("ID вышестоящего подразделения").append("</td><td>").append(unit.getHigherUnitId()).append("</td></tr>\n");
        filter = new UnitDAOImpl.Filter();
        if (unit.getHigherUnitId() != -1) {
            filter.setId(unit.getHigherUnitId());
            UnitEntity higher_unit = unitDAO.getUnitListByFilter(filter).get(0);
            output.append("<tr><td>").append("Название вышестоящего подразделения").append("</td><td><a href=\"division_data?division_id=").append(unit.getHigherUnitId()).append("\">").append(higher_unit.getUnitName()).append("</a></td></tr>\n");
        }
        output.append("<tr><td>Выберите новое вышестоящее подразделение</td><td><select size=\"1\" id=\"division_list\">").append(getDivisionList()).append("</select></td></tr>\n");
        output.append("</table>");
        return output.toString();
    };

    @GetMapping("get_emp_data")
    @ResponseBody
    public String getEmployeeData(
            @RequestParam(required = false) String id){
        EmployeeDAOImpl.Filter filter = new EmployeeDAOImpl.Filter();
        if (id != null) {
            filter.setId(Long.parseLong(id));
        }
        List<EmployeeEntity> emp_list = employeeDAO.getEmployeeListByFilter(filter);
        if (emp_list.size() != 1) {
            return "<h1>ID is incorrect</h1>";
        }
        EmployeeEntity emp = emp_list.get(0);

        EmpPositionDAOImpl.Filter ep_filter = new EmpPositionDAOImpl.Filter();
        ep_filter.setEmpId(emp.getEmpId());
        List<EmpPositionEntity> ep_list = empPositionDAO.getEmpPositionListByFilter(ep_filter);
        StringBuilder ep_info = new StringBuilder();
        for (EmpPositionEntity ep : ep_list) {
            ep_info.append("<tr><td>").append(ep.getPosId()).append("</td><td>").append(ep.getPosName()).append("</td>");
            ep_info.append("<td>").append(ep.getStartTimestamp()).append("</td>");
            if (ep.getStartTimestamp().equals(ep.getEndTimestamp())) {
                ep_info.append("<td>").append("(now)").append("</td></tr>\n");
            } else {
                ep_info.append("<td>").append(ep.getEndTimestamp()).append("</td></tr>\n");
            }
        }

        StringBuilder output = new StringBuilder();
        output.append("<h2>Информация о работнике</h2>\n");
        output.append("<table>\n");
        output.append("<tr><th></th><th></th></tr>\n");
        output.append("<tr><td>").append("ID").   append("</td><td>").append(emp.getEmpId()).append("</td></tr>\n");

        output.append("<tr><td>").append("Имя").append("</td><td>").append("<input id=\"emp_name_input\" type=\"text\" size=\"25\" value=\"").append(emp.getEmpName()).append("\">").append("</td></tr>\n");
        output.append("<tr><td>").append("Адрес").append("</td><td>").append("<input id=\"home_adr_input\" type=\"text\" size=\"25\" value=\"").append(emp.getHomeAddress()).append("\">").append("</td></tr>\n");
        output.append("<tr><td>").append("ID текущей должности").append("</td><td>").append(emp.getCurrentPositionId()).append("</td></tr>\n");

        output.append("</table>");
        output.append("<h2>История должностей</h2>\n");
        output.append("<table>\n");
        output.append("<tr><th>ID</th><th>Название должности</th><th>Начало работы</th><th>Конец работы</th></tr>\n");
        output.append(ep_info);
        output.append("</table>");
        return output.toString();
    };

    @GetMapping("get_emp_list")
    @ResponseBody
    public String getEmployeesList(
            @RequestParam(required = false) String id,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String address
    ) {
        StringBuilder output = new StringBuilder();
        EmployeeDAOImpl.Filter filter = new EmployeeDAOImpl.Filter();
        if (name != null) {
            System.out.println("Added param: " + name);
            filter.setName(name);
        }
        if (id != null) {
            filter.setId(Long.parseLong(id));
        }
        List<EmployeeEntity> emp_list = employeeDAO.getEmployeeListByFilter(filter);
        output.append("<table>");
        output.append("<tr><th>ID</th><th>Имя</th><th>Адрес</th></tr>\n");
        for (EmployeeEntity ent : emp_list) {
            if (address == null || ent.getHomeAddress().contains(address)) {
                output.append("<tr><td><a href=\"employee_data?employee_id=").append(ent.getEmpId()).append("\">").append(ent.getEmpId())
                        .append("</a></td><td><a href=\"employee_data?employee_id=").append(ent.getEmpId()).append("\">").append(ent.getEmpName())
                        .append("</a></td><td><a href=\"employee_data?employee_id=").append(ent.getEmpId()).append("\">").append(ent.getHomeAddress())
                        .append("</a></td></tr>\n");
            }
        }
        output.append("</table>");
        return output.toString();
    }

    @GetMapping("get_div_list")
    @ResponseBody
    public String getDivisionsList(
            @RequestParam(required = false) String id,
            @RequestParam(required = false) String higher_id,
            @RequestParam(required = false) String name
    ) {
        StringBuilder output = new StringBuilder();
        UnitDAOImpl.Filter filter = new UnitDAOImpl.Filter();
        if (name != null) {
            System.out.println("Added param: " + name);
            filter.setUnitName(name);
        }
        if (id != null) {
            filter.setId(Long.parseLong(id));
        }
        if (higher_id != null) {
            filter.setHigherUnitId(Long.parseLong(higher_id));
        }
        List<UnitEntity> emp_list = unitDAO.getUnitListByFilter(filter);
        output.append("<table>");
        output.append("<tr><th>ID</th><th>Название</th><th>ID вышестоящего подразделения</th></tr>\n");
        for (UnitEntity ent : emp_list) {
            output.append("<tr><td><a href=\"division_data?division_id=").append(ent.getUnitId()).append("\">").append(ent.getUnitId())
                    .append("</a></td><td><a href=\"division_data?division_id=").append(ent.getUnitId()).append("\">").append(ent.getUnitName())
                    .append("</a></td><td><a href=\"division_data?division_id=").append(ent.getUnitId()).append("\">").append(ent.getHigherUnitId())
                    .append("</a></td></tr>\n");
        }
        output.append("</table>");
        return output.toString();
    }
}