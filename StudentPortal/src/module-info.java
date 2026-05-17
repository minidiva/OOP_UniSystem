module StudentPortal {
    requires com.google.gson;

    // ── exports (make packages visible to the compiler & other modules) ──
    exports app;
    exports bootstrap;
    exports config;
    exports command.core;
    exports command.admin;
    exports command.course;
    exports command.manager;
    exports command.research;
    exports command.student;
    exports command.system;
    exports command.teacher;
    exports domain.common;
    exports domain.course;
    exports domain.registration;
    exports domain.research;
    exports domain.research.exceptions;
    exports domain.user;
    exports repository;
    exports service;
    exports service.event;
    exports storage;
    exports ui;
    exports util;

    // ── opens (needed so Gson can reflectively read your classes at runtime) ──
    opens domain.common      to com.google.gson;
    opens domain.course      to com.google.gson;
    opens domain.registration to com.google.gson;
    opens domain.research    to com.google.gson;
    opens domain.user        to com.google.gson;
    opens repository         to com.google.gson;
}