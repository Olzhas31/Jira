package com.example.Jira.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false, unique = true)
    private String title;

    @Column(nullable = false)
    private String priority;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdTime;

    @Column(nullable = false)
    private LocalDateTime updatedTime;

    @Column
    private LocalDateTime resolvedTime;

    @Column
    private LocalDate startDate;

    @Column
    private LocalDate dueDate;

    @Column(nullable = false)
    private String status;

    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @ManyToOne
    @JoinColumn(name = "assignee_id")
    private User assignee; // ответственный

    @ManyToOne
    @JoinColumn(name = "reviewer_id")
    private User reviewer; // code review

    @ManyToOne
    @JoinColumn(name = "developer_id")
    private User developer; // разработчик

    @ManyToOne
    @JoinColumn(name = "expert_id", nullable = false)
    private User expert; // аналитик

    @ManyToOne
    @JoinColumn(name = "acceptor_id", nullable = false)
    private User acceptor; // кто создал

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "tasks_sprints",
            joinColumns = @JoinColumn(name = "task_id"),
            inverseJoinColumns = @JoinColumn(name = "sprint_id"))
    private List<Sprint> sprints;

    //    private List<User> watchers;

    @ManyToOne
    @JoinColumn(name = "hub_id")
    private Hub hub;

//    private List<Log> workLogs;


    @Override
    public String toString() {
        String assigneeUsername = assignee == null ? "null" : assignee.getUsername();
        String reviewerUsername = reviewer == null ? "null" : reviewer.getUsername();
        String developerUsername = developer == null ? "null" : developer.getUsername();
        String hubId = Objects.isNull(hub) ? "null" : hub.getName();
        return "Task{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", title='" + title + '\'' +
                ", priority='" + priority + '\'' +
                ", description='" + description + '\'' +
                ", createdTime=" + createdTime +
                ", updatedTime=" + updatedTime +
                ", resolvedTime=" + resolvedTime +
                ", startDate=" + startDate +
                ", dueDate=" + dueDate +
                ", status='" + status + '\'' +
                ", project=" + project.getId() +
                ", assignee=" + assigneeUsername +
                ", reviewer=" + reviewerUsername +
                ", developer=" + developerUsername +
                ", expert=" + expert.getUsername() +
                ", acceptor=" + acceptor.getUsername() +
                ", sprints=" + sprints +
                ", hub=" + hubId +
                '}';
    }
}
