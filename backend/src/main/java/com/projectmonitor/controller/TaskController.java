//package com.projectmonitor.controller;
//import com.projectmonitor.entity.*; import com.projectmonitor.repository.*; import org.springframework.http.*; import org.springframework.web.bind.annotation.*; import java.time.LocalDate; import java.util.*;
//@RestController @RequestMapping("/api/tasks") public class TaskController {private final TaskRepository tasks;private final ProjectRepository projects;private final UserRepository users;public TaskController(TaskRepository t,ProjectRepository p,UserRepository u){tasks=t;projects=p;users=u;}
// @GetMapping public List<Map<String,Object>> all(){return tasks.findAll().stream().map(this::map).toList();}
// @GetMapping("/project/{projectId}") public List<Map<String,Object>> byProject(@PathVariable Long projectId){return tasks.findByProjectId(projectId).stream().map(this::map).toList();}
// @PostMapping public Map<String,Object> create(@RequestBody Map<String,Object>b){Task t=new Task();fill(t,b);return map(tasks.save(t));}
// @PutMapping("/{id}") public Map<String,Object> update(@PathVariable Long id,@RequestBody Map<String,Object>b){Task t=tasks.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Task not found"));fill(t,b);return map(tasks.save(t));}
// @DeleteMapping("/{id}") public void delete(@PathVariable Long id){tasks.deleteById(id);}
// private void fill(Task t,Map<String,Object>b){if(b.get("title")!=null)t.setTitle(b.get("title").toString());t.setDescription((String)b.get("description"));if(b.get("deadline")!=null)t.setDeadline(LocalDate.parse(b.get("deadline").toString()));if(b.get("priority")!=null)t.setPriority(Priority.valueOf(b.get("priority").toString()));if(b.get("status")!=null)t.setStatus(TaskStatus.valueOf(b.get("status").toString()));if(b.get("projectId")!=null)t.setProject(projects.findById(Long.valueOf(b.get("projectId").toString())).orElseThrow(()->new ResponseStatusException(HttpStatus.BAD_REQUEST,"Invalid projectId")));if(b.get("assignedToId")!=null)t.setAssignedTo(users.findById(Long.valueOf(b.get("assignedToId").toString())).orElseThrow(()->new ResponseStatusException(HttpStatus.BAD_REQUEST,"Invalid assignedToId")));}
// private Map<String,Object> map(Task t){Map<String,Object>m=new LinkedHashMap<>();m.put("id",t.getId());m.put("title",t.getTitle());m.put("description",t.getDescription());m.put("deadline",t.getDeadline());m.put("priority",t.getPriority());m.put("status",t.getStatus());m.put("projectId",t.getProject()==null?null:t.getProject().getId());m.put("projectName",t.getProject()==null?null:t.getProject().getName());m.put("assignedToId",t.getAssignedTo()==null?null:t.getAssignedTo().getId());m.put("assignedToName",t.getAssignedTo()==null?null:t.getAssignedTo().getName());return m;}}
//package com.projectmonitor.controller;
//
//import com.projectmonitor.dto.LoginRequest;
//import com.projectmonitor.dto.LoginResponse;
//import com.projectmonitor.dto.RegisterRequest;
//import com.projectmonitor.entity.Role;
//import com.projectmonitor.entity.User;
//import com.projectmonitor.repository.UserRepository;
//import com.projectmonitor.security.JwtService;
//import jakarta.validation.Valid;
//import org.springframework.http.HttpStatus;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.server.ResponseStatusException;
//
//@RestController
//@RequestMapping("/api/auth")
//public class TaskController {
//
// private final UserRepository users;
// private final PasswordEncoder encoder;
// private final JwtService jwt;
//
// public TaskController(
//         UserRepository users,
//         PasswordEncoder encoder,
//         JwtService jwt) {
//  this.users = users;
//  this.encoder = encoder;
//  this.jwt = jwt;
// }
//
// @PostMapping("/login")
// public LoginResponse login(
//         @Valid @RequestBody LoginRequest request) {
//
//  User user = users.findByEmail(request.email())
//          .orElseThrow(() ->
//                  new ResponseStatusException(
//                          HttpStatus.UNAUTHORIZED,
//                          "Invalid email or password"
//                  ));
//
//  if (!encoder.matches(
//          request.password(),
//          user.getPassword())) {
//
//   throw new ResponseStatusException(
//           HttpStatus.UNAUTHORIZED,
//           "Invalid email or password"
//   );
//  }
//
//  return new LoginResponse(
//          jwt.generate(
//                  user.getEmail(),
//                  user.getRole().name()
//          ),
//          user.getId(),
//          user.getName(),
//          user.getEmail(),
//          user.getRole().name()
//  );
// }
//
// @PostMapping("/register")
// public LoginResponse register(
//         @Valid @RequestBody RegisterRequest request) {
//
//  if (users.findByEmail(request.email()).isPresent()) {
//   throw new ResponseStatusException(
//           HttpStatus.CONFLICT,
//           "Email already registered"
//   );
//  }
//
//  Role role = Role.MEMBER;
//
//  try {
//   if (request.role() != null) {
//    role = Role.valueOf(
//            request.role().toUpperCase()
//    );
//   }
//  } catch (IllegalArgumentException ignored) {
//   // Keep default MEMBER role
//  }
//
//  User user = users.save(
//          new User(
//                  request.name(),
//                  request.email(),
//                  encoder.encode(request.password()),
//                  role
//          )
//  );
//
//  return new LoginResponse(
//          jwt.generate(
//                  user.getEmail(),
//                  user.getRole().name()
//          ),
//          user.getId(),
//          user.getName(),
//          user.getEmail(),
//          user.getRole().name()
//  );
// }
//}

package com.projectmonitor.controller;

import com.projectmonitor.entity.Priority;
import com.projectmonitor.entity.Task;
import com.projectmonitor.entity.TaskStatus;
import com.projectmonitor.repository.ProjectRepository;
import com.projectmonitor.repository.TaskRepository;
import com.projectmonitor.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

 private final TaskRepository tasks;
 private final ProjectRepository projects;
 private final UserRepository users;

 public TaskController(
         TaskRepository tasks,
         ProjectRepository projects,
         UserRepository users) {

  this.tasks = tasks;
  this.projects = projects;
  this.users = users;
 }

 @GetMapping
 public List<Map<String, Object>> all() {
  return tasks.findAll()
          .stream()
          .map(this::map)
          .toList();
 }

 @GetMapping("/project/{projectId}")
 public List<Map<String, Object>> byProject(
         @PathVariable Long projectId) {

  return tasks.findByProjectId(projectId)
          .stream()
          .map(this::map)
          .toList();
 }

 @PostMapping
 public Map<String, Object> create(
         @RequestBody Map<String, Object> body) {

  Task task = new Task();

  fill(task, body);

  return map(tasks.save(task));
 }

 @PutMapping("/{id}")
 public Map<String, Object> update(
         @PathVariable Long id,
         @RequestBody Map<String, Object> body) {

  Task task = tasks.findById(id)
          .orElseThrow(() ->
                  new ResponseStatusException(
                          HttpStatus.NOT_FOUND,
                          "Task not found"
                  ));

  fill(task, body);

  return map(tasks.save(task));
 }

 @DeleteMapping("/{id}")
 public void delete(@PathVariable Long id) {
  tasks.deleteById(id);
 }

 private void fill(
         Task task,
         Map<String, Object> body) {

  if (body.get("title") != null) {
   task.setTitle(body.get("title").toString());
  }

  task.setDescription(
          body.get("description") == null
                  ? null
                  : body.get("description").toString()
  );

  if (body.get("deadline") != null) {
   task.setDeadline(
           LocalDate.parse(
                   body.get("deadline").toString()
           )
   );
  }

  if (body.get("priority") != null) {
   task.setPriority(
           Priority.valueOf(
                   body.get("priority").toString()
           )
   );
  }

  if (body.get("status") != null) {
   task.setStatus(
           TaskStatus.valueOf(
                   body.get("status").toString()
           )
   );
  }

  if (body.get("projectId") != null) {

   Long projectId =
           Long.valueOf(
                   body.get("projectId").toString()
           );

   task.setProject(
           projects.findById(projectId)
                   .orElseThrow(() ->
                           new ResponseStatusException(
                                   HttpStatus.BAD_REQUEST,
                                   "Invalid projectId"
                           ))
   );
  }

  if (body.get("assignedToId") != null) {

   Long userId =
           Long.valueOf(
                   body.get("assignedToId").toString()
           );

   task.setAssignedTo(
           users.findById(userId)
                   .orElseThrow(() ->
                           new ResponseStatusException(
                                   HttpStatus.BAD_REQUEST,
                                   "Invalid assignedToId"
                           ))
   );
  }
 }

 private Map<String, Object> map(Task task) {

  Map<String, Object> result =
          new LinkedHashMap<>();

  result.put("id", task.getId());
  result.put("title", task.getTitle());
  result.put("description", task.getDescription());
  result.put("deadline", task.getDeadline());
  result.put("priority", task.getPriority());
  result.put("status", task.getStatus());

  result.put(
          "projectId",
          task.getProject() == null
                  ? null
                  : task.getProject().getId()
  );

  result.put(
          "projectName",
          task.getProject() == null
                  ? null
                  : task.getProject().getName()
  );

  result.put(
          "assignedToId",
          task.getAssignedTo() == null
                  ? null
                  : task.getAssignedTo().getId()
  );

  result.put(
          "assignedToName",
          task.getAssignedTo() == null
                  ? null
                  : task.getAssignedTo().getName()
  );

  return result;
 }
}