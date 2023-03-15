package com.example.Jira.entity.states;

public enum TaskStatus {
    ZHANA, // NEW жаңа құрылғанда
    TODO, //  біреуге бергенде, жасау керек

    TALDAUDA, // ANALYZING анализде

    ORINDALUDA, // IN_PROGRESS жасалып жатыр

    KUTILUDE, // WAITING біреуді күтуде
    TEKSERISTE, // IN_REVIEW код-ревьюда
    DEPLOYMENT, // DEPLOYMENT деплай жасалуда
    TESTTE, // ACCEPTANCE_TEST аналитикте, тестте
    
    PRODUCTION_DEPLOYMENT, //PRODUCTION_DEPLOYMENT девелоперге

    JABYLDY, // CLOSED жабылды
    QABYLDANBADY_NEMESE_JOIYLDY // REJECTED_OR_CANCELED отмена болды
}
