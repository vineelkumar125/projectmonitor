//package com.projectmonitor.controller;
//import com.projectmonitor.entity.*; import com.projectmonitor.repository.*; import org.springframework.http.*; import org.springframework.web.bind.annotation.*; import java.util.*;
//@RestController @RequestMapping("/api/projects") public class ProjectController {private final ProjectRepository projects;private final UserRepository users;public ProjectController(ProjectRepository p,UserRepository u){projects=p;users=u;}
// @GetMapping public List<Map<String,Object>> all(){return projects.findAll().stream().map(this::map).toList();}
// @GetMapping("/{id}") public Map<String,Object> one(@PathVariable Long id){return map(projects.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Project not found")));}
// @PostMapping public Map<String,Object> create(@RequestBody Map<String,Object> b){Project p=new Project();fill(p,b);if(b.get("managerId")!=null)p.setManager(users.findById(Long.valueOf(b.get("managerId").toString())).orElse(null));return map(projects.save(p));}
// @PutMapping("/{id}") public Map<String,Object> update(@PathVariable Long id,@RequestBody Map<String,Object> b){Project p=projects.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Project not found"));fill(p,b);if(b.get("managerId")!=null)p.setManager(users.findById(Long.valueOf(b.get("managerId").toString())).orElse(null));return map(projects.save(p));}
// @DeleteMapping("/{id}") public void delete(@PathVariable Long id){if(!projects.existsById(id))throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Project not found");projects.deleteById(id);}
// private void fill(Project p,Map<String,Object>b){if(b.get("name")!=null)p.setName(b.get("name").toString());p.setDescription((String)b.get("description"));if(b.get("startDate")!=null)p.setStartDate(java.time.LocalDate.parse(b.get("startDate").toString()));if(b.get("endDate")!=null)p.setEndDate(java.time.LocalDate.parse(b.get("endDate").toString()));if(b.get("budget")!=null)p.setBudget(Double.valueOf(b.get("budget").toString()));if(b.get("status")!=null)p.setStatus(ProjectStatus.valueOf(b.get("status").toString()));}
// private Map<String,Object> map(Project p){Map<String,Object>m=new LinkedHashMap<>();m.put("id",p.getId());m.put("name",p.getName());m.put("description",p.getDescription());m.put("startDate",p.getStartDate());m.put("endDate",p.getEndDate());m.put("budget",p.getBudget());m.put("status",p.getStatus());m.put("managerId",p.getManager()==null?null:p.getManager().getId());m.put("managerName",p.getManager()==null?null:p.getManager().getName());return m;}}
package com.projectmonitor.controller;

import com.projectmonitor.entity.Project;
import com.projectmonitor.entity.ProjectStatus;
import com.projectmonitor.repository.ProjectRepository;
import com.projectmonitor.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

 private final ProjectRepository projects;
 private final UserRepository users;

 public ProjectController(
         ProjectRepository projects,
         UserRepository users) {
  this.projects = projects;
  this.users = users;
 }

 @GetMapping
 public List<Map<String, Object>> all() {
  return projects.findAll()
          .stream()
          .map(this::map)
          .toList();
 }

 @GetMapping("/{id}")
 public Map<String, Object> one(@PathVariable Long id) {

  Project project = projects.findById(id)
          .orElseThrow(() ->
                  new ResponseStatusException(
                          HttpStatus.NOT_FOUND,
                          "Project not found"
                  ));

  return map(project);
 }

 @PostMapping
 public Map<String, Object> create(
         @RequestBody Map<String, Object> body) {

  Project project = new Project();

  fill(project, body);

  if (body.get("managerId") != null) {

   Long managerId = Long.valueOf(
           body.get("managerId").toString()
   );

   project.setManager(
           users.findById(managerId).orElse(null)
   );
  }

  return map(projects.save(project));
 }

 @PutMapping("/{id}")
 public Map<String, Object> update(
         @PathVariable Long id,
         @RequestBody Map<String, Object> body) {

  Project project = projects.findById(id)
          .orElseThrow(() ->
                  new ResponseStatusException(
                          HttpStatus.NOT_FOUND,
                          "Project not found"
                  ));

  fill(project, body);

  if (body.get("managerId") != null) {

   Long managerId = Long.valueOf(
           body.get("managerId").toString()
   );

   project.setManager(
           users.findById(managerId).orElse(null)
   );
  }

  return map(projects.save(project));
 }

 @DeleteMapping("/{id}")
 public void delete(@PathVariable Long id) {

  if (!projects.existsById(id)) {
   throw new ResponseStatusException(
           HttpStatus.NOT_FOUND,
           "Project not found"
   );
  }

  projects.deleteById(id);
 }

 private void fill(
         Project project,
         Map<String, Object> body) {

  if (body.get("name") != null) {
   project.setName(
           body.get("name").toString()
   );
  }

  if (body.get("description") != null) {
   project.setDescription(
           body.get("description").toString()
   );
  }

  if (body.get("startDate") != null) {
   project.setStartDate(
           LocalDate.parse(
                   body.get("startDate").toString()
           )
   );
  }

  if (body.get("endDate") != null) {
   project.setEndDate(
           LocalDate.parse(
                   body.get("endDate").toString()
           )
   );
  }

  if (body.get("budget") != null) {
   project.setBudget(
           Double.valueOf(
                   body.get("budget").toString()
           )
   );
  }

  if (body.get("status") != null) {
   project.setStatus(
           ProjectStatus.valueOf(
                   body.get("status").toString().toUpperCase()
           )
   );
  }
 }

 private Map<String, Object> map(Project project) {

  Map<String, Object> map = new LinkedHashMap<>();

  map.put("id", project.getId());
  map.put("name", project.getName());
  map.put("description", project.getDescription());
  map.put("startDate", project.getStartDate());
  map.put("endDate", project.getEndDate());
  map.put("budget", project.getBudget());
  map.put("status", project.getStatus());

  map.put(
          "managerId",
          project.getManager() == null
                  ? null
                  : project.getManager().getId()
  );

  map.put(
          "managerName",
          project.getManager() == null
                  ? null
                  : project.getManager().getName()
  );

  return map;
 }
}