package com.example.Jira.entity.states;

public enum TaskStatus {
    NEW, // жаңа құрылғанда
    TODO, // біреуге бергенде, жасау керек

    ANALYZING, // анализде

    IN_PROGRESS, // жасалып жатыр

    WAITING, // біреуді күтуде
    IN_REVIEW, // код-ревьюда
    DEPLOYMENT, // деплай жасалуда
    ACCEPTANCE_TEST, // аналитикте, тестте
    
    PRODUCTION_DEPLOYMENT, // девелоперге

    CLOSED, // жабылды
    REJECTED_OR_CANCELED // отмена болды
}
