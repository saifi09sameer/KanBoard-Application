# KanBoard-Application


Description: Developed a Kanban board application using Angular for the frontend, Spring Boot REST API for the backend, and MySQL and MongoDB for databases. The application allows users to register, login, and create multiple projects with unique project IDs. Users can assign projects to other users, and assigned projects are displayed on the assigned user's dashboard. Within a project, users can create and manage tasks with different stages and priorities. Users can assign tasks to other assigned users within the same project. Each user can have a maximum of five assigned tasks and five assigned projects at a time. The application incorporates various technologies such as Angular components, guards, services, reactive forms, Angular Material, Spring Boot REST API with JPA repository and Mongo repository, JWT token authentication, asynchronous communication using RabbitMQ, and synchronous communication using Feign client. Core Java concepts such as collections, streams, and lambda expressions are also utilized in the project.

Key Features:

- User registration and login functionality
- Creation and management of multiple projects with unique project IDs
- Assignment of projects to other users
- Display of assigned projects on the assigned user's dashboard
- Creation and management of tasks within projects, with different stages and priorities
- Assignment of tasks to other assigned users within the same project
- Limit of five assigned tasks and five assigned projects per user
- Separation of tasks and projects between assigned users, ensuring privacy
- Integration of Angular frontend with Spring Boot REST API and databases
- Implementation of authentication and authorization using JWT tokens
- Asynchronous communication between services using RabbitMQ
- Synchronous communication between services using Feign client
- Utilization of core Java concepts such as collections, streams, and lambda expressions

Admin Features:

- The admin has the authority to delete and update user accounts, providing complete control over user management.

- Both registered and unregistered users have the ability to send messages and provide feedback to the admin.

- The admin can easily View the top 5 projects, view the project creator, and track the number of ongoing tasks within each project. This feature enables the admin to stay informed about the most active projects and their progress at a glance.

- The admin has access to a dedicated dashboard that provides various statistics and insights. The admin can view the total number of users registered in the system, the number of users currently online, the total number of projects, and the number of ongoing tasks. These metrics help the admin monitor the system's usage and track its activity.
