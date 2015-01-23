package pages.usermgmt;

import play.twirl.api.Html;

public abstract class PageTemplateContainer implements PageTemplate {

	private volatile PageTemplate pageTemplate;
	
	private final Object mutex = new Object();
	
	private PageTemplate getPageTemplate(){
		if (pageTemplate == null){
			synchronized (mutex) {
				if (pageTemplate == null){
					pageTemplate = createPageTemplate();
				}
			}
		}
		return pageTemplate;
	}
	
	abstract PageTemplate createPageTemplate();

	@Override
	public Html render(Html content) {
		return getPageTemplate().render(content);
	}
	
	public void setPageTemplate(PageTemplate pageTemplate){
		synchronized (mutex) {
			this.pageTemplate = pageTemplate;
		}
	}
	
}
