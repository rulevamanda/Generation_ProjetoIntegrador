module redeSocial {
	exports com.AskMarinho.app.RedeSocial.models;
	exports com.AskMarinho.app.RedeSocial;
	exports com.AskMarinho.app.RedeSocial.controllers;
	exports com.AskMarinho.app.RedeSocial.services;
	exports com.AskMarinho.app.RedeSocial.repositories;

	requires java.sql;
	requires spring.boot.autoconfigure;
	requires spring.web;
	requires spring.boot;
	requires spring.beans;
	requires java.validation;
	requires org.apache.tomcat.embed.core;
	requires java.persistence;
	requires com.sun.istack.runtime;
	requires org.junit.jupiter.api;
	requires org.hibernate.validator;
	requires spring.data.jpa;
	requires spring.context;

}