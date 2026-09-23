// import React,{useEffect,useState} from 'react'; import {LayoutDashboard,FolderKanban,CheckSquare,Users,LogOut,Plus,Trash2,RefreshCw} from 'lucide-react'; import api from './services/api';
// const blankProject={name:'',description:'',startDate:'',endDate:'',budget:'',status:'NOT_STARTED'}; const blankTask={title:'',description:'',deadline:'',priority:'MEDIUM',status:'PENDING',projectId:'',assignedToId:''};
// function Login({onLogin}){const [email,setEmail]=useState('admin@projectmonitor.com'),[password,setPassword]=useState('admin123'),[err,setErr]=useState('');async function submit(e){e.preventDefault();try{const r=await api.post('/auth/login',{email,password});localStorage.setItem('token',r.data.token);localStorage.setItem('user',JSON.stringify(r.data));onLogin(r.data)}catch(x){setErr(x.response?.data?.message||'Login failed')}}return <div className="login"><form onSubmit={submit}><h1>ProjectMonitor</h1><p>Project monitoring platform</p>{err&&<div className="error">{err}</div>}<label>Email<input value={email} onChange={e=>setEmail(e.target.value)}/></label><label>Password<input type="password" value={password} onChange={e=>setPassword(e.target.value)}/></label><button>Login</button><small>Demo: admin@projectmonitor.com / admin123</small></form></div>}
// function App(){const [user,setUser]=useState(()=>JSON.parse(localStorage.getItem('user')||'null'));const [page,setPage]=useState('dashboard');const [stats,setStats]=useState({}),[projects,setProjects]=useState([]),[tasks,setTasks]=useState([]),[users,setUsers]=useState([]),[showProject,setShowProject]=useState(false),[showTask,setShowTask]=useState(false);const [project,setProject]=useState(blankProject),[task,setTask]=useState(blankTask);
//  async function load(){try{const [s,p,t,u]=await Promise.all([api.get('/dashboard'),api.get('/projects'),api.get('/tasks'),api.get('/users')]);setStats(s.data);setProjects(p.data);setTasks(t.data);setUsers(u.data)}catch(e){if(e.response?.status===401)logout()}} useEffect(()=>{if(user)load()},[user]); function logout(){localStorage.clear();setUser(null)} if(!user)return <Login onLogin={setUser}/>;
//  async function saveProject(e){e.preventDefault();await api.post('/projects',{...project,budget:Number(project.budget)||0});setShowProject(false);setProject(blankProject);load()} async function saveTask(e){e.preventDefault();await api.post('/tasks',{...task,projectId:Number(task.projectId),assignedToId:task.assignedToId?Number(task.assignedToId):null});setShowTask(false);setTask(blankTask);load()} async function delProject(id){if(confirm('Delete project?')){await api.delete('/projects/'+id);load()}} async function delTask(id){if(confirm('Delete task?')){await api.delete('/tasks/'+id);load()}}
//  const nav=[['dashboard','Dashboard',LayoutDashboard],['projects','Projects',FolderKanban],['tasks','Tasks',CheckSquare],['users','Team Members',Users]];return <div className="app"><aside><div className="brand">◈ ProjectMonitor</div>{nav.map(([id,n,I])=><button className={page===id?'active':''} onClick={()=>setPage(id)} key={id}><I size={19}/>{n}</button>)}<div className="sidebottom"><div>{user.name}<small>{user.role}</small></div><button onClick={logout}><LogOut size={18}/></button></div></aside><main><header><div><h2>{page==='dashboard'?'Dashboard':page[0].toUpperCase()+page.slice(1)}</h2><span>Welcome back, {user.name}</span></div><button className="refresh" onClick={load}><RefreshCw size={17}/> Refresh</button></header>{page==='dashboard'&&<Dashboard stats={stats} projects={projects} tasks={tasks}/>} {page==='projects'&&<Projects projects={projects} onAdd={()=>setShowProject(true)} onDelete={delProject}/>} {page==='tasks'&&<Tasks tasks={tasks} projects={projects} users={users} onAdd={()=>setShowTask(true)} onDelete={delTask}/>} {page==='users'&&<UsersPage users={users}/>}</main>{showProject&&<Modal title="Create Project" close={()=>setShowProject(false)}><form onSubmit={saveProject}><Input label="Project name" value={project.name} set={v=>setProject({...project,name:v})}/><Input label="Description" value={project.description} set={v=>setProject({...project,description:v})}/><div className="grid2"><Input label="Start date" type="date" value={project.startDate} set={v=>setProject({...project,startDate:v})}/><Input label="End date" type="date" value={project.endDate} set={v=>setProject({...project,endDate:v})}/></div><Input label="Budget" type="number" value={project.budget} set={v=>setProject({...project,budget:v})}/><Select label="Status" value={project.status} set={v=>setProject({...project,status:v})} options={['NOT_STARTED','IN_PROGRESS','COMPLETED','ON_HOLD']}/><button>Save Project</button></form></Modal>}{showTask&&<Modal title="Create Task" close={()=>setShowTask(false)}><form onSubmit={saveTask}><Input label="Task title" value={task.title} set={v=>setTask({...task,title:v})}/><Input label="Description" value={task.description} set={v=>setTask({...task,description:v})}/><div className="grid2"><Input label="Deadline" type="date" value={task.deadline} set={v=>setTask({...task,deadline:v})}/><Select label="Priority" value={task.priority} set={v=>setTask({...task,priority:v})} options={['LOW','MEDIUM','HIGH']}/></div><Select label="Project" value={task.projectId} set={v=>setTask({...task,projectId:v})} options={projects.map(p=>`${p.id}:${p.name}`)} displayOption={(o)=>o.split(':')[1]} valueOption={(o)=>o.split(':')[0]}/><Select label="Assign to" value={task.assignedToId} set={v=>setTask({...task,assignedToId:v})} options={users.map(u=>`${u.id}:${u.name}`)} displayOption={o=>o.split(':')[1]} valueOption={o=>o.split(':')[0]}/><button>Create Task</button></form></Modal>}</div>}
// function Dashboard({stats,projects,tasks}){return <><div className="cards"><Card t="Total Projects" v={stats.totalProjects??0}/><Card t="Completed" v={stats.completedProjects??0}/><Card t="In Progress" v={stats.inProgressProjects??0}/><Card t="Overdue Tasks" v={tasks.filter(t=>t.deadline&&new Date(t.deadline)<new Date()&&t.status!=='COMPLETED').length}/></div><div className="two"><section className="panel"><h3>Project Progress Overview</h3><div className="bars">{projects.slice(0,6).map(p=><div className="bar" key={p.id}><div><span>{p.name}</span><b>{p.status.replaceAll('_',' ')}</b></div><div className="track"><i style={{width:p.status==='COMPLETED'?'100%':p.status==='IN_PROGRESS'?'60%':p.status==='ON_HOLD'?'35%':'5%'}}/></div></div>)}</div></section><section className="panel"><h3>Recent Projects</h3>{projects.slice(0,5).map(p=><div className="row" key={p.id}><div><b>{p.name}</b><small>Due {p.endDate||'Not set'}</small></div><span className="pill">{p.status.replaceAll('_',' ')}</span></div>)}</section></div><section className="panel"><h3>Recent Tasks</h3><TaskTable tasks={tasks.slice(0,8)}/></section></>}
// function Projects({projects,onAdd,onDelete}){return <section className="panel"><div className="panelhead"><h3>Projects</h3><button onClick={onAdd}><Plus size={17}/> New Project</button></div><div className="cards small">{projects.map(p=><div className="projectcard" key={p.id}><h3>{p.name}</h3><p>{p.description||'No description'}</p><div className="meta"><span>{p.status.replaceAll('_',' ')}</span><span>{p.endDate||'No deadline'}</span></div><button className="danger" onClick={()=>onDelete(p.id)}><Trash2 size={16}/> Delete</button></div>)}</div></section>}
// function Tasks({tasks,projects,users,onAdd,onDelete}){return <section className="panel"><div className="panelhead"><h3>Tasks</h3><button onClick={onAdd}><Plus size={17}/> New Task</button></div><TaskTable tasks={tasks} onDelete={onDelete}/></section>}
// function TaskTable({tasks,onDelete}){return <div className="tablewrap"><table><thead><tr><th>Task</th><th>Project</th><th>Assigned</th><th>Priority</th><th>Deadline</th><th>Status</th><th/></tr></thead><tbody>{tasks.map(t=><tr key={t.id}><td><b>{t.title}</b><small>{t.description||''}</small></td><td>{t.projectName}</td><td>{t.assignedToName||'Unassigned'}</td><td><span className={'priority '+t.priority.toLowerCase()}>{t.priority}</span></td><td>{t.deadline||'-'}</td><td><span className="pill">{t.status.replaceAll('_',' ')}</span></td><td>{onDelete&&<button className="iconbtn" onClick={()=>onDelete(t.id)}><Trash2 size={16}/></button>}</td></tr>)}</tbody></table></div>}
// function UsersPage({users}){return <section className="panel"><h3>Team Members</h3>{users.map(u=><div className="userrow" key={u.id}><div className="avatar">{u.name[0]}</div><div><b>{u.name}</b><small>{u.email}</small></div><span className="pill">{u.role}</span></div>)}</section>}
// function Card({t,v}){return <div className="card"><span>{t}</span><strong>{v}</strong></div>} function Modal({title,close,children}){return <div className="overlay"><div className="modal"><div className="panelhead"><h3>{title}</h3><button className="iconbtn" onClick={close}>×</button></div>{children}</div></div>} function Input({label,type='text',value,set}){return <label>{label}<input type={type} value={value??''} onChange={e=>set(e.target.value)} required={label.includes('name')||label.includes('title')}/></label>} function Select({label,value,set,options,displayOption=o=>o,valueOption=o=>o}){return <label>{label}<select value={value??''} onChange={e=>set(e.target.value)} required><option value="">Select</option>{options.map(o=><option key={o} value={valueOption(o)}>{displayOption(o)}</option>)}</select></label>}
// export default App;


import React, { useEffect, useState } from 'react';
import {
 LayoutDashboard,
 FolderKanban,
 CheckSquare,
 Users,
 LogOut,
 Plus,
 Trash2,
 RefreshCw
} from 'lucide-react';
import api from './services/api';

const blankProject = {
 name: '',
 description: '',
 startDate: '',
 endDate: '',
 budget: '',
 status: 'NOT_STARTED'
};

const blankTask = {
 title: '',
 description: '',
 deadline: '',
 priority: 'MEDIUM',
 status: 'PENDING',
 projectId: '',
 assignedToId: ''
};

/* =========================
   LOGIN
========================= */

function Login({ onLogin, onRegister }) {
 const [email, setEmail] = useState('admin@projectmonitor.com');
 const [password, setPassword] = useState('admin123');
 const [err, setErr] = useState('');

 async function submit(e) {
  e.preventDefault();

  try {
   const r = await api.post('/auth/login', {
    email,
    password
   });

   localStorage.setItem('token', r.data.token);
   localStorage.setItem('user', JSON.stringify(r.data));

   onLogin(r.data);
  } catch (x) {
   setErr(
       x.response?.data?.message ||
       'Login failed'
   );
  }
 }

 return (
     <div className="login">
      <form onSubmit={submit}>
       <h1>ProjectMonitor</h1>

       <p>Project monitoring platform</p>

       {err && (
           <div className="error">
            {err}
           </div>
       )}

       <label>
        Email
        <input
            type="email"
            value={email}
            onChange={e => setEmail(e.target.value)}
            required
        />
       </label>

       <label>
        Password
        <input
            type="password"
            value={password}
            onChange={e => setPassword(e.target.value)}
            required
        />
       </label>

       <button type="submit">
        Login
       </button>

       <small>
        Demo: admin@projectmonitor.com / admin123
       </small>

       <button
           type="button"
           className="linkbutton"
           onClick={onRegister}
       >
        Create a new account
       </button>
      </form>
     </div>
 );
}


/* =========================
   REGISTER
========================= */

function Register({ onRegister, onBack }) {
 const [name, setName] = useState('');
 const [email, setEmail] = useState('');
 const [password, setPassword] = useState('');
 const [role, setRole] = useState('MEMBER');
 const [err, setErr] = useState('');
 const [success, setSuccess] = useState('');

 async function submit(e) {
  e.preventDefault();

  setErr('');
  setSuccess('');

  try {
   const r = await api.post('/auth/register', {
    name,
    email,
    password,
    role
   });

   /*
    * Backend returns LoginResponse after registration,
    * so we can automatically log the new user in.
    */

   localStorage.setItem('token', r.data.token);
   localStorage.setItem('user', JSON.stringify(r.data));

   setSuccess('Account created successfully!');

   onRegister(r.data);

  } catch (x) {
   setErr(
       x.response?.data?.message ||
       'Registration failed'
   );
  }
 }

 return (
     <div className="login">
      <form onSubmit={submit}>
       <h1>Create Account</h1>

       <p>Join ProjectMonitor</p>

       {err && (
           <div className="error">
            {err}
           </div>
       )}

       {success && (
           <div className="success">
            {success}
           </div>
       )}

       <label>
        Name
        <input
            type="text"
            value={name}
            onChange={e => setName(e.target.value)}
            placeholder="Enter your name"
            required
        />
       </label>

       <label>
        Email
        <input
            type="email"
            value={email}
            onChange={e => setEmail(e.target.value)}
            placeholder="Enter your email"
            required
        />
       </label>

       <label>
        Password
        <input
            type="password"
            value={password}
            onChange={e => setPassword(e.target.value)}
            placeholder="Create a password"
            required
        />
       </label>

       <label>
        Role
        <select
            value={role}
            onChange={e => setRole(e.target.value)}
        >
         <option value="MEMBER">
          Member
         </option>

         <option value="ADMIN">
          Admin
         </option>
        </select>
       </label>

       <button type="submit">
        Register
       </button>

       <button
           type="button"
           className="linkbutton"
           onClick={onBack}
       >
        Already have an account? Login
       </button>
      </form>
     </div>
 );
}


/* =========================
   MAIN APP
========================= */

function App() {

 const [user, setUser] = useState(
     () =>
         JSON.parse(
             localStorage.getItem('user') || 'null'
         )
 );

 const [showRegister, setShowRegister] =
     useState(false);

 const [page, setPage] =
     useState('dashboard');

 const [stats, setStats] =
     useState({});

 const [projects, setProjects] =
     useState([]);

 const [tasks, setTasks] =
     useState([]);

 const [users, setUsers] =
     useState([]);

 const [showProject, setShowProject] =
     useState(false);

 const [showTask, setShowTask] =
     useState(false);

 const [project, setProject] =
     useState(blankProject);

 const [task, setTask] =
     useState(blankTask);


 async function load() {

  try {

   const [
    s,
    p,
    t,
    u
   ] = await Promise.all([
    api.get('/dashboard'),
    api.get('/projects'),
    api.get('/tasks'),
    api.get('/users')
   ]);

   setStats(s.data);
   setProjects(p.data);
   setTasks(t.data);
   setUsers(u.data);

  } catch (e) {

   if (e.response?.status === 401) {
    logout();
   }

  }
 }


 useEffect(() => {

  if (user) {
   load();
  }

 }, [user]);


 function logout() {

  localStorage.clear();

  setUser(null);

  setShowRegister(false);
 }


 /*
  * LOGIN PAGE
  */

 if (!user && !showRegister) {

  return (
      <Login
          onLogin={setUser}
          onRegister={() => setShowRegister(true)}
      />
  );

 }


 /*
  * REGISTER PAGE
  */

 if (!user && showRegister) {

  return (
      <Register
          onRegister={setUser}
          onBack={() => setShowRegister(false)}
      />
  );

 }


 /* =========================
    PROJECT
 ========================= */

 async function saveProject(e) {

  e.preventDefault();

  await api.post('/projects', {
   ...project,
   budget: Number(project.budget) || 0
  });

  setShowProject(false);

  setProject(blankProject);

  load();
 }


 /* =========================
    TASK
 ========================= */

 async function saveTask(e) {

  e.preventDefault();

  await api.post('/tasks', {
   ...task,
   projectId: Number(task.projectId),
   assignedToId:
       task.assignedToId
           ? Number(task.assignedToId)
           : null
  });

  setShowTask(false);

  setTask(blankTask);

  load();
 }


 /* =========================
    DELETE PROJECT
 ========================= */

 async function delProject(id) {

  if (confirm('Delete project?')) {

   await api.delete(
       '/projects/' + id
   );

   load();
  }
 }


 /* =========================
    DELETE TASK
 ========================= */

 async function delTask(id) {

  if (confirm('Delete task?')) {

   await api.delete(
       '/tasks/' + id
   );

   load();
  }
 }


 const nav = [
  [
   'dashboard',
   'Dashboard',
   LayoutDashboard
  ],
  [
   'projects',
   'Projects',
   FolderKanban
  ],
  [
   'tasks',
   'Tasks',
   CheckSquare
  ],
  [
   'users',
   'Team Members',
   Users
  ]
 ];


 return (

     <div className="app">

      {/* =========================
          SIDEBAR
      ========================= */}

      <aside>

       <div className="brand">
        ◈ ProjectMonitor
       </div>

       {nav.map(
           ([id, n, I]) => (

               <button
                   className={
                    page === id
                        ? 'active'
                        : ''
                   }
                   onClick={() => setPage(id)}
                   key={id}
               >

                <I size={19} />

                {n}

               </button>

           )
       )}

       <div className="sidebottom">

        <div>

         {user.name}

         <small>
          {user.role}
         </small>

        </div>

        <button onClick={logout}>

         <LogOut size={18} />

        </button>

       </div>

      </aside>


      {/* =========================
          MAIN CONTENT
      ========================= */}

      <main>

       <header>

        <div>

         <h2>
          {
           page === 'dashboard'
               ? 'Dashboard'
               : page[0].toUpperCase()
               + page.slice(1)
          }
         </h2>

         <span>
              Welcome back, {user.name}
            </span>

        </div>

        <button
            className="refresh"
            onClick={load}
        >

         <RefreshCw size={17} />

         Refresh

        </button>

       </header>


       {/* DASHBOARD */}

       {page === 'dashboard' && (

           <Dashboard
               stats={stats}
               projects={projects}
               tasks={tasks}
           />

       )}


       {/* PROJECTS */}

       {page === 'projects' && (

           <Projects
               projects={projects}
               onAdd={() => setShowProject(true)}
               onDelete={delProject}
           />

       )}


       {/* TASKS */}

       {page === 'tasks' && (

           <Tasks
               tasks={tasks}
               projects={projects}
               users={users}
               onAdd={() => setShowTask(true)}
               onDelete={delTask}
           />

       )}


       {/* USERS */}

       {page === 'users' && (

           <UsersPage users={users} />

       )}

      </main>


      {/* =========================
          CREATE PROJECT MODAL
      ========================= */}

      {showProject && (

          <Modal
              title="Create Project"
              close={() => setShowProject(false)}
          >

           <form onSubmit={saveProject}>

            <Input
                label="Project name"
                value={project.name}
                set={v =>
                    setProject({
                     ...project,
                     name: v
                    })
                }
            />

            <Input
                label="Description"
                value={project.description}
                set={v =>
                    setProject({
                     ...project,
                     description: v
                    })
                }
            />

            <div className="grid2">

             <Input
                 label="Start date"
                 type="date"
                 value={project.startDate}
                 set={v =>
                     setProject({
                      ...project,
                      startDate: v
                     })
                 }
             />

             <Input
                 label="End date"
                 type="date"
                 value={project.endDate}
                 set={v =>
                     setProject({
                      ...project,
                      endDate: v
                     })
                 }
             />

            </div>

            <Input
                label="Budget"
                type="number"
                value={project.budget}
                set={v =>
                    setProject({
                     ...project,
                     budget: v
                    })
                }
            />

            <Select
                label="Status"
                value={project.status}
                set={v =>
                    setProject({
                     ...project,
                     status: v
                    })
                }
                options={[
                 'NOT_STARTED',
                 'IN_PROGRESS',
                 'COMPLETED',
                 'ON_HOLD'
                ]}
            />

            <button>
             Save Project
            </button>

           </form>

          </Modal>

      )}


      {/* =========================
          CREATE TASK MODAL
      ========================= */}

      {showTask && (

          <Modal
              title="Create Task"
              close={() => setShowTask(false)}
          >

           <form onSubmit={saveTask}>

            <Input
                label="Task title"
                value={task.title}
                set={v =>
                    setTask({
                     ...task,
                     title: v
                    })
                }
            />

            <Input
                label="Description"
                value={task.description}
                set={v =>
                    setTask({
                     ...task,
                     description: v
                    })
                }
            />

            <div className="grid2">

             <Input
                 label="Deadline"
                 type="date"
                 value={task.deadline}
                 set={v =>
                     setTask({
                      ...task,
                      deadline: v
                     })
                 }
             />

             <Select
                 label="Priority"
                 value={task.priority}
                 set={v =>
                     setTask({
                      ...task,
                      priority: v
                     })
                 }
                 options={[
                  'LOW',
                  'MEDIUM',
                  'HIGH'
                 ]}
             />

            </div>

            <Select
                label="Project"
                value={task.projectId}
                set={v =>
                    setTask({
                     ...task,
                     projectId: v
                    })
                }
                options={projects.map(
                    p => `${p.id}:${p.name}`
                )}
                displayOption={
                 o => o.split(':')[1]
                }
                valueOption={
                 o => o.split(':')[0]
                }
            />

            <Select
                label="Assign to"
                value={task.assignedToId}
                set={v =>
                    setTask({
                     ...task,
                     assignedToId: v
                    })
                }
                options={users.map(
                    u => `${u.id}:${u.name}`
                )}
                displayOption={
                 o => o.split(':')[1]
                }
                valueOption={
                 o => o.split(':')[0]
                }
            />

            <button>
             Create Task
            </button>

           </form>

          </Modal>

      )}

     </div>
 );
}


/* =========================
   DASHBOARD
========================= */

function Dashboard({
                    stats,
                    projects,
                    tasks
                   }) {

 return (

     <>

      <div className="cards">

       <Card
           t="Total Projects"
           v={stats.totalProjects ?? 0}
       />

       <Card
           t="Completed"
           v={stats.completedProjects ?? 0}
       />

       <Card
           t="In Progress"
           v={stats.inProgressProjects ?? 0}
       />

       <Card
           t="Overdue Tasks"
           v={
            tasks.filter(
                t =>
                    t.deadline &&
                    new Date(t.deadline) <
                    new Date() &&
                    t.status !== 'COMPLETED'
            ).length
           }
       />

      </div>


      <div className="two">

       <section className="panel">

        <h3>
         Project Progress Overview
        </h3>

        <div className="bars">

         {projects
             .slice(0, 6)
             .map(p => (

                 <div
                     className="bar"
                     key={p.id}
                 >

                  <div>

                    <span>
                      {p.name}
                    </span>

                   <b>
                    {p.status.replaceAll(
                        '_',
                        ' '
                    )}
                   </b>

                  </div>

                  <div className="track">

                   <i
                       style={{
                        width:
                            p.status === 'COMPLETED'
                                ? '100%'
                                : p.status === 'IN_PROGRESS'
                                    ? '60%'
                                    : p.status === 'ON_HOLD'
                                        ? '35%'
                                        : '5%'
                       }}
                   />

                  </div>

                 </div>

             ))}

        </div>

       </section>


       <section className="panel">

        <h3>
         Recent Projects
        </h3>

        {projects
            .slice(0, 5)
            .map(p => (

                <div
                    className="row"
                    key={p.id}
                >

                 <div>

                  <b>
                   {p.name}
                  </b>

                  <small>
                   Due {p.endDate || 'Not set'}
                  </small>

                 </div>

                 <span className="pill">
                  {p.status.replaceAll(
                      '_',
                      ' '
                  )}
                </span>

                </div>

            ))}

       </section>

      </div>


      <section className="panel">

       <h3>
        Recent Tasks
       </h3>

       <TaskTable
           tasks={tasks.slice(0, 8)}
       />

      </section>

     </>

 );
}


/* =========================
   PROJECTS
========================= */

function Projects({
                   projects,
                   onAdd,
                   onDelete
                  }) {

 return (

     <section className="panel">

      <div className="panelhead">

       <h3>
        Projects
       </h3>

       <button onClick={onAdd}>

        <Plus size={17} />

        New Project

       </button>

      </div>


      <div className="cards small">

       {projects.map(p => (

           <div
               className="projectcard"
               key={p.id}
           >

            <h3>
             {p.name}
            </h3>

            <p>
             {p.description ||
                 'No description'}
            </p>

            <div className="meta">

              <span>
                {p.status.replaceAll(
                    '_',
                    ' '
                )}
              </span>

             <span>
                {p.endDate ||
                    'No deadline'}
              </span>

            </div>

            <button
                className="danger"
                onClick={() =>
                    onDelete(p.id)
                }
            >

             <Trash2 size={16} />

             Delete

            </button>

           </div>

       ))}

      </div>

     </section>

 );
}


/* =========================
   TASKS
========================= */

function Tasks({
                tasks,
                projects,
                users,
                onAdd,
                onDelete
               }) {

 return (

     <section className="panel">

      <div className="panelhead">

       <h3>
        Tasks
       </h3>

       <button onClick={onAdd}>

        <Plus size={17} />

        New Task

       </button>

      </div>

      <TaskTable
          tasks={tasks}
          onDelete={onDelete}
      />

     </section>

 );
}


/* =========================
   TASK TABLE
========================= */

function TaskTable({
                    tasks,
                    onDelete
                   }) {

 return (

     <div className="tablewrap">

      <table>

       <thead>

       <tr>

        <th>Task</th>
        <th>Project</th>
        <th>Assigned</th>
        <th>Priority</th>
        <th>Deadline</th>
        <th>Status</th>
        <th></th>

       </tr>

       </thead>

       <tbody>

       {tasks.map(t => (

           <tr key={t.id}>

            <td>

             <b>
              {t.title}
             </b>

             <small>
              {t.description || ''}
             </small>

            </td>

            <td>
             {t.projectName}
            </td>

            <td>
             {t.assignedToName ||
                 'Unassigned'}
            </td>

            <td>

                <span
                    className={
                        'priority ' +
                        t.priority.toLowerCase()
                    }
                >
                  {t.priority}
                </span>

            </td>

            <td>
             {t.deadline || '-'}
            </td>

            <td>

                <span className="pill">
                  {t.status.replaceAll(
                      '_',
                      ' '
                  )}
                </span>

            </td>

            <td>

             {onDelete && (

                 <button
                     className="iconbtn"
                     onClick={() =>
                         onDelete(t.id)
                     }
                 >

                  <Trash2 size={16} />

                 </button>

             )}

            </td>

           </tr>

       ))}

       </tbody>

      </table>

     </div>

 );
}


/* =========================
   USERS
========================= */

function UsersPage({ users }) {

 return (

     <section className="panel">

      <h3>
       Team Members
      </h3>

      {users.map(u => (

          <div
              className="userrow"
              key={u.id}
          >

           <div className="avatar">
            {u.name[0]}
           </div>

           <div>

            <b>
             {u.name}
            </b>

            <small>
             {u.email}
            </small>

           </div>

           <span className="pill">
            {u.role}
          </span>

          </div>

      ))}

     </section>

 );
}


/* =========================
   CARD
========================= */

function Card({ t, v }) {

 return (

     <div className="card">

      <span>
        {t}
      </span>

      <strong>
       {v}
      </strong>

     </div>

 );
}


/* =========================
   MODAL
========================= */

function Modal({
                title,
                close,
                children
               }) {

 return (

     <div className="overlay">

      <div className="modal">

       <div className="panelhead">

        <h3>
         {title}
        </h3>

        <button
            className="iconbtn"
            onClick={close}
        >
         ×
        </button>

       </div>

       {children}

      </div>

     </div>

 );
}


/* =========================
   INPUT
========================= */

function Input({
                label,
                type = 'text',
                value,
                set
               }) {

 return (

     <label>

      {label}

      <input
          type={type}
          value={value ?? ''}
          onChange={e =>
              set(e.target.value)
          }
          required={
              label.includes('name') ||
              label.includes('title')
          }
      />

     </label>

 );
}


/* =========================
   SELECT
========================= */

function Select({
                 label,
                 value,
                 set,
                 options,
                 displayOption = o => o,
                 valueOption = o => o
                }) {

 return (

     <label>

      {label}

      <select
          value={value ?? ''}
          onChange={e =>
              set(e.target.value)
          }
          required
      >

       <option value="">
        Select
       </option>

       {options.map(o => (

           <option
               key={o}
               value={valueOption(o)}
           >
            {displayOption(o)}
           </option>

       ))}

      </select>

     </label>

 );
}


export default App;