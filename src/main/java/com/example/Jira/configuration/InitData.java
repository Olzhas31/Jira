package com.example.Jira.configuration;

import com.example.Jira.entity.*;
import com.example.Jira.entity.states.Priority;
import com.example.Jira.entity.states.Roles;
import com.example.Jira.entity.states.TaskStatus;
import com.example.Jira.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
@AllArgsConstructor
public class InitData implements CommandLineRunner {

    private final BCryptPasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    private final HubRepository hubRepository;
    private final SprintRepository sprintRepository;
    private final TaskRepository taskRepository;

    // {username, role, email, name, surname, phoneNumber, urlPicture, info}
    private final String[][] users = {
            {"jadyra", Roles.PROJECT_MANAGER.name(), "jadyra_pm@mail.ru", "Жадыра", "Бесбаева", "+7 777 000 89 94", "default_1.jpg", ""},
            {"armanbek", Roles.PROJECT_MANAGER.name(), "armanbek_pm@mail.ru", "Арманбек", "Майгазиев", "+7 777 000 95 94", "default_2.jpg", ""},
            {"saule", Roles.PROJECT_MANAGER.name(), "saule_pm@mail.ru", "Сәуле", "Мадигуловна", "+7 777 000 15 44", "default_3.jpg", ""},
            {"laura", Roles.EXPERT.name(), "laura_expert@mail.ru", "Лаура", "Жантасова", "+7 777 000 15 54", "default_4.jpg", ""},
            {"saltanat", Roles.EXPERT.name(), "saltanat_expert@mail.ru", "Салтанат", "Жарқынова", "+7 777 000 85 73", "default_5.jpg", ""},
            {"nurlykhan", Roles.EXPERT.name(), "nurlykhan_expert@mail.ru", "Нұрлыхан", "Тасыбаев", "+7 777 000 12 45", "default_6.jpg", ""},
            {"damir", Roles.EXPERT.name(), "damir_expert@mail.ru", "Дамир", "Санай", "+7 777 000 72 12", "default_7.jpg", ""},
            {"dastan", Roles.EXPERT.name(), "dastan_expert@mail.ru", "Дастан", "Сәбитов", "+7 777 000 42 88", "default_8.jpg", ""},
            {"dias", Roles.EXPERT.name(), "dias_expert@mail.ru", "Дияс", "Мұралхан", "+7 777 000 92 74", "default_9.jpg", ""},
            {"erkebulan", Roles.FRONTEND_DEVELOPER.name(), "erkebulan_front@mail.ru", "Еркебұлан", "Халыбаев", "+7 777 000 11 44", "default_10.jpg", ""},
            {"asanali", Roles.FRONTEND_DEVELOPER.name(), "asanali_front@mail.ru", "Асанәлі", "Жанабаев", "+7 777 000 89 24", "default_11.jpg", ""},
            {"azamat", Roles.FRONTEND_DEVELOPER.name(), "azamat_front@mail.ru", "Азамат", "Жақсылықов", "+7 777 000 52 25", "default_12.jpg", ""},
            {"nargiz", Roles.FRONTEND_DEVELOPER.name(), "nargiz_front@mail.ru", "Наргиз", "Тынышбаева", "+7 777 000 48 92", "default_13.jpg", ""},
            {"aqniet", Roles.FRONTEND_DEVELOPER.name(), "aqniet_front@mail.ru", "Ақниет", "Аманова", "+7 777 000 17 75", "default_14.jpg", ""},
            {"nazerke", Roles.FRONTEND_DEVELOPER.name(), "nazerke_front@mail.ru", "Назерке", "Беймырзаева", "+7 777 000 15 18", "default_15.jpg", ""},
            {"gulbibi", Roles.BACKEND_DEVELOPER.name(), "gulbibi_back@mail.ru", "Гүлбибі", "Байболатова", "+7 777 000 15 94", "default_16.jpg", ""},
            {"kuralai", Roles.BACKEND_DEVELOPER.name(), "kuralai_back@mail.ru", "Құралай", "Қылышбек", "+7 777 000 15 28", "default_17.jpg", ""},
            {"alua", Roles.BACKEND_DEVELOPER.name(), "alua_back@mail.ru", "Алуа", "Даутова", "+7 777 000 15 13", "default_18.jpg", ""},
            {"madiyar", Roles.BACKEND_DEVELOPER.name(), "madiyar_back@mail.ru", "Мадияр", "Сағындықов", "+7 777 000 93 18", "default_19.jpg", ""},
            {"shyngys", Roles.BACKEND_DEVELOPER.name(), "shyngys_back@mail.ru", "Шыңғыс", "Сәрсенбай", "+7 777 000 35 15", "default_20.jpg", ""},
            {"sabit", Roles.BACKEND_DEVELOPER.name(), "sabit_back@mail.ru", "Сәбит", "Қыдырбаев", "+7 777 000 33 14", "default_21.jpg", ""},
            {"aidana", Roles.DEVOPS.name(), "aidana_devops@mail.ru", "Айдана", "Маратова", "+7 777 000 89 84", "default_22.jpg", ""},
            {"aisulu", Roles.DEVOPS.name(), "aisulu_devops@mail.ru", "Айсұлу", "Қасабаева", "+7 777 000 77 24", "default_23.jpg", ""},
            {"aizhuldyz", Roles.DEVOPS.name(), "aizhuldyz_devops@mail.ru", "Айжұлдыз", "Жұмабаева", "+7 777 000 98 54", "default_24.jpg", ""},
    };

    // {name, description, goal, responsibility}
    private final String[][] projects = {
            {
                    "Jira",
                    "\"IT Кәсіпорында жобаларды басқаруға арналған ақпараттық жүйе әзірлеу\" дипломдық тақырыбына арналған веб-қосымша",
                    "\"IT Кәсіпорында жобаларды басқаруға арналған ақпараттық жүйе әзірлеу\" дипломдық тақырыбына арналған веб-қосымша әзірлеу",
                    "\"IT Кәсіпорында жобаларды басқаруға арналған ақпараттық жүйе әзірлеу\" дипломдық тақырыбына арналған веб-қосымша әзірлеу"
            },
            {
                    "SuperApp",
                    "SuperApp - мобильді қосымшасы",
                    "Универсалды мәселелерді шешуге арналған мобильді қосымша құру",
                    "Мобильді қосымша әзірлеу және эксплуатациялау"
            },
            {
                    "KaspiShop",
                    "KaspiShop - веб-қосымшасын әзірлеу",
                    "Әр түрлі тауарларды сатуға арналған веб-қосымша әзірлеу",
                    "Веб-қосымша әзірлеу және оны қолданысқа шығару"
            }
    };


    // i - project, j - user
    private final int[][] sequence = {
            {0, 3, 4, 9, 10, 15, 16, 21},
            {1, 5, 6, 11, 12, 17, 18, 22},
            {2, 7, 8, 13, 14, 19, 20, 23}
    };

    // {name, creater_username, project_name, content}
    private final String[][] hubs = {
            {"Деректер қоры", users[0][0], projects[0][0], "Деректер қоры \n Test"},
            {"Backend бағдарламалау", users[0][0], projects[0][0], "Backend бағдарламалау \n 1. Configuratin папкасы \n 2. Controller \n 3. Entity "},
            {"Frontend бағдарламалау", users[0][0], projects[0][0], "Fronend бағдарламау барысында қажетті файлдар \"src/main/resources/static\" папкасының ішінде орналасады"}
    };

    // {name, start_date, end_date, projectName}
    private final String[][] sprints = {
            {"Jira-қаңтар", String.valueOf(LocalDate.of(2023,1,1)), String.valueOf(LocalDate.of(2023,1,31)), projects[0][0]},
            {"Jira-ақпан", String.valueOf(LocalDate.of(2023,2,1)), String.valueOf(LocalDate.of(2023,2,28)), projects[0][0]},
            {"Jira-наурыз", String.valueOf(LocalDate.of(2023,3,1)), String.valueOf(LocalDate.of(2023,3,31)), projects[0][0]},
    };

    final static DateTimeFormatter CUSTOM_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    // { name, title, priority, description,
    // createdTime, resolvedTime, startDate, dueDate, status,
    // project, hub, acceptor, assignee, developer, expert, reviewer }
    private final String[][] tasks = {
            {
                "Jira-1", "Жаңа жобаға арналған деректер қорын құру", Priority.ҚАЛЫПТЫ.name(),
                    "Әзірленіп жатқан жаңа ақпараттық жүйеге арнап PostgreSQL деректер қорын құру",
                    (LocalDateTime.of(2023, 1, 1,10, 0)).format(CUSTOM_FORMATTER),
                    (LocalDateTime.of(2023, 1, 10,10, 0)).format(CUSTOM_FORMATTER),
                    String.valueOf(LocalDate.of(2023,1,3)),
                    String.valueOf(LocalDate.of(2023,1,10)),
                    TaskStatus.JABYLDY.name(), projects[0][0], hubs[0][0],
                    users[0][0], users[15][0], users[15][0], users[3][0], users[3][0]
            },
            {
                    "Jira-2", "Кестелер құру", Priority.ҚАЛЫПТЫ.name(),
                    "Деректер қорында келесі кестелерді құру: қолданушылар, қолданушылар туралы деректер, жобалар, спринттер, хабтар, тапсырмалар",
                    (LocalDateTime.of(2023, 1, 11,10, 0)).format(CUSTOM_FORMATTER),
                    (LocalDateTime.of(2023, 1, 21,10, 0)).format(CUSTOM_FORMATTER),
                    String.valueOf(LocalDate.of(2023,1,11)),
                    String.valueOf(LocalDate.of(2023,1,21)),
                    TaskStatus.JABYLDY.name(), projects[0][0], hubs[0][0],
                    users[0][0], users[16][0], users[15][0], users[4][0], users[4][0]
            },
            {
                    "Jira-3", "Фронтенд парақшалардың каркасын әзірлеу", Priority.ҚАЛЫПТЫ.name(),
                    "Веб-қосымшаның фронендтік бөлігіндегі парақшалардың каркастарын әзірлеу. HTML5 пайдалану арқылы блоктарды айқындау. Оларды қажетті мәліметтермен толтыру",
                    (LocalDateTime.of(2023, 2, 1,10, 0)).format(CUSTOM_FORMATTER),
                    (LocalDateTime.of(2023, 2, 10,10, 0)).format(CUSTOM_FORMATTER),
                    String.valueOf(LocalDate.of(2023,2,3)),
                    String.valueOf(LocalDate.of(2023,2,10)),
                    TaskStatus.JABYLDY.name(), projects[0][0], hubs[1][0],
                    users[0][0], users[9][0], users[9][0], users[3][0], users[3][0]
            },
            {
                    "Jira-4", "Фронтенд парақшалардың стильдері ", Priority.ҚАЛЫПТЫ.name(),
                    "Веб-қосымшаның негізгі стильдерін жазу",
                    (LocalDateTime.of(2023, 2, 1,10, 0)).format(CUSTOM_FORMATTER),
                    (LocalDateTime.of(2023, 2, 10,10, 0)).format(CUSTOM_FORMATTER),
                    String.valueOf(LocalDate.of(2023,2,3)),
                    String.valueOf(LocalDate.of(2023,2,10)),
                    TaskStatus.JABYLDY.name(), projects[0][0], hubs[1][0],
                    users[0][0], users[10][0], users[10][0], users[4][0], users[4][0]
            }
    };

    @Override
    public void run(String... args) throws Exception {
        initAdmin();
        initUsers();
        initProjects();
        initHubs();
        initSprints();
        addUsersToProject();
        initTasks();
        addTaskToSprints();
    }

    private void addTaskToSprints() {
        int [] ids = {0, 0, 1, 1};
        for (int i = 0; i < tasks.length; i++) {
            Sprint sprint = sprintRepository.findByName(sprints[ids[i]][0]);
            Task task = taskRepository.findByName(tasks[i][0]);
            if (!task.getSprints().contains(sprint)) {
                task.getSprints().add(sprint);
                taskRepository.save(task);
            }
        }
    }

    private void initTasks() {
        for (int i = 0; i < tasks.length; i++) {
            Project project = projectRepository.findByName(tasks[i][9]);
            Hub hub = hubRepository.findByName(tasks[i][10]);
            User acceptor = userRepository.findByUsername(tasks[i][11])
                    .orElse(null);
            User assignee = userRepository.findByUsername(tasks[i][12])
                    .orElse(null);
            User developer = userRepository.findByUsername(tasks[i][13])
                    .orElse(null);
            User expert = userRepository.findByUsername(tasks[i][14])
                    .orElse(null);
            User reviewer = userRepository.findByUsername(tasks[i][15])
                    .orElse(null);

            Task task = Task.builder()
                    .name(tasks[i][0])
                    .title(tasks[i][1])
                    .priority(tasks[i][2])
                    .description(tasks[i][3])
                    .createdTime(LocalDateTime.parse(tasks[i][4], CUSTOM_FORMATTER))
                    .updatedTime(LocalDateTime.parse(tasks[i][4], CUSTOM_FORMATTER))
                    .resolvedTime(LocalDateTime.parse(tasks[i][5], CUSTOM_FORMATTER))
                    .startDate(LocalDate.parse(tasks[i][6]))
                    .dueDate(LocalDate.parse(tasks[i][7]))
                    .status(tasks[i][8])
                    .project(project)
                    .assignee(assignee)
                    .reviewer(reviewer)
                    .developer(developer)
                    .expert(expert)
                    .acceptor(acceptor)
                    .hub(hub)
                    .build();

            if (!taskRepository.existsByName(task.getName())) {
                taskRepository.save(task);
            }
        }
    }

    private void addUsersToProject() {
        for (int i = 0; i < sequence.length; i++) {
            Project project = projectRepository.findByName(projects[i][0]);
            for (int j = 0; j < sequence[i].length; j++) {
                int id = sequence[i][j];
                User user = userRepository.findByUsername(users[id][0]).orElseThrow();
                if (!project.getUsers().contains(user)) {
                    project.getUsers().add(user);
                }
            }
            projectRepository.save(project);
        }
    }

    private void initSprints() {
        for (int i = 0; i < sprints.length; i++) {
            Project project = projectRepository.findByName(hubs[i][2]);
            Sprint sprint = Sprint.builder()
                    .name(sprints[i][0])
                    .startDate(LocalDate.parse(sprints[i][1]))
                    .endDate(LocalDate.parse(sprints[i][2]))
                    .project(project)
                    .build();

            if (!sprintRepository.existsByName(sprint.getName())) {
                sprintRepository.save(sprint);
            }
        }
    }

    private void initHubs() {
        for (int i = 0; i < hubs.length; i++) {
            LocalDateTime now = LocalDateTime.now();
            User user = userRepository.findByUsername(hubs[i][1])
                    .orElseThrow();
            Project project = projectRepository.findByName(hubs[i][2]);

            Hub hub = Hub.builder()
                    .name(hubs[i][0])
                    .createdTime(now)
                    .updatedTime(now)
                    .content(hubs[i][3])
                    .creator(user)
                    .lastUpdater(user)
                    .project(project)
                    .build();

            if (!hubRepository.existsByNameAndProject(hub.getName(), hub.getProject())) {
                hubRepository.save(hub);
            }
        }
    }

    private void initProjects() {
        for (int i = 0; i < projects.length; i++) {
            Project project = Project.builder()
                    .name(projects[i][0])
                    .description(projects[i][1])
                    .goal(projects[i][2])
                    .responsibility(projects[i][3])
                    .build();

            if (!projectRepository.existsByName(project.getName())) {
                projectRepository.save(project);
            }
        }
    }

    private void initUsers() {
        for (int i = 0; i < users.length; i++) {
            User user = User.builder()
                    .username(users[i][0])
                    .password(passwordEncoder.encode("100"))
                    .role(users[i][1])
                    .enabled(true)
                    .locked(false)
                    .build();
            UserDetail userDetail = UserDetail.builder()
                    .email(users[i][2])
                    .name(users[i][3])
                    .surname(users[i][4])
                    .info(users[i][7])
                    .phoneNumber(users[i][5])
                    .urlPicture(users[i][6])
                    .registerDate(LocalDate.of(2023, 1, 1))
                    .user(user)
                    .build();
            user.setUserDetail(userDetail);

            if (!userRepository.existsByUsername(user.getUsername())) {
                userRepository.save(user);
            }
        }
    }

    private void initAdmin() {
        User admin = User.builder()
                .username("admin")
                .password(passwordEncoder.encode("100"))
                .role(Roles.ADMIN.name())
                .enabled(true)
                .locked(false)
                .build();
        if (!userRepository.existsByUsername(admin.getUsername())) {
            userRepository.save(admin);
        }
    }
}
