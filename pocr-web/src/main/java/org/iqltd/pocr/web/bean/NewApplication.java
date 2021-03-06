package org.iqltd.pocr.web.bean;

import org.iqltd.pocr.ejb.RepositoryBean;
import org.iqltd.pocr.entity.ApplicationEntity;
import org.iqltd.pocr.entity.FormEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;
import java.util.List;

@ManagedBean(name = "newApp")
@ViewScoped
public class NewApplication implements Serializable {

	private static final Logger LOGGER = LoggerFactory.getLogger(NewApplication.class);
	private static final long serialVersionUID = 1L;

	private ApplicationEntity app;

    @EJB
    private RepositoryBean repository;

	@ManagedProperty("#{applications}")
    private Applications applications;

	@PostConstruct
	public void init() {
		reset();
    }

	public void reset() {
		app = new ApplicationEntity();
	}

	public void addForm(final FormEntity form) {
		LOGGER.info("addForm(). Form added: " + form.getName());
		LOGGER.info("addForm(). Fields: " + form.getFields());

		app.getForms().add(form);
		form.setApplication(app);
	}

	public String submit() {
		LOGGER.info("submit(). App submitted: " + app.getName());

		applications.addApp(app);
        repository.persistApplication(app);

        return "home.xhtml?faces-redirect=true";
    }

	public ApplicationEntity getApp() {
		return app;
	}

	public String getName() {
		return app.getName();
	}

	public void setName(final String appName) {
		app.setName(appName);
	}

	public List<FormEntity> getForms() {
		return app.getForms();
	}

	public String getDescription() {
		return app.getDescription();
	}

	public void setDescription(final String description) {
		app.setDescription(description);
	}

    public Applications getApplications() {
        return applications;
    }

    public void setApplications(Applications applications) {
        this.applications = applications;
    }
}
